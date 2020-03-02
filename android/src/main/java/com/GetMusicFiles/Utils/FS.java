package com.GetMusicFiles.Utils;

import android.util.Log;

import androidx.annotation.NonNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.GetMusicFiles.Utils.GeneralUtils.LOG;

public class FS {

    public static String saveToStorage(@NonNull String pathToImg, @NonNull byte[] imageBytes) throws IOException {
        File f = new File(pathToImg);
        File filePath = new File(pathToImg+ "/" + UUID.randomUUID().toString());

        if(f.exists() && !f.isDirectory()){
            Log.e(LOG, "coverPath is a file");
            throw new IOException("coverPath is a file");
        }
        if(!f.exists()){
            f.mkdir();
            Log.d(LOG, "coverFolder created");
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, false);
            fos.write(imageBytes);
        } catch (IOException e) {
            if (fos != null) {
                fos.flush();
                fos.close();
            }
            throw new IOException(e);
        } finally {
            if (fos != null) {
                fos.flush();
                fos.close();
                return  filePath.getAbsolutePath();
            }
        }
        return "";
    }
}
