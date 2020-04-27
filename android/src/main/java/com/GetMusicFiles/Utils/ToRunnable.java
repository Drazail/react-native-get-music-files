package com.GetMusicFiles.Utils;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class ToRunnable implements Runnable {

    private Runnable task;
    private SerialExecutor exec;
    public ToRunnable(Runnable task, SerialExecutor executor) {
        this.task = task;
        this.exec = executor;
    }

    public void run() {
        exec.execute(this.task);
    }
}

