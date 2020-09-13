package com.GetMusicFiles.Methods;

import android.util.Log;

import com.GetMusicFiles.Utils.FS;
import com.GetMusicFiles.Utils.MetaDataExtractor;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.GetMusicFiles.Utils.GeneralUtils.LOG;

public class GetSongByPath {

    public static WritableMap extractMetaDataFromFile(String path) {

        WritableMap results = new WritableNativeMap();
        HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(path);
        for (Map.Entry<String, String> entry : MetaMap.entrySet()) {
            results.putString(entry.getKey(), entry.getValue());
        }
        results.putString("lastModified", String.valueOf(new File(path).lastModified()) );

        return results;
    }

    public static WritableMap extractMetaDataFromDirectory(String uri, int minFileSize, int maxFileSize, String extensionFilter, boolean metaData, boolean cover, String coverPath, boolean sorted, int batchSize, int batchNumber) throws IOException {
        WritableArray results = new WritableNativeArray();
        File file = new File(uri);
        WritableMap resultsMap = new WritableNativeMap();
        if (file.isDirectory()) {
            List<String> filesPaths = new ArrayList<>();
            FS.listFilesForFolder(new File(uri), minFileSize, maxFileSize, extensionFilter, filesPaths, sorted);
            if (batchSize == 0) {
                for (String s : filesPaths) {
                    WritableMap result = new WritableNativeMap();
                    result.putString("path", s);
                    HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(s);
                    for (Map.Entry<String, String> entry : MetaMap.entrySet()) {
                        result.putString(entry.getKey(), entry.getValue());
                    }
                    if (cover) {
                        String path = getCoverFromFile(coverPath, s);
                        result.putString("cover", path);
                    }
                    results.pushMap(result);
                }
            } else {
                List<String> batch = filesPaths.subList(batchSize * batchNumber, Math.min(batchSize * (batchNumber + 1), filesPaths.size()));
                for (String s : batch) {
                    WritableMap result = new WritableNativeMap();
                    result.putString("path", s);
                    result.putString("lastModified", String.valueOf(new File(s).lastModified()));
                    if (metaData) {
                        HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(s);
                        for (Map.Entry<String, String> entry : MetaMap.entrySet()) {
                            result.putString(entry.getKey(), entry.getValue());
                        }
                    }
                    if (cover) {
                        String path = getCoverFromFile(coverPath, s);
                        result.putString("cover", path);
                    }
                    results.pushMap(result);
                }
            }

            resultsMap.putString("length", String.valueOf(filesPaths.size()));
            resultsMap.putArray("results", results);
        }
        return resultsMap;
    }

    public static String getCoverFromFile(String CoverPath, String path) throws IOException {
        String coverPath = FS.saveToStorage(CoverPath, path);
        Log.e(LOG, "File saved");
        return coverPath;
    }


}
