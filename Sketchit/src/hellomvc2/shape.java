package hellomvc2;


import javafx.scene.shape.Shape;

public class shape {
    int identity;
    Shape myshape;
    double deltax;
    double deltay;
    String name;

int getIdentity(){
    return identity;
}

void setIdentity(int id){
    identity=id;
}

void setMyshape(Shape m){
    myshape=m;
}
Shape getMyshape(){
    return myshape;
}
double getDeltax(){
    return deltax;
}
double getDeltay(){
    return deltay;
}
void setDeltax(double m){
    deltax=m;
}
void  setDeltay(double m){
    deltay=m;
}
void setName(String n){
    name=n;
}
String getName(){
    return name;

}}