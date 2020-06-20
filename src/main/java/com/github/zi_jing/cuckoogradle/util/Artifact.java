package com.github.zi_jing.cuckoogradle.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Artifact {
    private final String name;
    private final String group;
    private String description;
    private final List<Archive> archiveList = new ArrayList<>();

    public Artifact(String name, String group, String description) {
        this.name = name;
        this.group = group;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public List<Archive> getArchiveList() {
        return archiveList;
    }

    /**
     * 从Json中反序列化项目，Json格式请参照artifacts.json结构
     *
     * @param json 要反序列化的Json对象
     * @return 反序列化结果
     */
    public static Artifact fromJson(JsonObject json) {
        String name = json.get("name").getAsString();
        String group = json.get("group").getAsString();
        String description = json.get("description").getAsString();
        JsonArray jsonArray = json.getAsJsonArray("archives");
        Artifact artifact = new Artifact(name, group, description);
        for (JsonElement elem : jsonArray) {
            JsonObject archiveJson = elem.getAsJsonObject();
            Archive archive = Archive.fromJson(archiveJson);
            artifact.archiveList.add(archive);
        }
        return artifact;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("group", this.group);
        json.addProperty("description", this.description);
        JsonArray archives = new JsonArray();
        for (Archive archive : this.archiveList) {
            archives.add(archive.toJson());
        }
        json.add("archives", archives);
        return json;
    }

    /**
     * 当指定的项目不在{@code artifactList}中时添加到其中；若项目在{@code artifactList}中但
     * 项目描述({@code description})与{@code artifactList}中对应项目的描述不同，则更新
     * {@code artifactList}中对应项目的描述。
     *
     * @param artifactList 指定的{@code artifactList}
     * @param thisArtifact 指定的项目，通常是当前项目
     * @param thisArchive  指定的归档，通常是当前归档
     * @return 该项目是否在{@code artifactList}中
     */
    public static boolean addIfNotExistsInAndUpdateDescription(List<Artifact> artifactList, Artifact thisArtifact, Archive thisArchive) {
        boolean found = false;
        for (Artifact artifact : artifactList) {
            if (thisArtifact.doesMetadataEquals(artifact)) {
                found = true;
                if (!thisArtifact.description.equals(artifact.description)) {
                    artifact.description = thisArtifact.description;
                }
                thisArtifact.archiveList.addAll(artifact.archiveList);
                if (Archive.addIfNotExistsIn(artifact.archiveList, thisArchive)) {
                    System.out.println("Archive already exists in artifacts.json, artifacts.json won't be changed.");
                }
                break;
            }
        }
        if (!found) {
            thisArtifact.archiveList.add(thisArchive);
            artifactList.add(thisArtifact);

        }
        return found;
    }

    /**
     * 判断两个项目的{@code name}和{@code group}属性是否一致。
     *
     * @param another 要比较的另一个实例
     * @return 比较结果, true为一致, false为不一致
     */
    public boolean doesMetadataEquals(Artifact another) {
        return this.name.equals(another.name) && this.group.equals(another.group);
    }
}
