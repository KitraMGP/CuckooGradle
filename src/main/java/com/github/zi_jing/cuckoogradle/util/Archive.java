package com.github.zi_jing.cuckoogradle.util;

import com.google.gson.JsonObject;

import java.util.List;

public class Archive {
    private final String version;
    private final String mcVersion;
    private final long releaseDate;

    public Archive(String version, String mcVersion, long releaseDate) {
        this.version = version;
        this.mcVersion = mcVersion;
        this.releaseDate = releaseDate;
    }

    public String getVersion() {
        return version;
    }

    public String getMcVersion() {
        return mcVersion;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public static Archive fromJson(JsonObject json) {
        String version = json.get("version").getAsString();
        String mcVersion = json.get("mcVersion").getAsString();
        long releaseDate = json.get("releaseDate").getAsLong();
        return new Archive(version, mcVersion, releaseDate);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("version", this.version);
        json.addProperty("mcVersion", this.mcVersion);
        json.addProperty("releaseDate", this.releaseDate);
        return json;
    }

    /**
     * 当指定的归档不在{@code archiveList}中时添加到其中。
     *
     * @param archiveList 指定的{@code archiveList}
     * @param thisArchive 指定的归档，通常是当前归档
     * @return 该归档是否在{@code archiveList}中
     */
    public static boolean addIfNotExistsIn(List<Archive> archiveList, Archive thisArchive) {
        for (Archive archive : archiveList) {
            if (thisArchive.doesMetadataEquals(archive)) {
                return true;
            }
        }
        archiveList.add(thisArchive);
        return false;
    }

    /**
     * 判断两个项目的{@code version}, {@code mcVersion}属性是否一致。
     *
     * @param another 要比较的另一个实例
     * @return 比较结果, true为一致, false为不一致
     */
    public boolean doesMetadataEquals(Archive another) {
        return this.version.equals(another.version) && this.mcVersion.equals(another.mcVersion);
    }
}
