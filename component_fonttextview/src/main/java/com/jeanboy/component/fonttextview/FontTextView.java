package com.jeanboy.component.fonttextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by jianbo on 2023/7/18 14:39.
 */
public class FontTextView extends AppCompatTextView {

    public FontTextView(@NonNull Context context) {
        this(context, null);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        try {
            int fontName = typedArray.getInt(R.styleable.FontTextView_font_name, 0);
            int fontWeight = typedArray.getInt(R.styleable.FontTextView_font_weight, 400);
            boolean fontPadding = typedArray.getBoolean(R.styleable.FontTextView_font_padding, false);
            int lineHeight = typedArray.getDimensionPixelSize(R.styleable.FontTextView_line_height, 0);

            setIncludeFontPadding(fontPadding);

            int currentLineHeight = getLineHeight();
            if (lineHeight <= 0) {
                lineHeight = currentLineHeight;
            }
            setLineHeight(lineHeight);
            updateTypeface(fontName, fontWeight);
        } finally {
            typedArray.recycle();
        }
    }

    private void updateTypeface(int name, int weight) {
        FontHelper.get().replaceFont(this, name, weight);
    }
}
