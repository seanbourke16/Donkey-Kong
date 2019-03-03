package com.example.donkeykong;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DonkeyView donkeyView=new DonkeyView(this);
        setContentView(donkeyView);
    }

    class DonkeyView extends SurfaceView implements Runnable{
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
    }
}
