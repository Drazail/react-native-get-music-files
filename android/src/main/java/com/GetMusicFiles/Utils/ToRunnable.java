package com.GetMusicFiles.Utils;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class ToRunnable implements Runnable {

    private Runnable task;
    public ToRunnable(Runnable task) {
        this.task = task;
    }

    public void run() {
        this.task.run();
    }
}

