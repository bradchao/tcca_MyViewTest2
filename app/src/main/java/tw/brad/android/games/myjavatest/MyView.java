package tw.brad.android.games.myjavatest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/8/2.
 */

public class MyView extends View {
    private Resources res;
    private Bitmap ballBmp;
    private int viewW, viewH;
    private boolean isInit;
    private float ballW, ballH;
    private Matrix matrix;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.mybg);
        res = context.getResources();
        ballBmp = BitmapFactory.decodeResource(res,R.drawable.ball);
        matrix = new Matrix();
    }

    private void init(){
        isInit = true;
        viewW = getWidth(); viewH = getHeight();

        ballW = viewW / 2f; ballH = ballW;
        matrix.reset();
        matrix.postScale(ballW / ballBmp.getWidth(),
                ballH / ballBmp.getHeight());
        ballBmp = Bitmap.createBitmap(
                ballBmp,0,0,ballBmp.getWidth(),ballBmp.getHeight(),
                matrix, false);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        canvas.drawBitmap(ballBmp, 0, 0, null);


    }
}
