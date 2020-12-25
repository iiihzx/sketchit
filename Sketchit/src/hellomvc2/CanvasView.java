package hellomvc2;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;

import java.util.ArrayList;

class CanvasView extends Pane implements IView {

    protected static Pane text = new Pane();
    private Model model; // reference to the model

    CanvasView(Model model) {
        text.setLayoutX(60);
        text.setLayoutY(10);
        this.model = model;
        text.setMinHeight(SketchIt.WINDOW_HEIGHT*6/7);
        text.setMinWidth(SketchIt.WINDOW_WIDTH * 5 / 7);
        text.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: black; ");
        this.getChildren().add(text);
        // rectangle.setOnMouseClicked(mouseEvent -> System.out.println("Rectangle clicked"));
        // the previous controller code will just be handled here
        // we don't need always need a separate controller class!
        text.setOnMousePressed((MouseEvent e) -> {
           // SketchIt.view1
            System.out.println("Controller: changing hellomvc2.Controller.Model (actionPerformed)");
            System.out.println("tool"+SketchIt.tool);
            if (SketchIt.tool == 3) {
                if (SketchIt.nline < 2) {
                    System.out.println(e.getSceneX() + "hhhhh" + e.getSceneY());
                    SketchIt.linec[SketchIt.nline * 2] = e.getX();
                    SketchIt.linec[SketchIt.nline * 2 + 1] = e.getY();
                    SketchIt.nline++;
                    System.out.println(SketchIt.nline);
                    if (SketchIt.nline == 2) {
                        Polygon polygon = new Polygon(SketchIt.linec);
                        polygon.setStroke(Color.BLACK);
                        polygon.setStrokeWidth(10.0);
                        SketchIt.draw++;
                        hellomvc2.shape newd = new shape();
                        newd.setIdentity(SketchIt.draw);
                        System.out.println("create line" + SketchIt.draw);
                        newd.setMyshape(polygon);
                        newd.setName("Line");
                        text.getChildren().add(polygon);
                        SketchIt.nline = 0;
                        SketchIt.cs.add(newd);
                    }
                }
            } else if (SketchIt.tool == 4) {
                if (SketchIt.ncir < 2) {
                    System.out.println(e.getSceneX() + "hhhhh" + e.getSceneY());
                    SketchIt.circ[SketchIt.nline * 2] = e.getX();
                    SketchIt.circ[SketchIt.nline * 2 + 1] = e.getY();
                    SketchIt.nline++;
                    System.out.println(SketchIt.nline);
                    if (SketchIt.nline == 2) {
                        System.out.println("a circle is created");
                        double rad = Math.sqrt(Math.pow(SketchIt.circ[3] - SketchIt.circ[1], 2) + Math.pow(SketchIt.circ[2] - SketchIt.circ[0], 2));
                        Circle cc = new Circle((SketchIt.circ[0]+SketchIt.circ[2])/2, (SketchIt.circ[1]+SketchIt.circ[3])/2, rad/2);
                        cc.setStroke(Color.BLACK);
                        cc.setFill(Color.TRANSPARENT);
                        SketchIt.draw++;
                        hellomvc2.shape newd1 = new shape();
                        newd1.setIdentity(SketchIt.draw);
                        newd1.setName("Circle");
                        System.out.println("create circle" + newd1.getIdentity());
                        newd1.setMyshape(cc);
                        text.getChildren().add(cc);
                        SketchIt.cs.add(newd1);
                        SketchIt.nline = 0;
                    }
                }

            } else if (SketchIt.tool == 5) {
                if (SketchIt.nrec < 4) {
                    System.out.println(e.getSceneX() + "hhhhh" + e.getSceneY());
                    SketchIt.rectc[SketchIt.nrec * 2] = e.getX();
                    SketchIt.rectc[SketchIt.nrec * 2 + 1] = e.getY();
                    SketchIt.nrec++;
                    if (SketchIt.nrec == 2) {
                        Polygon polygon = new Polygon(SketchIt.rectc[0], SketchIt.rectc[1], SketchIt.rectc[2], SketchIt.rectc[3]);
                        polygon.setStroke(Color.BLACK);
                        Rectangle rr=new Rectangle(SketchIt.rectc[0],SketchIt.rectc[1], Math.abs(SketchIt.rectc[0]-SketchIt.rectc[2]),Math.abs(SketchIt.rectc[1]-SketchIt.rectc[3]));
                        text.getChildren().add(rr);
                        rr.setStroke(Color.BLACK);
                        rr.setFill(Color.TRANSPARENT);
                        SketchIt.draw+=1;
                        hellomvc2.shape newd2 = new shape();
                        newd2.setIdentity(SketchIt.draw);
                        newd2.setName("rectangle");
                        System.out.println("create rec" + newd2.getIdentity());
                        newd2.setMyshape(rr);
                        newd2.setName("Rectangle");
                        SketchIt.cs.add(newd2);
                        SketchIt.nrec = 0;
                    }
                }

            }
            for (shape i : SketchIt.cs) {
                (i.getMyshape()).setOnMousePressed((MouseEvent m) -> {
                    i.setDeltax(m.getSceneX());
                    i.setDeltay(m.getSceneY());
                    if (SketchIt.tool == 1) {
                        //System.out.println("a rec is clicked");
                        for(shape j:SketchIt.cs) {
                            for (Integer aa : SketchIt.sgroup) {
                                if (j.getIdentity() == aa) {
                                    j.getMyshape().setStrokeWidth(SketchIt.strokew);
                                }
                            }
                        }
                        SketchIt.sgroup.add(i.getIdentity());
                        System.out.println("id:" + i.getIdentity());
                        System.out.println("shape"+i.getMyshape());
                        i.getMyshape().setStrokeWidth(20.0);
                        SketchIt.strokew=SketchIt.width;
                        i.getMyshape().setStroke(SketchIt.c);
                        i.getMyshape().setFill(SketchIt.c2);
                        if(SketchIt.line==1){
                            i.getMyshape().getStrokeDashArray().clear();
                            i.getMyshape().getStrokeDashArray().addAll(1d);
                        } else if (SketchIt.line==2){
                            i.getMyshape().getStrokeDashArray().clear();
                            i.getMyshape().getStrokeDashArray().setAll(4d);
                        } else if (SketchIt.line==3){
                            i.getMyshape().getStrokeDashArray().clear();
                            i.getMyshape().getStrokeDashArray().setAll(2d);
                        }
                        // newd.getMyshape().setStrokeDashOffset(?);
                    } else if (SketchIt.tool == 2) {
                        text.getChildren().remove(i.getMyshape());
                        SketchIt.cs.remove(i);
                    } else if (SketchIt.tool == 6) {
                        i.getMyshape().setFill(SketchIt.c2);

                    }
                });
                (i.getMyshape()).setOnMouseDragged((MouseEvent m)-> {
                    System.out.println(i.getMyshape().getLayoutX()+m.getSceneX()-i.getDeltax());
                    System.out.println(i.getMyshape().getLayoutY()+m.getSceneY()-i.getDeltay());
                    i.getMyshape().setLayoutX(i.getMyshape().getLayoutX()+m.getSceneX()-i.getDeltax());
                    i.getMyshape().setLayoutY(i.getMyshape().getLayoutY()+m.getSceneY()-i.getDeltay());
                    i.setDeltax(m.getSceneX());
                    i.setDeltay(m.getSceneY());
                    SketchIt.nrec=0;
                    SketchIt.nline=0;
                    SketchIt.ncir=0;
                });
         /*       if(i.getName().equals("Line")|| (i.getName().equals("Rectangle"))){
                (((Polygon)(i.getMyshape())).getPoints()).setOnMouseDragged((MouseEvent m)-> {
                    System.out.println(i.getMyshape().getLayoutX() + m.getSceneX() - i.getDeltax());
                    System.out.println(i.getMyshape().getLayoutY() + m.getSceneY() - i.getDeltay());
                    i.getMyshape().setLayoutX(i.getMyshape().getLayoutX() + m.getSceneX() - i.getDeltax());
                    i.getMyshape().setLayoutY(i.getMyshape().getLayoutY() + m.getSceneY() - i.getDeltay());
                    i.setDeltax(m.getSceneX());
                    i.setDeltay(m.getSceneY());
                    if (SketchIt.tool == 1) {

                    }
                }
                });*/
            }
        });
        model.addView(this);

    }

    // When notified by the model that things have changed,
    // update to display the new value
    public void updateView(int change) {
        System.out.println("View2: updateView");
        for(shape i : SketchIt.cs ){
            for(Integer aa: SketchIt.sgroup) {
           if(i.getIdentity()==aa) {
               Shape hh = i.getMyshape();
               if (change == SketchIt.sliderchange) {
                   hh.setStrokeWidth(SketchIt.width);
               }
           }

           }        }
    }
}