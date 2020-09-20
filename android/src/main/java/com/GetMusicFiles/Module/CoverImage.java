package com.GetMusicFiles.Module;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.GetMusicFiles.Utils.MetaDataExtractor;
import com.GetMusicFiles.Utils.ToRunnable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageLoadEvent;
import com.facebook.react.views.image.ImageResizeMethod;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.yoga.YogaConstants;

import java.io.IOException;
import java.util.Map;

import static android.util.Base64.*;


public class CoverImage extends SimpleViewManager<ReactImageView> {

    public static final String REACT_CLASS = "RCTCoverImageView";

    private @Nullable
    AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private @Nullable
    GlobalImageLoadListener mGlobalImageLoadListener;
    private final @Nullable Object mCallerContext;

    String placeHolder = null;

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }


    public CoverImage(
            AbstractDraweeControllerBuilder draweeControllerBuilder, Object callerContext) {
        this(draweeControllerBuilder, null, callerContext);
    }

    public CoverImage(
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            @Nullable GlobalImageLoadListener globalImageLoadListener,
            Object callerContext) {
        mDraweeControllerBuilder = draweeControllerBuilder;
        mGlobalImageLoadListener = globalImageLoadListener;
        mCallerContext = callerContext;
    }

    public CoverImage() {
        // Lazily initialize as FrescoModule have not been initialized yet
        mDraweeControllerBuilder = null;
        mCallerContext = null;
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        if (mDraweeControllerBuilder == null) {
            mDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        }
        return mDraweeControllerBuilder;
    }

    public Object getCallerContext() {
        return mCallerContext;
    }

    @NonNull
    @Override
    public ReactImageView createViewInstance(ThemedReactContext context) {
        return new ReactImageView(
                context, getDraweeControllerBuilder(), mGlobalImageLoadListener, getCallerContext());
    }

    // In JS this is CoverImage.props.src
    @ReactProp(name = "src")
    public void setSource(ReactImageView view, @Nullable ReadableArray sources) {
        view.setSource(sources);
    }

    // In JS this is CoverImage.props.source
    @ReactProp(name = "source")
    public void setSrc(ReactImageView view, String path) {
        Log.d("RNGMF", "setting source for: " + path);
        ToRunnable runnable = new ToRunnable(()->{
                WritableArray sources = new WritableNativeArray();
                WritableMap sourceMap = new WritableNativeMap();
                try {
                    String base64 = Base64.encodeToString(MetaDataExtractor.getEmbededPicture(path), DEFAULT) ;
                    sourceMap.putString("uri","data:image/jpg;base64,"+ base64 );
                    sources.pushMap(sourceMap);
                }catch (Exception e){
                    Log.e("RNGMF", "404");
                }
                view.setSource(sources);
        });
        runnable.run();
    }
    
    // In JS this is CoverImage.props.blurRadius
    @ReactProp(name = "blurRadius")
    public void setBlurRadius(ReactImageView view, float blurRadius) {
        view.setBlurRadius(blurRadius);
    }

    // In JS this is CoverImage.props.defaultSrc
    @ReactProp(name = "defaultSrc")
    public void setDefaultSource(ReactImageView view, @Nullable String source) {
        view.setDefaultSource(source);
    }

    // In JS this is CoverImage.props.loadingIndicatorSrc
    @ReactProp(name = "loadingIndicatorSrc")
    public void setLoadingIndicatorSource(ReactImageView view, @Nullable String source) {
        view.setLoadingIndicatorSource(source);
    }
    
    // In JS this is CoverImage.props.borderColor
    @ReactProp(name = "borderColor", customType = "Color")
    public void setBorderColor(ReactImageView view, @Nullable Integer borderColor) {
        if (borderColor == null) {
            view.setBorderColor(Color.TRANSPARENT);
        } else {
            view.setBorderColor(borderColor);
        }
    }

    // In JS this is CoverImage.props.overlayColor
    @ReactProp(name = "overlayColor", customType = "Color")
    public void setOverlayColor(ReactImageView view, @Nullable Integer overlayColor) {
        if (overlayColor == null) {
            view.setOverlayColor(Color.TRANSPARENT);
        } else {
            view.setOverlayColor(overlayColor);
        }
    }

    // In JS this is CoverImage.props.borderWidth
    @ReactProp(name = "borderWidth")
    public void setBorderWidth(ReactImageView view, float borderWidth) {
        view.setBorderWidth(borderWidth);
    }

    // In JS this is CoverImage.props.borderRadius
    @ReactPropGroup(
            names = {
                    ViewProps.BORDER_RADIUS,
                    ViewProps.BORDER_TOP_LEFT_RADIUS,
                    ViewProps.BORDER_TOP_RIGHT_RADIUS,
                    ViewProps.BORDER_BOTTOM_RIGHT_RADIUS,
                    ViewProps.BORDER_BOTTOM_LEFT_RADIUS
            },
            defaultFloat = YogaConstants.UNDEFINED)
    public void setBorderRadius(ReactImageView view, int index, float borderRadius) {
        if (!YogaConstants.isUndefined(borderRadius)) {
            borderRadius = PixelUtil.toPixelFromDIP(borderRadius);
        }

        if (index == 0) {
            view.setBorderRadius(borderRadius);
        } else {
            view.setBorderRadius(borderRadius, index - 1);
        }
    }

    // In JS this is CoverImage.props.resizeMode
    @ReactProp(name = ViewProps.RESIZE_MODE)
    public void setResizeMode(ReactImageView view, @Nullable String resizeMode) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
        view.setTileMode(ImageResizeMode.toTileMode(resizeMode));
    }

    // In JS this is CoverImage.props.resizeMethod
    @ReactProp(name = ViewProps.RESIZE_METHOD)
    public void setResizeMethod(ReactImageView view, @Nullable String resizeMethod) {
        if (resizeMethod == null || "auto".equals(resizeMethod)) {
            view.setResizeMethod(ImageResizeMethod.AUTO);
        } else if ("resize".equals(resizeMethod)) {
            view.setResizeMethod(ImageResizeMethod.RESIZE);
        } else if ("scale".equals(resizeMethod)) {
            view.setResizeMethod(ImageResizeMethod.SCALE);
        } else {
            throw new JSApplicationIllegalArgumentException(
                    "Invalid resize method: '" + resizeMethod + "'");
        }
    }

    // In JS this is CoverImage.props.tintColor
    @ReactProp(name = "tintColor", customType = "Color")
    public void setTintColor(ReactImageView view, @Nullable Integer tintColor) {
        if (tintColor == null) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        }
    }
    
    // In JS this is CoverImage.props.progressiveRenderingEnabled
    @ReactProp(name = "progressiveRenderingEnabled")
    public void setProgressiveRenderingEnabled(ReactImageView view, boolean enabled) {
        view.setProgressiveRenderingEnabled(enabled);
    }

    // In JS this is CoverImage.props.fadeDuration
    @ReactProp(name = "fadeDuration")
    public void setFadeDuration(ReactImageView view, int durationMs) {
        view.setFadeDuration(durationMs);
    }

    // In JS this is CoverImage.props.shouldNotifyLoadEvents
    @ReactProp(name = "shouldNotifyLoadEvents")
    public void setLoadHandlersRegistered(ReactImageView view, boolean shouldNotifyLoadEvents) {
        view.setShouldNotifyLoadEvents(shouldNotifyLoadEvents);
    }
    
   // In JS this is CoverImage.props.headers
    @ReactProp(name = "headers")
    public void setHeaders(ReactImageView view, ReadableMap headers) {
        view.setHeaders(headers);
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                ImageLoadEvent.eventNameForType(ImageLoadEvent.ON_LOAD_START),
                MapBuilder.of("registrationName", "onLoadStart"),
                ImageLoadEvent.eventNameForType(ImageLoadEvent.ON_LOAD),
                MapBuilder.of("registrationName", "onLoad"),
                ImageLoadEvent.eventNameForType(ImageLoadEvent.ON_ERROR),
                MapBuilder.of("registrationName", "onError"),
                ImageLoadEvent.eventNameForType(ImageLoadEvent.ON_LOAD_END),
                MapBuilder.of("registrationName", "onLoadEnd"));
    }

    @Override
    protected void onAfterUpdateTransaction(ReactImageView view) {
        super.onAfterUpdateTransaction(view);
        view.maybeUpdateView();
    }
}
