package com.administrator.projectsdemon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.administrator.projectsdemon.R;

import java.lang.ref.WeakReference;

/**
 * 作者：zuo
 * 时间：2017/4/5:10:57
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap mMaskBitmap;
    private WeakReference<Bitmap> mWeakBitmap;
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    //图片相关属性
    private static final int BODER_RADIUS_DEFAULT = 10;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private int radius;
    private int type;
    private Paint paint;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);

        //获取相关属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        radius = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_Radius, BODER_RADIUS_DEFAULT);
        type = typedArray.getInt(R.styleable.CircleImageView_type, TYPE_CIRCLE);
        typedArray.recycle();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(min, min);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

            Canvas drawCanvas = new Canvas(bitmap);
            float scale = 1.0f;
            if (type == TYPE_ROUND) {
                scale = Math.max(getWidth() * 1.0f / intrinsicWidth, getHeight() * 1.0f / intrinsicHeight);
            } else {
                scale = getWidth() * 1.0f / Math.min(intrinsicWidth, intrinsicHeight);
            }
            drawable.setBounds(0, 0, (int) scale * intrinsicWidth, (int) scale * intrinsicHeight);
            drawable.draw(drawCanvas);

            if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                mMaskBitmap = getBitmap();
            }

            paint.reset();
            paint.setFilterBitmap(false);
            paint.setXfermode(xfermode);
            //绘制形状
            drawCanvas.drawBitmap(mMaskBitmap, 0, 0, paint);
            //存储bitmap
            mWeakBitmap = new WeakReference<>(bitmap);
            //绘制图片
            canvas.drawBitmap(bitmap, 0, 0, null);
            paint.setXfermode(null);
        } else {
            paint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }

    private Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, paint);
        } else {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        }
        return bitmap;
    }
}
