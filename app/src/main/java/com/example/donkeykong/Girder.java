package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Girder {
    int sx;
    int fx;
    int sy;
    int fy;
    public Girder(int x1,int x2, int x3, int x4){
        sx=x1;
        fx=x2;
        sy=x3;
        fy=x4;
    }
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,150,0,0));
        canvas.drawLine(sx,sy,fx,fy,paint);
        canvas.drawLine(sx,sy+10,fx,fy+10,paint);
    }
}
