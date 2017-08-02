package tw.brad.android.games.myjavatest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.mybg);
        res = context.getResources();
        ballBmp = BitmapFactory.decodeResource(res,R.drawable.ball);

    }

    private void init(){
        isInit = true;
        viewW = getWidth(); viewH = getHeight();




    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();


        canvas.drawBitmap(ballBmp, 0, 0, null);


    }
}
