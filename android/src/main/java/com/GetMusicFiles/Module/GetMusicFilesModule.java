package com.GetMusicFiles.Module;

import android.content.ContentResolver;

import com.GetMusicFiles.Methods.GetAlbums;
import com.GetMusicFiles.Methods.GetArtists;
import com.GetMusicFiles.Methods.GetSongByPath;
import com.GetMusicFiles.Methods.GetSongs;
import com.GetMusicFiles.Models.Options.GetAlbumsOptions;
import com.GetMusicFiles.Models.Options.GetAllOptions;
import com.GetMusicFiles.Models.Options.GetArtistsOptions;
import com.GetMusicFiles.Models.Options.GetSongsByPathOptions;
import com.GetMusicFiles.Models.Options.GetSongsByPathsOptions;
import com.GetMusicFiles.Models.Options.GetSongsOptions;
import com.GetMusicFiles.Models.Options.SearchOptions;
import com.GetMusicFiles.Utils.SerialExecutor;
import com.GetMusicFiles.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.Objects;

import static com.GetMusicFiles.Methods.GetAll.getAllSongs;
import static com.GetMusicFiles.Methods.Search.searchDB;

public class GetMusicFilesModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private SerialExecutor executor;

    public GetMusicFilesModule(ReactApplicationContext reactContext, SerialExecutor exec) {
        super(reactContext);
        this.reactContext = reactContext;
        this.executor = exec;
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
                }, executor
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
                        if (options.cover) {
                            try {
                                String PathToCover = GetSongByPath.getCoverFromFile(String.valueOf(options.coverFolder), String.valueOf(options.path));
                                results.putString("cover", PathToCover);
                            } catch (Exception e) {
                                e.printStackTrace();
                                results.putString("cover", "");
                            }

                        }
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }, executor
        );

        runnable.run();

    }

    @ReactMethod
    public void getSongsByPaths(ReadableMap args, Promise callback) {

        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetSongsByPathsOptions options = new GetSongsByPathsOptions(args);
                        WritableMap results = GetSongByPath.extractMetaDataFromDirectory(
                                String.valueOf(options.path), options.minFileSize, options.maxFileSize, options.extensionFilter, options.cover, String.valueOf(options.coverFolder), options.sorted, options.batchSize, options.batchNumber);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }, executor
        );

        runnable.run();

    }

    @ReactMethod
    public void getAlbums(ReadableMap args, Promise callback) {
        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetAlbumsOptions options = new GetAlbumsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetAlbums.getAlbums(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }, executor
        );

        runnable.run();
    }

    @ReactMethod
    public void getArtists(ReadableMap args, Promise callback) {

        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetArtistsOptions options = new GetArtistsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetArtists.getArtists(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }, executor
        );

        runnable.run();

    }

    @ReactMethod
    public void getSongs(ReadableMap args, Promise callback) {
        Runnable runnable = new ToRunnable(
                () -> {
                    try {
                        GetSongsOptions options = new GetSongsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetSongs.getSongs(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }, executor
        );

        runnable.run();

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
                }, executor
        );

        runnable.run();

    }

    @ReactMethod
    public void query(ReadableMap options, Promise callback) {

    }

}
