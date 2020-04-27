package com.GetMusicFiles.Models.ReturnObjects;

import android.net.Uri;

public class Album {

    String title;
    String[] artists;
    int numberOfSongs;

    public Album(String id, String title, String[] artists, Uri artwork, Uri blurred, int numberOfSongs) {
        this.title = title;
        this.artists = artists;
        this.numberOfSongs = numberOfSongs;
    }
}
