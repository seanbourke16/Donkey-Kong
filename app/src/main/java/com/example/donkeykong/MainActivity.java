package com.example.donkeykong;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DonkeyView donkeyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        donkeyView = new DonkeyView(this);
        setContentView(donkeyView);
    }

    class DonkeyView extends SurfaceView implements Runnable {
        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        boolean paused = true;
        Canvas canvas;
        Paint paint;
        int y;
        int posx, posy;
        int dx, dy;
        int height, width;
        ArrayList<Girder> g;
        Mario m;
        ArrayList<Barrel> b;

        private long thisTimeFrame;
        public DonkeyView(Context context) {
            super(context);

            ourHolder = getHolder();
            paint = new Paint();
        }

        @Override
        public void run() {
            posx = 50;
            posy = 50;
            dx = 20;
            dy = 45;

           g.add(new Girder(width,0,height,height-10,1));
           int barrels=1;

            while (playing)
            {
                while(g.get(0).sy>height){
                    g.remove(g.get(0));
                    if(g.get(g.size()-1).dir==0){
                        g.add(new Girder(g.get(g.size()-1).fx-10,0,g.get(g.size()-1).fy-15,g.get(g.size()-1).fy+50,1));
                    }
                    else{
                        g.add(new Girder(g.get(g.size()-1).fx-));
                    }
                }
                if (!paused) {
                    update();
                }
                draw();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
            }
        }
        public void update() {
            y = y + 5;
            if (y > 200)
                y = 5;

            posx += dx;
            posy += dy;
            if ((posx > width) || (posx < 0))
                dx = -dx;
            if ((posy > height) || (posy < 0))
                dy = -dy;
            //g.get(0).update();

        }
        public void draw() {
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                width = canvas.getWidth();
                height = canvas.getHeight();

                // Draw the background color
                canvas.drawColor(Color.argb(255, 26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawLine(0, 0, 300, y, paint);


                // canvas.drawCircle(posx, posy, 30l, paint);
                //g.get(0).draw(canvas,paint);

                // canvas.drawCircle(b.x, b.y, 50, paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN)
                paused = !paused;
            return true;
        }
    }


    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        donkeyView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        donkeyView.pause();
    }

}

/*public class MainActivity extends AppCompatActivity {

    DonkeyView donkeyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        donkeyView=new DonkeyView(this);
        setContentView(donkeyView);
    }

    class DonkeyView extends SurfaceView implements Runnable{
        Thread gameThread=null;
        SurfaceHolder ourHolder;
        Paint paint;
        Canvas canvas;
        int height;
        int width;
        boolean playing;
        //ArrayList<Barrel> b;
        //Mario m;
        //ArrayList<Ladder> l;
        ArrayList<Girder> g;

        public DonkeyView(Context context){
            super(context);
            ourHolder=getHolder();
            paint=new Paint();
            playing=true;
        }

        @Override
        public void run(){
            g.add(new Girder(50,30,30,40));
            resume();
            while(playing){
                draw();
            }
        }

        public void draw(){
            if(ourHolder.getSurface().isValid()){
                canvas=ourHolder.lockCanvas();
                width=canvas.getWidth();
                height=canvas.getHeight();
                canvas.drawColor(Color.argb(255,255,0,0));
                g.get(0).draw(canvas,paint);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            //Log.e("Click","click");
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                //Log.e("Click","if");
                if(paused) {
                    paused = !paused;
                    return true;
                }
            }
            return true;
        }

        public void pause() {
            //playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        donkeyView.resume();
    }

    // This method executes when the player quits the game
    //@Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        donkeyView.pause();
    }*/
//}
