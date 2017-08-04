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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
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
    private LinkedList<BallTask> balls;

    private GestureDetector gd;


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.mybg);
        res = context.getResources();
         ballBmp = new Bitmap[ballRes.length];
        for (int i=0; i<ballRes.length; i++) {
            ballBmp[i] = BitmapFactory.decodeResource(res, ballRes[i]);
        }

        matrix = new Matrix();
        //timer = new Timer();

        balls = new LinkedList<>();

        gd = new GestureDetector(context, new MyGestureListener());


    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i("brad", "onDown");
            return true; //super.onDown(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //Log.i("brad", "onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {
            if (Math.abs(vX) > Math.abs(vY)){
                if (vX > 0){
                    Log.i("brad", "Right");
                }else{
                    Log.i("brad", "Left");
                }
            }else{
                if (vY > 0){
                    Log.i("brad", "Down");
                }else{
                    Log.i("brad", "Up");
                }
            }
            return super.onFling(e1, e2, vX, vY);
        }
    }


    public void setTimer(Timer timer){
        this.timer = timer;
        timer.schedule(new RefreshView(), 0, 30);
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

    }

    private class RefreshView extends TimerTask {
        @Override
        public void run() {
            postInvalidate();
        }
    }

    private class BallTask extends TimerTask {
        float ballX, ballY, dx, dy;
        int intBall;

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
    public boolean onTouchEvent(MotionEvent event) {
//        float ex = event.getX(), ey = event.getY();
//        BallTask ball = new BallTask((int)(Math.random()*4), ex, ey, 16, 16);
//        balls.add(ball);
//        timer.schedule(ball, 0, 30);
//        return false; // super.onTouchEvent(event);


        return gd.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        for (BallTask ball : balls) {
            canvas.drawBitmap(ballBmp[ball.intBall], ball.ballX, ball.ballY, null);
        }


    }
}
