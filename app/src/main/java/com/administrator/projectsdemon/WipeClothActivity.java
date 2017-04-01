package com.administrator.projectsdemon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class WipeClothActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView after_img;
    private ImageView before_img;
    private Bitmap bitmap;
    private Bitmap alterBitmap;
    private Canvas canvas;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wipe_cloth);
        initView();
        initData();
    }

    private void initData() {
        //此处的bitmap是只读形式
        Bitmap afterBitmap = getBitmap(R.drawable.after, 500, 800);
        Bitmap beforeBitmap = getBitmap(R.drawable.before, 500, 800);


        alterBitmap = Bitmap.createBitmap(beforeBitmap.getWidth(), beforeBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        canvas = new Canvas(alterBitmap);
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        canvas.drawBitmap(beforeBitmap, new Matrix(), paint);

        bitmap = Bitmap.createBitmap(beforeBitmap);
        after_img.setImageBitmap(afterBitmap);
        before_img.setImageBitmap(beforeBitmap);
        before_img.setOnTouchListener(this);
    }

    private Bitmap getBitmap(int resId,int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
//        options.inSampleSize = computeSimpleSize(options,reqWidth,reqHeight);
        options.inSampleSize = 3;
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

        int withRadio = (int) Math.round(outWidth * 0.1 / reqWidth);
        int heightRadio = (int)Math.round(outHeight*0.1/reqHeight);
        return Math.max(withRadio, heightRadio);
    }

    private void initView() {
        after_img = (ImageView) findViewById(R.id.after_img);
        before_img = (ImageView) findViewById(R.id.before_img);
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
               before_img.setImageBitmap(alterBitmap);
               break;
           case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
