package com.github.zi_jing.cuckoogradle.task;

import com.github.zi_jing.cuckoogradle.CuckooGradle;
import com.github.zi_jing.cuckoogradle.extension.CuckooGradleExtension;
import com.github.zi_jing.cuckoogradle.util.Archive;
import com.github.zi_jing.cuckoogradle.util.Artifact;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GenerateArtifactsJson extends DefaultTask {

    @TaskAction
    public void run() throws IOException {
        Project project = CuckooGradle.getProject();

        if (!project.hasProperty("localPublishMavenPath") || ((String) Objects.requireNonNull(project.property("localPublishMavenPath"))).isEmpty()) {
            System.err.println("ERROR: Can't find the property \"localPublishMavenPath\" in gradle.properties!");
            throw new IllegalStateException("Can't find the property \"localPublishMavenPath\" in gradle.properties");
        }

        File jsonFile = new File(new File((String) Objects.requireNonNull(project.property("localPublishMavenPath"))).getAbsolutePath() + File.separator + "artifacts.json");

        if (jsonFile.isDirectory()) {
            System.err.println("ERROR: artifacts.json can't be a directory!");
            throw new FileNotFoundException("artifacts.json can't be a directory");
        }
        if (!jsonFile.exists()) {
            System.out.println(jsonFile.getAbsolutePath() + " can't be found, creating a new one.");
            jsonFile.createNewFile();
        }
        System.out.println("Generating artifacts.json");

        if (jsonFile.length() == 0) {
            FileUtils.writeStringToFile(jsonFile, "{}", StandardCharsets.UTF_8);
        }

        JsonParser parser = new JsonParser();
        JsonObject json;
        try {
            json = parser.parse(FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Can't parse artifacts.json!");
            throw e;
        }
        if (!json.has("artifacts")) {
            json.add("artifacts", new JsonArray());
        }

        /*
         * 在Artifact.addIfNotExistInAndUpdateDescription()调用之前,
         * thisArtifact仅包含项目的元数据(name, group, description)。
         * 上述方法调用之后, 若artifactList中有该项目, 之前的所有archives也会被带入到
         * thisArtifact中; 若没有, thisArtifact保持不变(因为此时这是一个新项目)
         */
        Artifact thisArtifact = new Artifact(project.getName(), (String) project.getGroup(), project.getDescription());
        Archive thisArchive = new Archive((String) project.getVersion(), CuckooGradleExtension.MC_VERSION, new Date().getTime());
        List<Artifact> artifactList = new ArrayList<>();

        // ---------- 获取并更新所有项目 ----------
        JsonArray artifactsArray = json.getAsJsonArray("artifacts");
        for (JsonElement artifactJsonElement : artifactsArray) {
            JsonObject artifactJsonObject = artifactJsonElement.getAsJsonObject();
            artifactList.add(Artifact.fromJson(artifactJsonObject));
        }
        Artifact.addIfNotExistsInAndUpdateDescription(artifactList, thisArtifact, thisArchive);
        // ---------- 序列化并写入 ----------
        JsonObject newJson = new JsonObject();
        JsonArray artifacts = new JsonArray();
        for (Artifact artifact : artifactList) {
            artifacts.add(artifact.toJson());
        }
        newJson.add("artifacts", artifacts);
        FileUtils.writeStringToFile(jsonFile, newJson.toString(), StandardCharsets.UTF_8);
        System.out.println("Done!");
    }
}
