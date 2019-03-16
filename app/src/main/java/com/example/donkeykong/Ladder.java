package com.example.donkeykong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Ladder {
    Girder t;
    Girder b;
    float lx;
    float rx;
    float y1;
    float y2;
    float y3;
    float y4;
    boolean broken=true;

    public Ladder(Girder t1, Girder b1, float lx1, float rx1,int brok){
        lx=lx1;
        rx=rx1;
        t=t1;
        b=b1;
        if(brok==0)broken=true;
        if(brok==1)broken=false;
        setY();
    }

    public void setY(){
        y1=(t.ly-t.ry)*(lx-t.lx)/(t.lx-t.rx)+t.ly;
        y2=(b.ly-b.ry)*(lx-b.lx)/(b.lx-b.rx)+b.ly;
        y3=(t.ly-t.ry)*(rx-t.lx)/(t.lx-t.rx)+t.ly;
        y4=(b.ly-b.ry)*(rx-b.lx)/(b.lx-b.rx)+b.ly;
        //Log.e("Chaos",(t.ly-t.ry)+" "+(t.lx-t.rx)+" "+(lx-t.ly)+" "+t.ly+" y1 "+y1);
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,26, 128, 182));
        canvas.drawLine(lx,y1,lx,y2,paint);
        canvas.drawLine(rx,y3,rx,y4,paint);
        float rungt=max(y1,y3);
        float rungb=min(y2,y4);
        float brea=0;
        if(broken)brea=(rungb-rungt)/3+rungt;
        paint.setStrokeWidth(3);
        //if(broken)Log.e("Chaos","start");
        /*if(broken){
            canvas.drawLine(lx,rungb-20,rx,rungb-20,paint);
            canvas.drawLine(lx,rungt+20,rx,rungt+20,paint);
        }*/
        if(!broken) {
            for (float i = rungt; i < rungb; i += 20) {
                canvas.drawLine(lx, i, rx, i, paint);
            }
        }
        //if(broken)Log.e("Chaos","end");
        paint.setStrokeWidth(10);
    }

}
