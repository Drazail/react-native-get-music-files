package com.GetMusicFiles.Methods;

import android.util.Log;

import com.GetMusicFiles.Utils.FS;
import com.GetMusicFiles.Utils.MetaDataExtractor;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.IOException;
import java.util.HashMap;
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

    public static String getCoverFromFile(String CoverPath, String path) throws IOException {
        byte[] albumImageData = MetaDataExtractor.getEmbededPicture(path);
        String coverPath = FS.saveToStorage(CoverPath, albumImageData);
        Log.e(LOG, "File saved");
        return coverPath;
    }

}
