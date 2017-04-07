package com.administrator.projectsdemon.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.administrator.projectsdemon.R;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.Utils;

public class WipeClothActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView after_img;
    private ImageView before_img;
    private Bitmap bitmap;
    private Bitmap alterBitmap;
    private Canvas canvas;
    private Paint paint;
    private RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wipe_cloth);
        initView();
        final ViewTreeObserver treeObserver = parent.getViewTreeObserver();
        if (treeObserver != null && treeObserver.isAlive()) {
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver aliveObserver = treeObserver;
                    if (!aliveObserver.isAlive()) {
                        aliveObserver = parent.getViewTreeObserver();
                    }
                    if (aliveObserver != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            aliveObserver.removeOnGlobalLayoutListener(this);
                        } else {
                            aliveObserver.removeGlobalOnLayoutListener(this);
                        }
                    }
                    initData();
                }
            });
        }
    }

    private void initData() {
        //此处的bitmap是只读形式
        Log.i("zuo","after_img.width="+after_img.getMeasuredWidth()+" after_img.height="+after_img.getMeasuredHeight());
//        Bitmap afterBitmap = getBitmap(R.drawable.after, after_img.getWidth(), after_img.getHeight());
//        Bitmap beforeBitmap = getBitmap(R.drawable.before, before_img.getWidth(), before_img.getHeight());

        Bitmap afterBitmap = ImageUtils.compressByScale(BitmapFactory.decodeResource(getResources(), R.drawable.after), after_img.getWidth(), after_img.getHeight());
        Bitmap beforeBitmap = ImageUtils.compressByScale(BitmapFactory.decodeResource(getResources(), R.drawable.before), before_img.getWidth(), before_img.getHeight());

        alterBitmap = Bitmap.createBitmap(beforeBitmap.getWidth(), beforeBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Log.i("zuo","beforeBitmap.width="+beforeBitmap.getWidth()+" beforeBitmap.height="+beforeBitmap.getHeight());
        Log.i("zuo","beforeBitmap.width="+beforeBitmap.getWidth()+" beforeBitmap.height="+beforeBitmap.getHeight());
        Log.i("zuo","alterBitmap.width="+alterBitmap.getWidth()+" alterBitmap.height="+alterBitmap.getHeight());
        canvas = new Canvas(alterBitmap);
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        canvas.drawBitmap(beforeBitmap, new Matrix(), paint);

        this.bitmap = Bitmap.createBitmap(beforeBitmap);
        after_img.setImageBitmap(afterBitmap);
        before_img.setImageBitmap(beforeBitmap);
        before_img.setOnTouchListener(this);
    }

    private Bitmap getBitmap(int resId,int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        options.inSampleSize = computeSimpleSize(options,reqWidth,reqHeight);
        Log.i("zuo","options.inSampleSize="+options.inSampleSize);
        options.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeResource(getResources(),resId,options);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        return bitmap;
    }

    private int computeSimpleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        if(outWidth > reqWidth || outHeight > reqHeight){
            int withRadio =  Math.round((float) outWidth / reqWidth);
            int heightRadio = Math.round((float) outHeight/reqHeight);
            return Math.max(withRadio, heightRadio);
        }
        return 1;
    }

    private void initView() {
        after_img = (ImageView) findViewById(R.id.after_img);
        before_img = (ImageView) findViewById(R.id.before_img);
        parent = (RelativeLayout) findViewById(R.id.activity_wipe_cloth);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int left = before_img.getLeft();
        int top = before_img.getTop();
        int right = before_img.getRight();
        int bottom = before_img.getBottom();
        switch(event.getAction()){
           case MotionEvent.ACTION_DOWN:
                break;
           case MotionEvent.ACTION_MOVE:
               int x = (int) event.getX();
               int y = (int) event.getY();

               Log.i("zuo","left="+left+" right="+right+" top="+top+" bottom="+bottom);
               Log.i("zuo","x="+x+" y="+y);

               for (int i = -20;i<20;i++){
                   for (int j = -20; j < 20; j++) {
                       if(i+x>=left && j+y>=top && x+i<right && y+j<bottom){
                           alterBitmap.setPixel(x+i,y+j, Color.TRANSPARENT);
                       }
                   }
               }
               Log.i("zuo","===============================================================");
               before_img.setImageBitmap(alterBitmap);
               break;
           case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}
