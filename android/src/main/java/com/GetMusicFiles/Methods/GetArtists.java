package com.GetMusicFiles.Methods;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.GetMusicFiles.C;
import com.GetMusicFiles.Models.Options.GetArtistsOptions;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Objects;

import static com.GetMusicFiles.Utils.OrderByGenerator.generateSortOrder;

public class GetArtists {

    public static WritableMap getArtists(GetArtistsOptions options, ContentResolver contentResolver) throws Exception {

        WritableArray jsonArray = new WritableNativeArray();
        String[] projection = new String[]{MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS};

        String orderBy = null;

        if (options.sortBy != null) {
            orderBy = generateSortOrder(options.sortBy, options.sortOrder);
        }

        Cursor cursor = contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                projection, null, null, orderBy);

        int cursorCount = Objects.requireNonNull(cursor).getCount();


        if (cursorCount > (options.batchSize * options.batchNumber)) {
            cursor.moveToPosition(options.batchSize * options.batchNumber);
            do {
                WritableMap item = new WritableNativeMap();
                item.putString("artist", String.valueOf(cursor.getString(0)));
                item.putString("numberOfAlbums", String.valueOf(cursor.getString(1)));
                item.putString("numberOfSongs", String.valueOf(cursor.getString(2)));

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
