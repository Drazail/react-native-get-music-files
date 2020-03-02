package com.GetMusicFiles.Models.Options;

import com.GetMusicFiles.C;
import com.facebook.react.bridge.ReadableMap;

public class GetAllOptions {
    public boolean cover;
    public String coverFolder;
    public int minimumSongDuration;
    public int batchSize;
    public int batchNumber;
    public C.SortBy sortBy;
    public C.SortOrder sortOrder;

    public GetAllOptions(ReadableMap options) {
        this.cover = options.hasKey("cover") && options.getBoolean("cover");
        this.coverFolder = options.hasKey("coverFolder") ? options.getString("coverFolder") : android.os.Environment.getExternalStorageDirectory() + "/.covers";
        this.minimumSongDuration = options.hasKey("minimumSongDuration") ? options.getInt("minimumSongDuration") : 0;
        this.batchSize = options.hasKey("batchSize") ? options.getInt("batchSize") : 0;
        this.batchNumber = options.hasKey("batchNumber") ? options.getInt("batchNumber") : 0;
        this.sortBy = options.hasKey("sortBy") ? C.SortBy.valueOf(options.getString("sortBy")) : null;
        this.sortOrder = options.hasKey("sortOrder") ? C.SortOrder.valueOf(options.getString("sortOrder")) : C.SortOrder.ASC;
    }
}
