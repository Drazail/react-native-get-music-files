package com.GetMusicFiles;

import com.GetMusicFiles.Module.CoverImage;
import com.GetMusicFiles.Module.GetMusicFilesModule;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetMusicFiles implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new GetMusicFilesModule(reactContext));
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }


    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(new CoverImage());
        return viewManagers;
    }
}
