package com.github.zi_jing.cuckoogradle.extension;

public class CuckooGradleExtension {

    public static String MC_VERSION = "";
    private String mcVersion = "";

    public String getMcVersion() {
        return mcVersion;
    }

    public void setMcVersion(String mcVersion) {
        this.mcVersion = mcVersion;
        MC_VERSION = mcVersion;
    }
}
