package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Mario {

    int height;
    int width;
    int x;
    int y;
    int move=-1;
    int dy;
    Girder g;
    boolean l=false;
    int j;
    int f;
    boolean jum=false;

    public Mario(Girder g1){
        g=g1;
        x=g.lx;
        y=g.ly;
    }

    public int ladder(){
        if(g.d1!=null) {
            if (x < ((g.d1.rx + g.d1.lx) / 2) + 2 && x > ((g.d1.rx + g.d1.lx) / 2) - 2) {
                return 0;
            }
        }
        if(g.d2!=null) {
            if (x < ((g.d2.rx + g.d2.lx) / 2) + 2 && x > ((g.d2.rx + g.d2.lx) / 2) - 2) {
                return 0;
            }
        }
        if(g.u1!=null) {
            if (x < ((g.u1.rx + g.u1.lx) / 2) + 2 && x > ((g.u1.rx + g.u1.lx) / 2) - 2) {
                return 1;
            }
        }
        if(g.u2!=null) {
            if (x < ((g.u2.rx + g.u2.lx) / 2) + 2 && x > ((g.u2.rx + g.u2.lx) / 2) - 2) {
                return 1;
            }
        }
        return -1;
    }

    public int ground(){
        if(f==0)return 0;
        return -1;
    }

    public void update(){
        if(g.dir==0) {
            f=ground();
            if(jum){
                jum=false;
                jump();
            }
            if(j!=0&&j<y){
                y-=5;
            }
            else if(j!=0&&j>y){
                j=0;
            }
            else if(f!=0&&f>y){
                y+=5;
            }
            else if(f!=0&&f<y){
                f=0;
            }
            if (move == 0&&!l) {
                x += 5;
                y -= dy;
            }
            else if (move == 1&&!l) {
                x-=5;
                y+=dy;
            }
            else if(move==2&&(ladder()==0||l)){
                y-=5;
                l=true;
            }
            else if(move==3&&(ladder()==1||l)){
                y+=5;
                l=true;
            }
        }
    }

    public void jump(){
        j=y-50;
    }

    public void draw(int h, int w, Canvas canvas, Paint paint){
        height=h;
        width=w;
        paint.setColor(Color.argb(255,0,255,0));
        Rect r=new Rect(x-10,y-50,x+10,y);
        canvas.drawRect(r,paint);
    }

}
