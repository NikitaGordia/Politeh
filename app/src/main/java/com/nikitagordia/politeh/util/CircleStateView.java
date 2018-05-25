package com.nikitagordia.politeh.util;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nikitagordia.politeh.R;

/**
 * Created by nikitagordia on 4/30/18.
 */

public class CircleStateView extends FrameLayout {

    private static final float SHADOW_PERCENT = 0.03f;
    private static final float FONT_PERCENT = 0.15f;
    private static final float FONT_PERCENT_DELTA = 0.87f;

    private boolean isFirst = true, isCreated;
    private float a, b, c, halfHeight, small, normal, big, shadow, shadowPadding;

    private TextView first, second;

    public CircleStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = inflate(getContext(), R.layout.layout_circle_state_view, this);
        first = (TextView) v.findViewById(R.id.first);
        second = (TextView) v.findViewById(R.id.second);

        first.setText(R.string.one);
        second.setText(R.string.two);

        isCreated = false;

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isCreated) {
                    updateProp();
                    update(0);
                    isCreated = true;
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        updateProp();
        update(0);
    }

    public void setState(float state) {
        if (state < 0 || state > 1) throw new IllegalArgumentException("state must be in range [0, 1]");

        updateProp();
        update(state);
    }

    public void update(float state) {

        second.setAlpha(Math.max(state, 0.4f));
        first.setAlpha(Math.max(1 - state, 0.4f));
        if (state < 0.5f) {
            float ratio = state / 0.5f;
            transform(b * (2 - ratio) + shadow, halfHeight + shadow, normal + (big - normal) * (1 - ratio), first);
            transform(getW() - b * (2 - ratio) + shadow, halfHeight + shadow, normal - (normal - small) * (1 - ratio), second);
        } else {
            float ratio = (state - 0.5f) / 0.5f;
            transform(b * (1 + ratio) + shadow, halfHeight + shadow, normal - (normal - small) * ratio, first);
            transform(getW() - b * (1 + ratio) + shadow, halfHeight + shadow, normal + (big - normal) * ratio, second);
        }
    }

    private void transform(float x, float y, float r, TextView v) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.width = (int)(r * 2);
        params.height = (int)(r * 2);
        v.setLayoutParams(params);

        if ( Build.VERSION.SDK_INT >= 21) v.setElevation(shadow + shadow * ((r - small) / (big - small)));

        v.setTextSize(getH() * FONT_PERCENT + FONT_PERCENT_DELTA * (r - small));

        v.setX(x - r);
        v.setY(y - r);
    }

    public void updateProp() {
        shadow = getH() * SHADOW_PERCENT;
        shadowPadding = shadow * 3;
        b = getH() / 2.5f;
        a = getW() / 2f - 2f * b;
        c = b / 2f;
        halfHeight = getH() / 2f;
        normal = b;
        small = normal * 0.75f;
        big = normal * 1.25f;
    }

    public float getH() {
        return getHeight() - shadowPadding * 2;
    }

    public float getW() {
        return getWidth() - shadowPadding * 2;
    }
}

