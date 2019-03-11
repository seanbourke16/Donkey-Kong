package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Girder {
    int sx;
    int fx;
    int sy;
    int fy;
    int dir;
    Girder next;

    public Girder(int x1,int x2, int x3, int x4, int dir1){
        sx=x1;
        fx=x2;
        sy=x3;
        fy=x4;
        dir=dir1;
    }

    public void update(){

    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawLine(sx,sy,fx,fy,paint);
        //canvas.drawLine(sx,sy+10,fx,fy+10,paint);
    }
}
