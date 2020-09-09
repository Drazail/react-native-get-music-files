package com.GetMusicFiles.Models.Options;

import android.net.Uri;

import com.facebook.react.bridge.ReadableMap;

public class GetSongsByPathsOptions {
    public Uri path;
    public int minFileSize;
    public int maxFileSize;
    public String extensionFilter;
    public boolean cover;
    public Uri coverFolder;
    public boolean sorted;
    public int batchSize;
    public int batchNumber;
    public boolean metaData;

    public GetSongsByPathsOptions(ReadableMap options) {
        this.path = options.hasKey("path") ? Uri.parse(options.getString("path")) : null;
        this.minFileSize = options.hasKey("minFileSize") ? options.getInt("minFileSize") : 0;
        this.maxFileSize = options.hasKey("maxFileSize") ? options.getInt("maxFileSize") : 1073741824;
        this.extensionFilter = options.hasKey("extensionFilter") ? options.getString("extensionFilter") : "";
        this.cover = options.hasKey("cover") && options.getBoolean("cover");
        this.metaData = options.hasKey("metaData") && options.getBoolean("metaData");
        this.coverFolder = options.hasKey("coverFolder") ? Uri.parse(options.getString("coverFolder")) : null;
        this.sorted = options.hasKey("sorted") && options.getBoolean("sorted");
        this.batchSize = options.hasKey("batchSize") ? options.getInt("batchSize") : 0;
        this.batchNumber = options.hasKey("batchNumber") ? options.getInt("batchNumber") : 0;
    }
}
