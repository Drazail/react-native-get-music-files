package com.GetMusicFiles.Methods;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.GetMusicFiles.C;
import com.GetMusicFiles.Models.Options.GetSongsOptions;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.GetMusicFiles.Utils.OrderByGenerator.generateSortOrder;

public class GetSongs {

    public static WritableMap getSongs(GetSongsOptions options, ContentResolver contentResolver) throws Exception {

        WritableArray jsonArray = new WritableNativeArray();
        String[] projection = new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATE_MODIFIED, MediaStore.Audio.Media.DATE_ADDED};


        String Selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String artistSearchParam ;
        String albumSearchParam ;
        List<String> selectionArgs = new ArrayList<>();
        if (options.artist != null) {
            Selection += " AND " + MediaStore.Audio.Media.ARTIST + " Like ?";
            artistSearchParam = "%" + options.artist + "%";
            selectionArgs.add(artistSearchParam);
        }

        if (options.album != null) {
            Selection += " AND " + MediaStore.Audio.Media.ALBUM + " Like ?";
            albumSearchParam = "%" + options.album + "%";
            selectionArgs.add(albumSearchParam);
        }


        String orderBy = null;

        if (options.sortBy != null) {
            orderBy = generateSortOrder(options.sortBy, options.sortOrder);
        }

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, Selection, selectionArgs.size() != 0 ? selectionArgs.toArray(new String[0]) : null, orderBy);

        int cursorCount = Objects.requireNonNull(cursor).getCount();


        if (cursorCount > (options.batchSize * options.batchNumber)) {
            cursor.moveToPosition(options.batchSize * options.batchNumber);
            do {

                WritableMap item = new WritableNativeMap();
                item.putString("title", cursor.getString(0));
                item.putString("artist", cursor.getString(1));
                item.putString("album", cursor.getString(2));
                item.putString("duration", cursor.getString(3));
                item.putString("path", cursor.getString(4));
                item.putString("id", cursor.getString(5));
                item.putString("lastModified", cursor.getString(6));
                item.putString("dateAdded", cursor.getString(7));

                jsonArray.pushMap(item);
            } while ((options.batchSize == 0 || cursor.getPosition() + 1 < options.batchSize * (options.batchNumber + 1)) & cursor.moveToNext());
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
