package com.example.donkeykong;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static java.lang.Math.max;

public class Mario {

    int height;
    int width;
    boolean start=false;
    float x;
    float y;
    int move;
    float dy;
    Girder g=null;
    int l;
    float j;
    float f;
    boolean jum=false;
    boolean dead=false;
    int face;


    public Mario(){
        move=-1;
        x=0;
        y=0;
        height=0;
        width=0;
        l=0;
        dy=0;
        f=0;
        j=0;

    }

    public int ladder(){
        if(g.d1!=null&&!g.d1.broken) {
            if (x<g.d1.rx&&x>g.d1.lx) {
                return -1;
            }
        }
        if(g.d2!=null&&!g.d2.broken) {
            if (x<g.d2.rx&&x>g.d2.lx) {
                return -2;
            }
        }
        if(g.u1!=null&&!g.u1.broken) {
            if (x<g.u1.rx&&x>g.u1.lx) {
                return 1;
            }
        }
        if(g.u2!=null&&!g.u2.broken) {
            if (x<g.u2.rx&&x>g.u2.lx) {
                return 2;
            }
        }
        return 0;
    }

    public float ground(){
        if(f==0)return 0;
        return (g.ly-g.ry)*(x-g.lx)/(g.lx-g.rx)+g.ly;
    }
    public float Yground(){
        return (g.ly-g.ry)*(x-g.lx)/(g.lx-g.rx)+g.ly;
    }
    public float ceiling(){
        if(j==0)return 0;
        float t=0;
        if(g.prev.dir==0){
            if(x>g.prev.lx){
                t=(g.prev.ly-g.prev.ry)*(x-g.prev.lx)/(g.prev.lx-g.prev.rx)+g.prev.ly;
            }
        }
        else{
            if(x<g.prev.rx){
                t=(g.prev.ly-g.prev.ry)*(x-g.prev.lx)/(g.prev.lx-g.prev.rx)+g.prev.ly;
            }
        }
        Log.e("jump","t "+t+" j "+j);
        return max(j,t+35);
    }

    public void checkG(){
        if(g.prev.dir==0){
          if(x>g.prev.lx&&y<g.prev.ly){
              g=g.prev;
              setXY();
          }
        }
        else{
            if(x<g.prev.rx&&y<g.prev.ry){
                g=g.prev;
                setXY();
            }
        }
        if(g.dir==0){
            if(x<g.lx&&y>g.ly){
                g=g.next;
                setXY();
            }
        }
        else{
            if(x>g.rx&&y>g.prev.ry){
                g=g.next;
                setXY();
            }
        }
    }

    public void setXY(){
        if(g.dir==1){
            dy=(g.ly-y)/(g.lx-x)*5;
            //dx=(g.lx-x)/s;
        }
        else {
            dy=(g.ry-y)/(g.rx-x)*5;
            //dx=(g.rx-x)/s;
        }
    }

    public void update() {
        /*if (g != null) {
            x = g.lx;
            y = g.ly;
        } else return;*/

        if(move==0)face=0;
        if(move==1)face=1;
        f = ground();
        j=ceiling();
        checkG();
        if (jum) {
            jum = false;
            jump();
            Log.e("jump","start");
        }
        if (j != 0 && j < y) {
            y -= 5;
            if(move==0)x+=5;
            if(move==1)x-=5;
            Log.e("jump","up f "+f+" j "+j+" y "+y);
        } else if (j != 0 && j >= y) {
            j = 0;
            Log.e("jump","top");
        } else if (f != 0 && f > y) {
            y += 5;
            if(move==0)x+=5;
            if(move==1)x-=5;
            //Log.e("jump","down f"+f+" j "+j+" y "+y);
        } else if (f != 0 && f <= y) {
            f = 0;
            //Log.e("jump","done");
        }
        if(l<0){
            if(move==2){
                y+=5;
            }
            if(move==3){
                y-=5;
            }
            if((y<(g.ly-g.ry)*(x-g.lx)/(g.lx-g.rx)+g.ly)){
               l=0;
            }
            if(y>(g.next.ly-g.next.ry)*(x-g.next.lx)/(g.next.lx-g.next.rx)+g.next.ly){
                l=0;
                g=g.next;
                setXY();
            }
        }
        if(l>0){
            Log.e("touch","going up "+l+" "+move);
            if(move==2){
                y+=5;
            }
            if(move==3){
                y-=5;
            }
            if((y>(g.ly-g.ry)*(x-g.lx)/(g.lx-g.rx)+g.ly)){
                l=0;
            }
            if(y<(g.prev.ly-g.prev.ry)*(x-g.prev.lx)/(g.prev.lx-g.prev.rx)+g.prev.ly){
                l=0;
                g=g.prev;
                setXY();
            }
        }
        if(f!=0||j!=0||l!=0)return;
        if(dy==0)setXY();
        //if(move!=-1) Log.e("move","move: "+move+" x: "+x+" y:"+y+" dy:"+dy);
        if (move == 0 && l==0) {
            x += 5;
            y = Yground();
        } else if (move == 1 && l==0) {
            x -= 5;
            y = Yground();
        } else if (move == 2 && (ladder() < 0 && l==0)) {
            y += 5;
            l = ladder();
            //Log.e("Chaos","down ladder "+ladder());
        } else if(move==3&&(ladder()>0&&l==0)){
            y-=5;
            l=ladder();
            //Log.e("Chaos","ladder "+l);
        } else if (move == 3 && (ladder() == 0 || l==0)) {
            jum=true;
            //Log.e("touch","jump "+l);
        }
        //if(move==3)Log.e("Chaos",ladder()+" "+l);
        if(x<0)x=0;
        if(x>width)x=width;
        if(y>height)y=height;
        if(y<0)y=0;
        collision();
    }

    public void collision(){
        if(g==null)return;
        if(g.prev!=null) {
            for (int z = 0; z < g.prev.b.size(); z++) {
                if (x >= g.prev.b.get(z).x - 10 && x <= g.prev.b.get(z).x + 10 && (y > g.prev.b.get(z).y && y - 35 < g.prev.b.get(z).y)) {
                    dead = true;
                }
            }
        }
        if(g.next!=null) {
            for (int z = 0; z < g.next.b.size(); z++) {
                if (x>=g.next.b.get(z).x-10&&x<=g.next.b.get(z).x+10&&(y>g.next.b.get(z).y&&y-35<g.next.b.get(z).y)) {
                    dead = true;
                }
            }
        }
        for(int z=0;z<g.b.size();z++){
            if(x>=g.b.get(z).x-10&&x<=g.b.get(z).x+10&&(y>g.b.get(z).y&&y-35<g.b.get(z).y)){
                dead=true;
            }
            //if(z==0)Log.e("dead",dead+" "+(x>=g.b.get(z).x-10)+" "+(x<=g.b.get(z).x+10)+" "+(y>g.b.get(z).y)+" "+(y-35<g.b.get(z).y));
        }
    }

    public void jump(){
        j=y-75;
        f=-1;
    }

    public void draw(int h, int w, Canvas canvas, Paint paint){
        if(!start){
            x=0;
            y=h;
            start=true;
        }
        height=h;
        width=w;
        //paint.setColor(Color.argb(255,0,255,0));
        //Rect r=new Rect((int)(x-10),(int)(y-35),(int)(x+10),(int)(y));
        //canvas.drawRect(r,paint);

    }

}
