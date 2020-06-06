package com.github.zi_jing.cuckoogradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import com.github.zi_jing.cuckoogradle.CuckooGradle;

public class TaskBuildLibrary extends DefaultTask {
	
	@TaskAction
	public void run() {
		CuckooGradle.getProject().getRootDir();
	}
}
