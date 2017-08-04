package tw.brad.android.games.myjavatest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/2.
 */

public class MyView extends View {
    private Resources res;
    private Bitmap[] ballBmp;
    private int[] ballRes = {R.drawable.ball0, R.drawable.ball1,R.drawable.ball2,R.drawable.ball3};
    private int viewW, viewH;
    private boolean isInit;
    private float ballW, ballH;
    private Matrix matrix;
    private Timer timer;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.mybg);
        res = context.getResources();
        for (int i=0; i<ballRes.length; i++) {
            ballBmp[i] = BitmapFactory.decodeResource(res, ballRes[i]);
        }

        matrix = new Matrix();
        //timer = new Timer();

    }

    public void setTimer(Timer timer){
        this.timer = timer;
        timer.schedule(new BallTask(), 1000, 30);
    }

    //public Timer getTimer(){return timer;}
    private void init(){
        isInit = true;
        viewW = getWidth(); viewH = getHeight();

        ballW = viewW / 8f; ballH = ballW;
        matrix.reset();
        matrix.postScale(ballW / ballBmp[0].getWidth(),
                ballH / ballBmp[0].getHeight());

        for (int i=0; i<ballBmp.length; i++) {
            ballBmp[i] = Bitmap.createBitmap(
                    ballBmp[i], 0, 0, ballBmp[i].getWidth(), ballBmp[i].getHeight(),
                    matrix, false);
        }

        dx = dy = 16;

    }

    private class BallTask extends TimerTask {
        private float ballX, ballY, dx, dy;
        private int intBall;

        BallTask(int intBall, float ballX, float ballY, float dx, float dy){
            this.intBall = intBall;
            this.ballX = ballX;
            this.ballY = ballY;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void run() {
            if (ballX<0 || ballX + ballW > viewW){
                dx *= -1;
            }
            if (ballY<0 || ballY + ballH > viewH){
                dy *= -1;
            }
            ballX += dx;
            ballY += dy;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        canvas.drawBitmap(ballBmp, ballX, ballY, null);


    }
}
