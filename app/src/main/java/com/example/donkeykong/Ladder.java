package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ladder {
    Girder t;
    Girder b;
    float lx;
    float rx;
    float y1;
    float y2;
    float y3;
    float y4;

    public Ladder(Girder t1, Girder b1, float lx1, float rx1){
        lx=lx1;
        rx=rx1;
        t=t1;
        b=b1;
        setY();
    }

    public void setY(){
        y1=(t.ly-t.ry)*(lx-t.lx)/(t.lx-t.rx)+t.ly;
        y2=(b.ly-b.ry)*(lx-b.lx)/(b.lx-b.rx)+b.ly;
        y3=(t.ly-t.ry)*(rx-t.lx)/(t.lx-t.rx)+t.ly;
        y4=(b.ly-b.ry)*(rx-b.lx)/(b.lx-b.rx)+b.ly;
        Log.e("Chaos",(t.ly-t.ry)+" "+(t.lx-t.rx)+" "+(lx-t.ly)+" "+t.ly+" y1 "+y1);
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,0,0));
        canvas.drawLine(lx,y1,lx,y2,paint);
        canvas.drawLine(rx,y3,rx,y4,paint);
    }

}
