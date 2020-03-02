package com.GetMusicFiles.Module;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.GetMusicFiles.Utils.MetaDataExtractor;
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


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactImageView createViewInstance(ThemedReactContext context) {
        return new ReactImageView(context, Fresco.newDraweeControllerBuilder(), null, null);
    }

    @ReactProp(name = "src")
    public void setSrc(ReactImageView view, String path) {
        WritableArray sources = new WritableNativeArray();
        WritableMap sourceMap = new WritableNativeMap();
        try{
            String base64 = Base64.encodeToString(MetaDataExtractor.getEmbededPicture(path), DEFAULT) ;
            sourceMap.putString("uri","data:image/jpg;base64,"+ base64 );
            sources.pushMap(sourceMap);
            view.setSource(sources);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @ReactProp(name = "borderRadius", defaultFloat = 0f)
    public void setBorderRadius(ReactImageView view, float borderRadius) {
        view.setBorderRadius(borderRadius);
    }

    @ReactProp(name = ViewProps.RESIZE_MODE)
    public void setResizeMode(ReactImageView view, @Nullable String resizeMode) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
    }
}
