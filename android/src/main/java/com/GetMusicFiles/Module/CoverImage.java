package com.GetMusicFiles.Module;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.GetMusicFiles.Utils.MetaDataExtractor;
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

    public static final String REACT_CLASS = "RCTCoverImageView";
    String placeHolder = "https://images-na.ssl-images-amazon.com/images/I/51bMt-LGOyL.png";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

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
        WritableArray sources = new WritableNativeArray();
        WritableMap sourceMap = new WritableNativeMap();
        ToRunnable getImageAsBase64 = new ToRunnable(()->{
            try{
                String base64 = Base64.encodeToString(MetaDataExtractor.getEmbededPicture(path), DEFAULT) ;
                sourceMap.putString("uri","data:image/jpg;base64,"+ base64 );
                sources.pushMap(sourceMap);
                view.setSource(sources);
            }catch (Exception e){
                e.printStackTrace();
                sourceMap.putString("uri",placeHolder );
                sources.pushMap(sourceMap);
                view.setSource(sources);
            }
        });
        getImageAsBase64.run();
    }
}
