package com.GetMusicFiles.Models.Options;

import android.net.Uri;

import com.facebook.react.bridge.ReadableMap;

public class GetSongsByPathsOptions {
    public Uri path;
    public int minFileSize;
    public int maxFileSize;
    public String extensionFilter;

    public GetSongsByPathsOptions(ReadableMap options) {
        this.path = options.hasKey("path") ? Uri.parse(options.getString("path")) : null;
        this.minFileSize = options.hasKey("minFileSize") ? options.getInt("minFileSize") : 0;
        this.maxFileSize = options.hasKey("maxFileSize") ? options.getInt("maxFileSize") : 1073741824;
        this.extensionFilter = options.hasKey("extensionFilter") ? options.getString("extensionFilter") : "";
    }
}
