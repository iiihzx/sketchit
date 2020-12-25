package hellomvc2;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class shapedemo implements java.io.Serializable {
    public String name;
    public double c1;
    public double c2;
    public double c3;
    public double c4;
    public double c5;
    public double c6;
    public double c7;
    public double c8;
    public double    storke;//dashoff
    public double   width;
    public String storkefill;
    public String   fill;


    shapedemo(String n, double n1, double n2, double n3, double n4, double n5, double n6, double n7, double n8, double b1, double nn, String r1, String g1){
        name=n;
        c1=n1;
        c2=n2;
        c3=n3;
        c4=n4;
        c5=n5;
        c6=n6;
        c7=n7;
        c8=n8;
        storke=b1;
        width=nn;
        storkefill=r1;
        fill=g1;
    }

}
