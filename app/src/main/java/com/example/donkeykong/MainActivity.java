package com.example.donkeykong;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.StrictMath.abs;

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

            Random r=new Random();
            posx = 50;
            posy = 50;
            dx = 20;
            dy = 45;

            g=new ArrayList<>();
            m=new Mario();

            //g.add(new Girder(10,20,30,40,1));

           //g.add(new Girder3(0,width,height-10,height,1));
           int barrels=1;

            while (playing)
            {
                draw();
                height=m.height;
                width=m.width;
                if(g.size()!=0)Log.e("Size",""+(g.get(g.size()-1).ly>0)+"Height "+height);
                while(height!=0&&(g.size()==0||(g.get(g.size()-1).ly>0&&g.get(g.size()-1).ry>0))){
                    Log.e("Size",""+g.size());
                    if(g.size()!=0) {
                        //g.remove(0);
                        if (g.get(g.size() - 1).dir == 0) {
                            g.add(new Girder(0, g.get(g.size() - 1).rx - 60, g.get(g.size() - 1).ry - 90, g.get(g.size() - 1).ry - 70, 1));
                        } else {
                            g.add(new Girder(60, width, g.get(g.size() - 1).ly - 70, g.get(g.size() - 1).ly - 90, 0));
                        }
                        g.get(g.size()-1).next=g.get(g.size()-2);
                        Log.e("Or",""+g.get(g.size()-1).dir);
                    }
                    else{
                        g.add(new Girder(0,width,height,height-25,0));
                    }
                    Log.e("Size",""+g.size());
                }
                if(g.size()>2&&g.get(g.size()-2).b.size()==0){
                    Log.e("Barrel","start");
                    float x=r.nextInt(abs(g.get(g.size()-2).lx-g.get(g.size()-1).rx));
                    float y=(g.get(g.size()-2).ly-g.get(g.size()-2).ry)/(g.get(g.size()-2).lx-g.get(g.size()-2).rx)*x+g.get(g.size()-2).ly;
                    y-=10;
                    g.get(g.size()-2).b.add(new Barrel(g.get(g.size()-2),x,y,50));
                    Log.e("Barrel",""+g.get(g.size()-2).b.get(0).dx);
                    barrels++;
                    Log.e("tot",""+barrels);
                }

                if (!paused) {
                    update();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
            }
        }
        public void update() {
            Random r=new Random();
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
            for(int x=0;x<g.size();x++){
                g.get(x).update();
                if(x!=0){
                    if(g.get(x).d1==null){
                        //Log.e("Chaos","d1 "+x);
                        int lx;
                        while(true){
                            lx=r.nextInt(min(g.get(x).rx,g.get(x-1).rx)-max(g.get(x).lx,g.get(x-1).lx)-30)+max(g.get(x).lx,g.get(x-1).lx)+15;
                            if(g.get(x).d2!=null&&(g.get(x).d2.lx-50<lx&&g.get(x).d2.rx+50>lx))continue;
                            if(g.get(x).u1!=null&&(g.get(x).u1.lx-50<lx&&g.get(x).u1.rx+50>lx))continue;
                            if(g.get(x).u2!=null&&(g.get(x).u2.lx-50<lx&&g.get(x).u2.rx+50>lx))continue;
                            break;
                        }
                        g.get(x).d1=new Ladder(g.get(x-1),g.get(x),lx,lx+25);
                        if(g.get(x-1).u1==null)g.get(x-1).u1=g.get(x).d1;
                        else g.get(x-1).u2=g.get(x).d1;
                    }
                    //else Log.e("is a Ladder",g.get(x).d1.lx+" "+g.get(x).d1.rx+" d1 at g:"+x);
                    if(g.get(x).d2==null){
                        //Log.e("Chaos","d2 "+x);
                        int lx;
                        while(true){
                            lx=r.nextInt(min(g.get(x).rx,g.get(x-1).rx)-max(g.get(x).lx,g.get(x-1).lx)-30)+max(g.get(x).lx,g.get(x-1).lx)+15;
                            if(g.get(x).d1!=null&&(g.get(x).d1.lx-50<lx&&g.get(x).d1.rx+50>lx))continue;
                            if(g.get(x).u1!=null&&(g.get(x).u1.lx-50<lx&&g.get(x).u1.rx+50>lx))continue;
                            if(g.get(x).u2!=null&&(g.get(x).u2.lx-50<lx&&g.get(x).u2.rx+50>lx))continue;
                            break;
                        }
                        g.get(x).d2=new Ladder(g.get(x-1),g.get(x),lx,lx+25);
                        if(g.get(x-1).u1==null)g.get(x-1).u1=g.get(x).d2;
                        else g.get(x-1).u2=g.get(x).d2;
                    }
                    //else Log.e("is a Ladder",g.get(x).d2.lx+" "+g.get(x).d2.rx+" d2 at g:"+x);
                }
                if(x!=g.size()-1){
                    if(g.get(x).u1==null){
                        //Log.e("Chaos","u1 "+x);
                        int lx;
                        while(true){
                            lx=r.nextInt(min(g.get(x).rx,g.get(x+1).rx)-max(g.get(x).lx,g.get(x+1).lx)-30)+max(g.get(x).lx,g.get(x+1).lx)+15;
                            if(g.get(x).d1!=null&&(g.get(x).d1.lx-50<lx&&g.get(x).d1.rx+50>lx))continue;
                            if(g.get(x).d2!=null&&(g.get(x).d2.lx-50<lx&&g.get(x).d2.rx+50>lx))continue;
                            if(g.get(x).u2!=null&&(g.get(x).u2.lx-50<lx&&g.get(x).u2.rx+50>lx))continue;
                            break;
                        }
                        g.get(x).u1=new Ladder(g.get(x+1),g.get(x),lx,lx+25);
                        if(g.get(x+1).d1==null)g.get(x+1).d1=g.get(x).u1;
                        else g.get(x+1).d2=g.get(x).u1;
                    }
                    //else Log.e("is a Ladder",g.get(x).u1.lx+" "+g.get(x).u1.rx+" u1 at g:"+x);
                    if(g.get(x).u2==null){
                        //Log.e("Chaos","u2 "+x);
                        int lx;
                        while(true){
                            lx=r.nextInt(min(g.get(x).rx,g.get(x+1).rx)-max(g.get(x).lx,g.get(x+1).lx)-30)+max(g.get(x).lx,g.get(x+1).lx)+15;
                            if(g.get(x).d1!=null&&(g.get(x).d1.lx-50<lx&&g.get(x).d1.rx+50>lx))continue;
                            if(g.get(x).d2!=null&&(g.get(x).d2.lx-50<lx&&g.get(x).d2.rx+50>lx))continue;
                            if(g.get(x).u1!=null&&(g.get(x).u1.lx-50<lx&&g.get(x).u1.rx+50>lx))continue;
                            break;
                        }
                        g.get(x).u2=new Ladder(g.get(x+1),g.get(x),lx,lx+25);
                        if(g.get(x+1).d1==null)g.get(x+1).d1=g.get(x).u1;
                        else g.get(x+1).d2=g.get(x).u1;
                    }
                    //else Log.e("is a Ladder",g.get(x).u2.lx+" "+g.get(x).u2.rx+"  u2 at g:"+x);
                }
            }

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
                //canvas.drawLine(0, 0, 300, y, paint);

                paint.setStrokeWidth(10);

                for(int x=0;x<g.size();x++){
                    g.get(x).draw(canvas,paint);
                }

                m.draw(height,width);

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
