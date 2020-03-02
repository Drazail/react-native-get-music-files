package com.GetMusicFiles.Models.Options;

import android.net.Uri;

import com.facebook.react.bridge.ReadableMap;

public class GetSongsByPathOptions {
    public Uri path;
    public boolean cover;
    public Uri coverFolder;

    public GetSongsByPathOptions(ReadableMap options) {
        this.path = options.hasKey("path") ? Uri.parse(options.getString("path")) : null;
        this.cover = options.hasKey("cover") && options.getBoolean("cover");
        this.coverFolder = options.hasKey("coverFolder") ? Uri.parse(options.getString("coverFolder")) : null;
    }
}
