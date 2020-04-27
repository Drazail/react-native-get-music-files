package com.GetMusicFiles.Module;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.GetMusicFiles.Utils.MetaDataExtractor;
import com.GetMusicFiles.Utils.SerialExecutor;
import com.GetMusicFiles.Utils.ToRunnable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;
import static android.util.Base64.*;


public class CoverImage extends SimpleViewManager<ReactImageView> {

    private SerialExecutor executor;

    public CoverImage(SerialExecutor exec){
        super();
        this.executor = exec;
    }

    public static final String REACT_CLASS = "RCTCoverImageView";
    String placeHolder = "https://images-na.ssl-images-amazon.com/images/I/51bMt-LGOyL.png";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ReactImageView createViewInstance(ThemedReactContext context) {
        return new ReactImageView(context, Fresco.newDraweeControllerBuilder(), null, null);
    }

    @ReactProp(name = "placeHolder")
    public void setPlaceHolder(ReactImageView view, String path) {
        placeHolder = path;
    }

    @ReactProp(name = "source")
    public void setSrc(ReactImageView view, String path) {
        Log.d("RNGMF", "setting source for: " + path);
        ToRunnable runnable = new ToRunnable(()->{
            try{
                WritableArray sources = new WritableNativeArray();
                WritableMap sourceMap = new WritableNativeMap();
                String base64 = Base64.encodeToString(MetaDataExtractor.getEmbededPicture(path), DEFAULT) ;
                sourceMap.putString("uri","data:image/jpg;base64,"+ base64 );
                sources.pushMap(sourceMap);
                view.setSource(sources);
                Log.d("RNGMF", "source set");
            }catch (Exception e){
                WritableArray sources = new WritableNativeArray();
                WritableMap sourceMap = new WritableNativeMap();
                e.printStackTrace();
                sourceMap.putString("uri",placeHolder );
                sources.pushMap(sourceMap);
                view.setSource(sources);
                Log.d("RNGMF", "source set to def");
            }
        }, this.executor);
        runnable.run();


    }
}
