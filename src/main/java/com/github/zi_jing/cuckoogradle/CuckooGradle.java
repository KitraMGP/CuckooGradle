package com.github.zi_jing.cuckoogradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CuckooGradle implements Plugin<Project> {

	@Override
	public void apply(Project proj) {
		System.out.println("Test Plugin");
	}

}
