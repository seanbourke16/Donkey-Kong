package com.example.donkeykong;

public class Barrel {

    Girder g;
    int dx;
    int dy;
    int y;
    int x;
    boolean fall=false;
    int s;

    public Barrel(Girder g1, int x1, int y1, int s1){
        g=g1;
        x=x1;
        y=y1;
        s=s1;
        dx=(g.sx-g.fx)/s;
    }

    public void update(){
        if(fall){
            if(y<=g.next.sy){
                g=g.next;
                dx=(g.sx-g.fx)/s;
                fall=false;
            }
        }
        x+=dx;
        y+=dy;
        if(g.dir==0&&x<g.fx){
            dx=0;
            fall=true;
        }
        if(g.dir==1&&x>g.fx){
            dx=0;
            fall=true;
        }
    }


}
