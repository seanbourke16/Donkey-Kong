package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Barrel {

    Girder g;
    float dx;
    float dy;
    float y;
    float x;
    boolean fall=false;
    float s;
    boolean done;

    public Barrel(Girder g1, float x1, float y1, float s1){
        g=g1;
        x=x1;
        y=y1;
        s=s1;
        setXY();
        done=false;
    }

    public void setXY(){
        if(g.dir==0){
            dy=(g.ly-10-y)/(g.lx-x)*-5;
            //dx=(g.lx-x)/s;
            dx=-5;
            Log.e("Barrel",g.lx+" x "+x+" "+dx+" left");
            Log.e("Barrel",g.ly+" y "+y+" "+dy+" left");
            Log.e("Barrel",g.ly+" y "+y+" g.ly-y "+(g.ly-y)+" g.lx-x "+(g.lx-x)+" tot "+(g.ly-y)/(g.lx-x)+" " +dy+" calc");
        }
        else {
            dy=(g.ry-10-y)/(g.rx-x)*5;
            //dx=(g.rx-x)/s;
            dx=5;
            Log.e("Barrel",g.rx+" x "+x+" "+dx+" right");
            Log.e("Barrel",g.ry+" y "+y+" "+dy+" right");
            Log.e("Barrel",g.ry+" y "+y+" g.ly-y "+(g.ry-y)+" g.lx-x "+(g.rx-x)+" tot "+(g.ry-y)/(g.rx-x)+" " +dy+" calc");
        }
    }

    public boolean ladder(){
        if(g.d1!=null) {
            if (x < ((g.d1.rx + g.d1.lx) / 2) + 2 && x > ((g.d1.rx + g.d1.lx) / 2) - 2) {
                return true;
            }
        }
        if(g.d2!=null) {
            if (x < ((g.d2.rx + g.d2.lx) / 2) + 2 && x > ((g.d2.rx + g.d2.lx) / 2) - 2) {
                return true;
            }
        }
        return false;
    }

    public void update(){
        Random r=new Random();
        if(done)return;
        if(fall){
            if(g.next==null){
                done=true;
                return;
            }
            if(g.next.dir==1&&y>=(g.next.ly-g.next.ry)*(x-g.next.lx)/(g.next.lx-g.next.rx)+g.next.ly-10){//(g.next.ly-10)){
                g.b.remove(this);
                g.next.b.add(this);
                g=g.next;
                setXY();
                fall=false;
            }
            else if(g.next.dir==0&&y>=(g.next.ly-g.next.ry)*(x-g.next.lx)/(g.next.lx-g.next.rx)+g.next.ly-10){//(g.next.ry-10)){
                g.b.remove(this);
                g.next.b.add(this);
                g=g.next;
                setXY();
                fall=false;
            }
        }
        Log.e("Fall","x: "+x+" dx: "+dx+" y: "+y+" dy: "+dy);
        x+=dx;
        y+=dy;
        if(g.dir==0&&x<g.lx){
            dx=0;
            dy=5;
            fall=true;
        }
        else if(g.dir==1&&x>g.rx){
            dx=0;
            dy=5;
            fall=true;
        }
        else if(ladder()&&r.nextInt(2)==0){
            dx=0;
            dy=5;
            fall=true;
        }

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(x,y,10,paint);
    }


}
