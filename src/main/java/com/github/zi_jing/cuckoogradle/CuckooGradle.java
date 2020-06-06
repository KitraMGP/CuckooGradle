package com.github.zi_jing.cuckoogradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import com.github.zi_jing.cuckoogradle.tasks.TaskBuildLibrary;

public class CuckooGradle implements Plugin<Project> {

	public static final String PLUGIN_VERSION = CuckooGradle.class.getPackage().getImplementationVersion() != null ? CuckooGradle.class.getPackage().getImplementationVersion() : "unknown";
	private static Project project;
	
	@Override
	public void apply(Project proj) {
		project = proj;
		System.out.println("Loading plugin CuckooGradle...");
		System.out.println("#####################################################");
		System.out.println("                 CuckooGradle" + PLUGIN_VERSION);
		System.out.println("        https://github.com/zi-jing/CuckooGradle      ");
		System.out.println("#####################################################");
		System.out.println("         Please run \"gradlew tasks\" to see the     ");
		System.out.println("              tasks added by CuckooGradle.           ");
		System.out.println("#####################################################");
		System.out.printf("Project name: %s(%s)\n", proj.getDisplayName(), proj.getName());
		System.out.println("Project version: " + proj.getVersion());
		Task testTask = proj.getTasks().create("buildLibrary", TaskBuildLibrary.class);
		testTask.setGroup("CuckooGradle Tasks");
		testTask.setDescription("Builds the Cuckoo Library");
		System.out.println("CuckooGradle is loaded successfully.");
		System.out.println();
	}
	
	public static Project getProject() {
		return project;
	}

}
