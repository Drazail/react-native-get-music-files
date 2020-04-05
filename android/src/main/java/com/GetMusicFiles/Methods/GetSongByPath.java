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

    public static WritableMap extractMetaDataFromFile (String path){

        WritableMap results = new WritableNativeMap();
        HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(path);
        for (Map.Entry<String, String> entry : MetaMap.entrySet()){
            results.putString(entry.getKey(), entry.getValue());
        }

        return results;
    }

    public static WritableArray extractMetaDataFromDirectory ( String uri, int minFileSize, int maxFileSize, String extensionFilter){
        WritableArray results = new WritableNativeArray();
        File file = new File(uri);
        if(file.isDirectory()){
            List<String> filesPaths = new ArrayList<>();
            FS.listFilesForFolder( new File(uri), minFileSize, maxFileSize, extensionFilter, filesPaths);
            for (String s : filesPaths) {
                WritableMap result = new WritableNativeMap();
                HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(s);
                for (Map.Entry<String, String> entry : MetaMap.entrySet()){
                    result.putString(entry.getKey(), entry.getValue());
                }
                results.pushMap(result);
            }
        }

        return results;
    }

    public static String getCoverFromFile(String CoverPath, String path) throws IOException {
        byte[] albumImageData = MetaDataExtractor.getEmbededPicture(path);
        String coverPath = FS.saveToStorage(CoverPath, albumImageData);
        Log.e(LOG, "File saved");
        return coverPath;
    }



}
