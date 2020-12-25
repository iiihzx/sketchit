package hellomvc2;
import java.lang.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

// HelloMVC
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version uses MVC: two views coordinated with the observer pattern, but no separate controller.

public class SketchIt extends Application {
    static int filenamecounter;
    Scene scene;
    MenuButton mb;
    static ToolbarView view1;
    static CanvasView view2;
    static double WINDOW_WIDTH = 640;
    static double WINDOW_HEIGHT = 480;
    static int sliderchange=0;
    static Color c;
    static Color c2;
    static double width;
    Boolean select;
    static ArrayList<shape> cs=new ArrayList<shape>();
    static ArrayList<shapedemo> shapes=new ArrayList<shapedemo>();
    static ArrayList<Integer> sgroup=new ArrayList<Integer>();
    static int nline;
    static int nrec;
    static int ncir;
    static double[] linec=new double[4];
    static double[] rectc=new double[8];
    static double[] circ=new double[4];
    static int tool;
    static int line;
    static volatile int draw;
    static double vx;
    static double vy;
    static double strokew;
    boolean save;
    String choose;
    @Override
    public void start(Stage stage) throws Exception {
        filenamecounter=0;
        ArrayList<String> filenames=new ArrayList<String>();
        width=10;
        tool=0;
        line=1;
        draw=0;
        save=true;
        // create and initialize the hellomvc2.Controller.Model to hold our counter
        Model model = new Model();
        Group group=new Group();
        // the views will register themselves with the model
        view1 = new ToolbarView(model);
        view2 = new CanvasView(model);
        GridPane grid = new GridPane();
        MenuButton mb = new MenuButton("File  Menu");
        mb.setTooltip(new Tooltip("Tooltip for MenuButton"));
        final javafx.scene.control.MenuItem mnew = new javafx.scene.control.MenuItem("New");
        mnew.setOnAction(e -> {
            if(save==false) {
                Popup pop = new Popup();
                pop.setX(200);
                pop.setY(200);
                pop.setHeight(30);
                pop.setWidth(60);
                Pane t1=new Pane(new Label("You haven't save the file, do you want to save it? Press save to save as file"+filenamecounter));
                t1.setStyle("-fx-font-size: 12pt; -fx-text-fill:black;-fx-background-color:white;-fx-border-color:black;");
                t1.setMinSize(60,30);
                pop.getContent().add(t1);
                t1.setLayoutX(200);
                t1.setLayoutY(200);
                Button n1=new Button("Save");
                Button n2=new Button("Ignore");
                t1.getChildren().add(n1);
                t1.getChildren().add(n2);
                n1.setLayoutX(140);
                n1.setLayoutY(230);
                n2.setLayoutX(230);
                n2.setLayoutY(230);
                pop.show(stage);
                n1.setOnAction(m -> {
                    pop.hide();
                    transform();
                    cs.clear();
                    CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                    String filename="file"+filenamecounter;
                    filenames.add(filename);
                    serialization.ser(filename, shapes);
                    filenamecounter++;
                });
                n2.setOnAction(m ->{
                 pop.hide();
                 cs.clear();
                 CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                });
            } else{
                draw=0;
                CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                cs.clear();
            }
            save=false;
        });
        mb.getItems().add(mnew);

        final javafx.scene.control.MenuItem mload = new javafx.scene.control.MenuItem("Load");
        mload.setOnAction(e -> {
            save=true;
            shapes=new ArrayList<shapedemo>();
            TextField fn = new TextField();
            Popup pop = new Popup();
            pop.setX(200);
            pop.setY(200);
            pop.setHeight(30);
            pop.setWidth(60);
            Pane t1=new Pane(new Label("Input the filename you want to load:"));
            t1.setStyle("-fx-font-size: 12pt; -fx-text-fill:black;-fx-background-color:white;-fx-border-color:black;");
            t1.setMinSize(60,30);
            pop.getContent().add(t1);
            fn.setLayoutX(200);
            fn.setLayoutY(170);
            Button n1=new Button("Confirm");
            Button n2=new Button("Clear");
            t1.getChildren().add(n1);
            t1.getChildren().add(n2);
            t1.getChildren().add(fn);
            t1.setLayoutX(200);
            t1.setLayoutY(200);
            n1.setLayoutX(140);
            n1.setLayoutY(230);
            n2.setLayoutX(230);
            n2.setLayoutY(230);
            pop.show(stage);
            n1.setOnAction(m -> {
                CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                cs.clear();
                System.out.println("mamamam"+fn.getText());
                choose=fn.getText();
                pop.hide();
                System.out.println(choose+"wtf");
                deserializeDemo.des(choose,shapes);
                draw=0;
                System.out.println("shapes!!!"+shapes.size());
                for (shapedemo i:shapes){
                    System.out.println("here");
                    shape newd2=new shape();
                    newd2.setIdentity(SketchIt.draw);
                    draw++;
                    if(i.name.equals("Circle")){
                        Circle cc=new Circle(i.c1,i.c2,i.c3);
                        if(i.storke!=0.0){
                            cc.getStrokeDashArray().addAll(i.storke);
                        }
                        cc.setStrokeWidth(i.width);
                        cc.setStroke(Paint.valueOf(i.storkefill));
                        cc.setFill(Paint.valueOf(i.fill));
                        CanvasView.text.getChildren().add(cc);
                        newd2.setMyshape(cc);
                    }  else if(i.name.equals("Line")) {
                        Polygon pp=new Polygon(i.c1,i.c2,i.c3,i.c4);
                        if(i.storke!=0.0){
                            pp.getStrokeDashArray().addAll(i.storke);
                        }
                        pp.setStrokeWidth(i.width);
                        pp.setStroke(Paint.valueOf(i.storkefill));
                        newd2.setMyshape(pp);
                        CanvasView.text.getChildren().add(pp);
                    }  else if (i.name.equals("Rectangle")){
                        System.out.println("here?????");
                        Rectangle pp=new Rectangle(i.c1,i.c2,i.c3,i.c4);
                        if(i.storke!=0.0){
                            pp.getStrokeDashArray().addAll(i.storke);
                        }
                        pp.setStrokeWidth(i.width);
                        pp.setStroke(Paint.valueOf(i.storkefill));
                        pp.setFill(Paint.valueOf(i.fill));
                        newd2.setMyshape(pp);
                        newd2.setName("Rectangle");
                        SketchIt.cs.add(newd2);
                        CanvasView.text.getChildren().add(pp);
                        SketchIt.nrec = 0;
                    }
                }
            });
            n2.setOnAction(m ->{
                fn.clear();
            });

              });//pop up a list of file saved
        mb.getItems().add(mload);

        final javafx.scene.control.MenuItem msave = new javafx.scene.control.MenuItem("Save");
        msave.setOnAction(e -> {
            shapes=new ArrayList<shapedemo>();
            System.out.println("hhhhhhh");
                Popup pop = new Popup();
                pop.setX(200);
                pop.setY(100);
                pop.setHeight(30);
                pop.setWidth(60);
                Pane t1=new Pane(new Label("Press save to save as file"+filenamecounter));
                t1.setStyle("-fx-font-size: 12pt; -fx-text-fill:black;-fx-background-color:white;-fx-border-color:black;");
                t1.setMinSize(60,30);
                pop.getContent().add(t1);
                t1.setLayoutX(200);
                t1.setLayoutY(200);
                Button n1=new Button("Save");
                Button n2=new Button("Ignore");
                t1.getChildren().add(n1);
                t1.getChildren().add(n2);
                n1.setLayoutX(140);
                n1.setLayoutY(230);
                n2.setLayoutX(230);
                n2.setLayoutY(230);
                pop.show(stage);
                n1.setOnAction(m -> {
                    pop.hide();
                    transform();
                    cs.clear();
                    CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                    String filename="file"+filenamecounter;
                    System.out.println(filename+"ppppp");
                    filenames.add(filename);
                    serialization.ser(filename, shapes);
                    filenamecounter++;
                    shapes.clear();
                });
                n2.setOnAction(m ->{
                    pop.hide();
                    cs.clear();
                    CanvasView.text.getChildren().removeAll(CanvasView.text.getChildren());
                }); });//save current file as given a name a pop up window
        mb.getItems().add(msave);

        final javafx.scene.control.MenuItem mquit = new javafx.scene.control.MenuItem("Quit");//ok
        mquit.setOnAction(e -> System.exit(0));
        mb.getItems().add(mquit);
        mb.setPopupSide(Side.RIGHT);

        grid.add(mb,0,0);
        grid.add(view1,0,1);// left
        grid.add(view2, 1, 1);      // right
        EventHandler<ActionEvent> actionEventHandler1 = t -> {
            ColorPicker cp = (ColorPicker) t.getTarget();
            c = cp.getValue();
            System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
        };
        EventHandler<ActionEvent> actionEventHandler2 = t -> {
            ColorPicker cp = (ColorPicker) t.getTarget();
            c2 = cp.getValue();
            System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
        };

// default mode combobox
        final ColorPicker normalColorPicker = new ColorPicker();
        normalColorPicker.setOnAction(actionEventHandler1);
        final ColorPicker normalColorPicker2 = new ColorPicker();
        normalColorPicker2.setOnAction(actionEventHandler2);
        Label n1=new Label(" Line-ColorPicker ");
        view1.getChildren().add(n1);
        n1.setLayoutX(10);
        n1.setLayoutY(225);
        view1.getChildren().add(normalColorPicker);
        normalColorPicker.setLayoutY(250);
        normalColorPicker.setLayoutX(0);
        normalColorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                c=normalColorPicker.getValue();
                if(tool==1&&sgroup.size()>0) {
                    for(shape i:cs){
                        for(Integer aa:sgroup) {
                            if (i.getIdentity() == aa) {
                                i.getMyshape().setStroke(c);
                            }
                        }
                    }
                }
            }
        });
        Label n2=new Label(" Fill-ColorPicker ");
        view1.getChildren().add(n2);
        n2.setLayoutX(10);
        n2.setLayoutY(280);
        view1.getChildren().add(normalColorPicker2);
        normalColorPicker2.setOnAction(new EventHandler() {
            public void handle(Event t) {
                c2=normalColorPicker2.getValue();
                if(tool==1&&sgroup.size()>0) {
                    for(shape i:cs){
                        for(Integer aa:sgroup) {
                            if (i.getIdentity() == aa) {
                                i.getMyshape().setFill(c2);
                            }
                        }
                    }
                }
            }
        });
        Label n3=new Label(" Line-WidthSlider ");
        n3.setStyle("-fx-border-color: grey;");
        n2.setStyle("-fx-border-color: grey;");
        n1.setStyle("-fx-border-color: grey;");
        view1.getChildren().add(n3);
        normalColorPicker2.setLayoutY(305);
        normalColorPicker2.setLayoutX(0);
        n3.setLayoutX(10);
        n3.setLayoutY(340);
        Slider slider = new Slider();
        view1.getChildren().add(slider);
        slider.setLayoutX(0);
        slider.setLayoutY(360);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                width=new_val.doubleValue()/10;
                System.out.println(width);
                view2.updateView(sliderchange);
            }
        });

        scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
                double sizee=(double)newSceneWidth/ (double)oldSceneWidth;
                CanvasView.text.setMinWidth(CanvasView.text.getWidth()*sizee);
                for(Node i: CanvasView.text.getChildren()){
                    Scale scale=new Scale(sizee,1);
                    i.getTransforms().add(scale);
                }
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                double sizee=(double)newSceneHeight/ (double)oldSceneHeight;
                System.out.println("Height: " + sizee);
                CanvasView.text.setMinHeight(CanvasView.text.getHeight()*sizee);
                for(Node i: CanvasView.text.getChildren()){
                    Scale scale=new Scale(1,sizee);
                    i.getTransforms().add(scale);
                }
            }
        });
        scene.setOnKeyPressed(e -> keylistener(e));
        stage.setScene(scene);
        stage.setTitle("SketchIt");
        stage.show();
    }

    private void keylistener (KeyEvent e){
        if(e.getCode() == KeyCode.ESCAPE){
            System.out.println("hereeeee?");
            sgroup.clear();
        }
    }


    void transform(){
     for( Node i: CanvasView.text.getChildren()){
         System.out.println(i);
         String s=i.toString();
         System.out.println(s);

        if(s.substring(0,6).equals("Circle")){
            Circle nnn=(Circle)i;
            double i1=nnn.getCenterX();
            double i2=nnn.getCenterY();
            double i3=nnn.getRadius();
            double i4=0.0;
            if(nnn.getStrokeDashArray().size()>0) {
                i4=nnn.getStrokeDashArray().get(0);
            }
            double i5=nnn.getStrokeWidth();
            Color i6= (Color) nnn.getStroke();
            Color i7= (Color) nnn.getFill();
            System.out.println(i4);
           shapedemo s1=new shapedemo("Circle",i1,i2,i3,0,0,0,0,0,i4,i5,i6.toString(),i7.toString());
           shapes.add(s1);
         }else if(s.substring(0,7).equals("Polygon")){
            Polygon nnn=(Polygon)i;
            double i4=0.0;
            if(nnn.getStrokeDashArray().size()>0) {
                i4=nnn.getStrokeDashArray().get(0);
            }
            double i5=nnn.getStrokeWidth();
            Color i6= (Color) nnn.getStroke();
            Color i7= (Color) nnn.getFill();

               ObservableList<Double> aa=nnn.getPoints();
                shapedemo s1=new shapedemo("Line",aa.get(0),aa.get(1),aa.get(2),aa.get(3),0,0,0,0,i4,i5,i6.toString(),i7.toString());
                shapes.add(s1);

        }else if (s.substring(0,9).equals("Rectangle")){
            Rectangle nnn=(Rectangle) i;
            double i4=0.0;
            if(nnn.getStrokeDashArray().size()>0) {
                i4=nnn.getStrokeDashArray().get(0);
            }
            double i5=nnn.getStrokeWidth();
            Color i6= (Color) nnn.getStroke();
            Color i7= (Color) nnn.getFill();
            shapedemo s1=new shapedemo("Rectangle",nnn.getX(),nnn.getY(),nnn.getWidth(),nnn.getHeight(),0,0,0,0,i4,i5,i6.toString(),i7.toString());
            shapes.add(s1);
        }
     }
    }
}