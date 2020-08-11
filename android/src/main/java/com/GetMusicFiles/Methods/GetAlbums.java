package com.GetMusicFiles.Methods;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.GetMusicFiles.C;
import com.GetMusicFiles.Models.Options.GetAlbumsOptions;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.GetMusicFiles.Utils.OrderByGenerator.generateSortOrder;

public class GetAlbums {

    public static WritableMap getAlbums(GetAlbumsOptions options, ContentResolver contentResolver) throws Exception {

        List<String> selectionArgs = new ArrayList<>();
        String artistSearchParam ;
        String albumSearchParam ;
        String Selection = null;

        WritableArray jsonArray = new WritableNativeArray();
        String[] projection = new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.FIRST_YEAR,
                MediaStore.Audio.Albums.LAST_YEAR,
        };




        if (options.artist != null) {
            artistSearchParam = "%" + options.artist + "%";
            Selection = MediaStore.Audio.Albums.ARTIST + " Like ?";
            selectionArgs.add(artistSearchParam);
        }

        if (options.album != null) {
            albumSearchParam = "%" + options.album + "%";
            if (options.artist != null) {
                Selection += " AND " + MediaStore.Audio.Media.ALBUM + " Like ?";
            }
            if (options.artist == null) {
                Selection = MediaStore.Audio.Media.ALBUM + " Like ?";
            }
            selectionArgs.add(albumSearchParam);
        }

        String orderBy = null;

        if (options.sortBy != null) {
            orderBy = generateSortOrder(options.sortBy, options.sortOrder);
        }

        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection, Selection, selectionArgs.size() != 0 ? selectionArgs.toArray(new String[0]): null, orderBy);

        int cursorCount = Objects.requireNonNull(cursor).getCount();


        if (cursorCount > (options.batchSize * options.batchNumber)) {
            cursor.moveToPosition(options.batchSize * options.batchNumber);
            do {
                WritableMap item = new WritableNativeMap();
                item.putString("id", String.valueOf(cursor.getString(0)));
                item.putString("album", String.valueOf(cursor.getString(1)));
                item.putString("artist", String.valueOf(cursor.getString(2)));
                item.putString("numberOfSongs", String.valueOf(cursor.getString(3)));
                item.putString("firstYear", String.valueOf(cursor.getString(4)));
                item.putString("lastYear", String.valueOf(cursor.getString(5)));

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
