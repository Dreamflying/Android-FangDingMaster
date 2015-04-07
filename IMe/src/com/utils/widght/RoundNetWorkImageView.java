package com.utils.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.project.iwant.R;

public class RoundNetWorkImageView extends NetworkImageView {
	 private final RectF roundRect = new RectF();
	    private float rectRadiusX = 20;
	    private float rectRadiusY = 20;
	    private final Paint maskPaint = new Paint();
	    private final Paint zonePaint = new Paint();
	public RoundNetWorkImageView(Context context) {
		super(context);
	}

	public RoundNetWorkImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.RoundImageView);
            rectRadiusX = a.getDimension(
                    R.styleable.RoundImageView_rectRadiusX, rectRadiusX);
            rectRadiusY = a.getDimension(
                    R.styleable.RoundImageView_rectRadiusY, rectRadiusY);
            a.recycle();
        } else {
            float density = getResources().getDisplayMetrics().density;
            rectRadiusX = rectRadiusX * density;
            rectRadiusY = rectRadiusY * density;
        }

        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
	}

	public RoundNetWorkImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setRectAdius(float radiusX, float radiusY) {
        rectRadiusX = radiusX;
        rectRadiusY = radiusY;
        invalidate();
    }

    @ Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @ Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rectRadiusX, rectRadiusY, zonePaint);

        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
    }

}
