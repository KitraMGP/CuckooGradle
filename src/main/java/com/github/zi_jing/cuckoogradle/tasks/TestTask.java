package com.github.zi_jing.cuckoogradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class TestTask extends DefaultTask {
	
	@TaskAction
	public void run() {
		System.out.println("Hello Gradle!");
	}
}
