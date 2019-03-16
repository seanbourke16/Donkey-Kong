package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.Log;

import java.util.ArrayList;

public class Girder {
    int lx;
    int rx;
    int ly;
    int ry;
    int dir;
    Ladder u1;
    Ladder u2;
    Ladder d1;
    Ladder d2;
    Girder next;
    Girder prev;
    ArrayList<Barrel> b;
    Mario m;

    public Girder(int x1,int x2, int x3, int x4, int dir1){
        lx=x1;
        rx=x2;
        ly=x3;
        ry=x4;
        dir=dir1;
        b=new ArrayList<>();
        next=null;
        prev=null;
        m=null;
    }

    public void update(){
        for(int x=0;x<b.size();x++){
            if(b.get(x).done){
                b.remove(x);
                x--;
            }
            else{
                b.get(x).update();
            }
            //m.update();
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        /*canvas.save();
        Rect r = new Rect(lx,ty,rx,by);
        if(dir==0)canvas.rotate(15);
        else canvas.rotate(-15);
        canvas.drawRect(r,paint);
        canvas.restore();*/
        /*if(ly<=0||ry<=0){
            if(next!=null&&b!=null){

            }
        }
        else {*/
        paint.setColor(Color.argb(255,150,0,0));
            canvas.drawLine(lx, ly, rx, ry, paint);
            paint.setColor(Color.argb(255,0,0,200));
        for(int x=0;x<b.size();x++){
            b.get(x).draw(canvas,paint);
        }
        if(u1!=null)u1.draw(canvas,paint);
        if(u2!=null)u2.draw(canvas,paint);
        //}
    }
}