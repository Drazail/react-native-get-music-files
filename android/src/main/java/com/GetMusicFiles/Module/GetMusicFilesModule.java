package com.GetMusicFiles.Module;

import android.content.ContentResolver;

import com.GetMusicFiles.Methods.GetSongByPath;
import com.GetMusicFiles.Models.Options.GetAllOptions;
import com.GetMusicFiles.Models.Options.GetSongsByPathOptions;
import com.GetMusicFiles.Models.Options.SearchOptions;
import com.GetMusicFiles.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.Objects;

import static com.GetMusicFiles.Methods.GetAll.getAllSongs;
import static com.GetMusicFiles.Methods.Search.searchDB;

public class GetMusicFilesModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public GetMusicFilesModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }


    @Override
    public String getName() {
        return "GetMusicFiles";
    }


    @ReactMethod
    public void getAll(ReadableMap args, Promise callback) {

        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetAllOptions options = new GetAllOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = getAllSongs(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.run();

    }

    @ReactMethod
    public void getSongsByPath(ReadableMap args, Promise callback) {

        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetSongsByPathOptions options = new GetSongsByPathOptions(args);
                        WritableMap results = GetSongByPath.extractMetaDataFromFile(String.valueOf(options.path));
                        if(options.cover){
                            try{
                                String PathToCover = GetSongByPath.getCoverFromFile(String.valueOf(options.coverFolder), String.valueOf(options.path));
                                results.putString("cover", PathToCover );
                            }catch (Exception e){
                                e.printStackTrace();
                                results.putString("cover", "");
                            }

                        }
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.run();

    }

    @ReactMethod
    public void getAlbums(ReadableMap options, Promise callback) {

    }

    @ReactMethod
    public void getArtists(ReadableMap options, Promise callback) {

    }

    @ReactMethod
    public void getSongs(ReadableMap options, Promise callback) {

    }

    @ReactMethod
    public void search(ReadableMap args, Promise callback) {
        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        SearchOptions options = new SearchOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = searchDB(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.run();

    }

    @ReactMethod
    public void query(ReadableMap options, Promise callback) {

    }

}
