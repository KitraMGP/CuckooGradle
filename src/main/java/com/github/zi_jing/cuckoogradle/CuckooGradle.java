package com.github.zi_jing.cuckoogradle;

import com.github.zi_jing.cuckoogradle.extension.CuckooGradleExtension;
import com.github.zi_jing.cuckoogradle.task.GenerateArtifactsJson;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class CuckooGradle implements Plugin<Project> {

    private static Project project;

    @Override
    public void apply(Project proj) {
        project = proj;
        System.out.println("Loading plugin CuckooGradle...");
        System.out.println("#####################################################");
        System.out.println("                      CuckooGradle                   ");
        System.out.println("        https://github.com/zi-jing/CuckooGradle      ");
        System.out.println("#####################################################");
        System.out.println("Project name: " + proj.getName());
        System.out.println("Local CuckooMaven Path: " + (proj.hasProperty("localPublishMavenPath") ? proj.property("localPublishMavenPath") : "None"));
        Task testTask = proj.getTasks().create("generateArtifactsJson", GenerateArtifactsJson.class);
        testTask.setGroup("CuckooGradle Tasks");
        testTask.setDescription("Generates artifacts.json in the maven root");
        project.getExtensions().create("CuckooGradle", CuckooGradleExtension.class);
        System.out.println("CuckooGradle is loaded successfully.");
        System.out.println();
    }

    public static Project getProject() {
        return project;
    }

}
