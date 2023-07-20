package com.jeanboy.component.fonttextview;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianbo on 2023/7/19 14:25.
 */
public class FontHelper {

    private final Map<String, SoftReference<Typeface>> typeFaceCache = new HashMap<>();

    /**
     * key：font_weight
     * value：font_path
     */
    private static final Map<String, String> fontMap = new ArrayMap<>();

    private static volatile FontHelper instance;

    private FontHelper() {
        fontMap.put("0_400", "fonts/Roboto/Roboto-Regular.ttf");
        fontMap.put("0_500", "fonts/Roboto/Roboto-Medium.ttf");
        fontMap.put("0_700", "fonts/Roboto/Roboto-Bold.ttf");
        fontMap.put("1_400", "fonts/SourceHanSansCN/SourceHanSansCN-Regular.otf");
        fontMap.put("1_500", "fonts/SourceHanSansCN/SourceHanSansCN-Medium.otf");
        fontMap.put("1_700", "fonts/SourceHanSansCN/SourceHanSansCN-Bold.otf");
    }

    public static FontHelper get() {
        if (instance == null) {
            synchronized (FontHelper.class) {
                if (instance == null) {
                    instance = new FontHelper();
                }
            }
        }
        return instance;
    }

    private Typeface createTypefaceFromAsset(Context context, String fontPath) {
        SoftReference<Typeface> typefaceRef = typeFaceCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceRef = new SoftReference<>(typeface);
            typeFaceCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    public void replaceFont(FontTextView textView, int name, int weight) {
        String fontPath = fontMap.get(name + "_" + weight);
        if (!TextUtils.isEmpty(fontPath)) {
            Typeface typeface = createTypefaceFromAsset(textView.getContext(), fontPath);
            Typeface currentFace = textView.getTypeface();
            int style = Typeface.NORMAL;
            if (currentFace != null) {
                style = currentFace.getStyle();
            }
            textView.setTypeface(typeface, style);
        }
    }
}
