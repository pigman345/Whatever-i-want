package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Platform {

        public int Plx;
        public int Ply;
        public int Plh;
        public int Plw;
        public int Plcol;
        public int Plrow;
        public Color Plcolor;

        public void draw(ShapeRenderer sr) {
            sr.rect(Plx,Ply, Plh, Plw, Plcolor, Plcolor, Plcolor, Plcolor);
        }

    public Platform(int plx, int ply, int plh, int plw, int plcol, int plrow, Color plcolor) {

        Plx = plx;
        Ply = ply;
        Plh = plh;
        Plw = plw;
        Plcol = plcol;
        Plrow = plrow;
        Plcolor = plcolor;
    }

    public int getPlx() {
        return Plx;
    }

    public void setPlx(int plx) {
        Plx = plx;
    }

    public int getPly() {
        return Ply;
    }

    public void setPly(int ply) {
        Ply = ply;
    }

    public int getPlh() {
        return Plh;
    }

    public void setPlh(int plh) {
        Plh = plh;
    }

    public int getPlw() {
        return Plw;
    }

    public void setPlw(int plw) {
        Plw = plw;
    }

    public int getPlcol() {
        return Plcol;
    }

    public void setPlcol(int plcol) {
        Plcol = plcol;
    }

    public int getPlrow() {
        return Plrow;
    }

    public void setPlrow(int plrow) {
        Plrow = plrow;
    }

    public Color getPlcolor() {
        return Plcolor;
    }

    public void setPlcolor(Color plcolor) {
        Plcolor = plcolor;
    }
}
