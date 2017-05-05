package com.administrator.projectsdemon.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.administrator.projectsdemon.R;

public class AutoScaleTextView extends android.support.v7.widget.AppCompatTextView
{
    private Paint textPaint;

	private float preferredTextSize;
	private float minTextSize;

	private float orgMinTextSize;

	private float oldpreferredTextSize;

	public AutoScaleTextView(Context context)
	{
		this(context, null);
	}

	public AutoScaleTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, R.attr.autoScaleTextViewStyle);

		// Use this constructor, if you do not want use the default style
		// super(context, attrs);
	}

	public AutoScaleTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		this.textPaint = new Paint();

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScaleTextView, defStyle, 0);
		this.minTextSize = a.getDimension(R.styleable.AutoScaleTextView_minTextSize, 10f);
		
		a.recycle();

		this.preferredTextSize = this.getTextSize();
		
		//将原本的数据另存一份
		this.orgMinTextSize = minTextSize;
		this.oldpreferredTextSize = preferredTextSize;
	}

	/**
	 * Set the minimum text size for this view
	 * 
	 * @param minTextSize
	 *            The minimum text size
	 */
	public void setMinTextSize(float minTextSize)
	{
		this.minTextSize = minTextSize;
	}

	/**
	 * Resize the text so that it fits
	 * 
	 * @param text
	 *            The text. Neither <code>null</code> nor empty.
	 * @param textWidth
	 *            The width of the TextView. > 0
	 */
	private void refitText(String text, int textWidth)
	{
		if (textWidth <= 0 || text == null || text.length() == 0)
			return;

		// the width
		int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

		final float threshold = 0.5f; // How close we have to be

		this.textPaint.set(this.getPaint());
		int count = 0;
		while ((this.preferredTextSize - this.minTextSize) > threshold)
		{
			float size = (this.preferredTextSize + this.minTextSize) / 2;
			this.textPaint.setTextSize(size);
			if (this.textPaint.measureText(text) >= targetWidth)
				this.preferredTextSize = size; // too big
			else
				this.minTextSize = size; // too small
			Log.i("zuo", "while执行了"+(count++)+"次:"+"size="+size+"preferredTextSize="+preferredTextSize+"minTextSize="+minTextSize);
		}
		Log.i("zuo", "ontextchange方法执行了"+"preferredTextSize="+preferredTextSize+"text="+text);
		// Use min size so that we undershoot rather than overshoot
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.minTextSize);
		//上面采用二分法进行数据逼近，数据已经改变，故要将数据还原
		minTextSize = orgMinTextSize;
		preferredTextSize = oldpreferredTextSize;
	}

	@Override
	protected void onTextChanged(final CharSequence text, final int start, final int before, final int after)
	{
		this.refitText(text.toString(), this.getWidth());
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldwidth, int oldheight)
	{
		if (width != oldwidth)
			this.refitText(this.getText().toString(), width);
	}
}