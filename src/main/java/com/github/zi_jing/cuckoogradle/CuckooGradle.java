package com.github.zi_jing.cuckoogradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

import com.github.zi_jing.cuckoogradle.tasks.TestTask;

public class CuckooGradle implements Plugin<Project> {

	@Override
	public void apply(Project proj) {
		System.out.println("Test Plugin");
		TaskProvider<TestTask> testTaskProvider = proj.getTasks().register("testTask", TestTask.class);
		testTaskProvider.configure(task -> {
			
		});
	}

}
