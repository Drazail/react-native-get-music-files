package com.GetMusicFiles.Models.Options;

import android.net.Uri;

import com.GetMusicFiles.C;
import com.facebook.react.bridge.ReadableMap;

public class GetSongsOptions {
    public int batchSize;
    public int batchNumber;
    public String album;
    public String artist;
    public C.SortBy sortBy;
    public C.SortOrder sortOrder;

    public GetSongsOptions(ReadableMap options) {
        this.artist = options.hasKey("artist") ? options.getString("artist") : null;
        this.album = options.hasKey("album") ? options.getString("album") : null;
        this.batchSize = options.hasKey("batchSize") ? options.getInt("batchSize") : 0;
        this.batchNumber = options.hasKey("batchNumber") ? options.getInt("batchNumber") : 0;
        this.sortBy = options.hasKey("sortBy") ? C.SortBy.valueOf(options.getString("sortBy")) : null;
        this.sortOrder = options.hasKey("sortOrder") ? C.SortOrder.valueOf(options.getString("sortOrder")) : C.SortOrder.ASC;
    }
}
