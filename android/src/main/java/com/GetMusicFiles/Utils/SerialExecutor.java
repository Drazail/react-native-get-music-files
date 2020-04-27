package com.GetMusicFiles.Utils;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialExecutor {

    private ExecutorService exService;

    public SerialExecutor(){
        this.exService = Executors.newSingleThreadExecutor();
    }

    public void execute (Runnable runnable){
        if (exService.isShutdown()|| exService.isTerminated()){
            Log.d("RNGMF", "exService is down");
            this.exService = Executors.newFixedThreadPool(1);
            Log.d("RNGMF", "exService is up and running");
        }
        this.exService.execute(runnable);
        Log.d("RNGMF", "exService is up and running");
    }

    public void shutDown(){
        this.exService.shutdown();
    }
}
