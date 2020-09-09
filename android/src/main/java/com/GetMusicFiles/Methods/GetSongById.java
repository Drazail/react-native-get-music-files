package com.GetMusicFiles.Methods;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.GetMusicFiles.C;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Objects;

public class GetSongById {
    public static WritableMap getSongById(ContentResolver contentResolver) throws Exception {

        WritableArray jsonArray = new WritableNativeArray();

        String Selection = MediaStore.Files.FileColumns._ID + " Like ?";
        String[] projection = new String[]{MediaStore.Files.FileColumns.DATA};

        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"),
                projection, Selection, null, null);

        int cursorCount = Objects.requireNonNull(cursor).getCount();


        if (cursorCount > 0) {
            do {
                WritableMap item = new WritableNativeMap();
                item.putString("path", cursor.getString(0));

                jsonArray.pushMap(item);
            } while (cursor.moveToNext());
        } else {

            cursor.close();
            throw new Exception(C.ErrorEnum.EMPTY_CURSOR.toString());
        }
        cursor.close();
        WritableMap results = new WritableNativeMap();
        results.putInt("length", cursorCount);
        results.putArray("results", jsonArray);
        return results;
    }
}
