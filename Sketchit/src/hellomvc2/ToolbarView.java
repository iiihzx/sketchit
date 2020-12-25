package hellomvc2;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.text.Element;
import java.awt.*;
import javafx.scene.control.ColorPicker;

class ToolbarView extends Pane implements IView {
    private Button button = new Button("?");
    private Model model; // reference to the model
    Image irectangle=new Image("src/hellomvc2/images/Rectangle.png",50,50,true,true);
    Image ieraser=new Image("src/hellomvc2/images/eraser.png",50,50,true,true);
    Image iselect=new Image("src/hellomvc2/images/Selection.png",50,50,true,true);
    Image iline=new Image("src/hellomvc2/images/Line.png",50,50,true,true);
    Image icircle=new Image("src/hellomvc2/images/circle.png",50,50,true,true);
    Image ifill=new Image("src/hellomvc2/images/Bucket.png",50,50,true,true);
    Image il1=new Image("src/hellomvc2/images/l1.png",80,10,false,true);
    Image il2=new Image("src/hellomvc2/images/l2.png",80,10,false,true);
    Image il3=new Image("src/hellomvc2/images/l3.png",80,10,false,true);
    ToolbarView(Model model) {
        // keep track of the model
        this.model = model;

        // setup the view (i.e. group+widget)
        this.setMinSize(SketchIt.WINDOW_WIDTH*2/9, SketchIt.WINDOW_HEIGHT/2);

        // add button widget to the pane
       // this.getChildren().add(button);
        ImageView vselect=new ImageView(iselect);
        ImageView veraser=new ImageView(ieraser);
        ImageView vline=new ImageView(iline);
        ImageView vcircle=new ImageView(icircle);
        ImageView vrectangle=new ImageView(irectangle);
        ImageView vfill=new ImageView(ifill);
        ImageView vl1=new ImageView(il1);
        ImageView vl2=new ImageView(il2);
        ImageView vl3=new ImageView(il3);
        this.getChildren().add(vselect);
        this.getChildren().add(veraser);
        this.getChildren().add(vline);
        this.getChildren().add(vcircle);
        this.getChildren().add(vrectangle);
        this.getChildren().add(vfill);
        this.getChildren().add(vl1);
        this.getChildren().add(vl2);
        this.getChildren().add(vl3);
        vselect.setPickOnBounds(true);
        this.setOnMouseClicked((MouseEvent e) ->{
            System.out.println("toolbarview clicked");
        });
        this.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: black;");
        vselect.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.sgroup.clear();
            SketchIt.tool=1;
            System.out.println("Clicked!");
        });
        vselect.setX(14);
        vselect.setY(10);
        //vselect.setFitWidth(20);
        veraser.setX(80);
        veraser.setY(10);
        veraser.setPickOnBounds(true);
        veraser.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.tool=2;
            SketchIt.sgroup.clear();
            System.out.println("Clicked2!");
        });
        // veraser.setFitWidth(20);
        vline.setX(14);
        vline.setY(80);
        vline.setPickOnBounds(true);
        vline.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.sgroup.clear();
            SketchIt.nline=0;
            SketchIt.tool=3;
            System.out.println("Clicked3!");
        });
        // vline.setFitWidth(20);
        vcircle.setX(80);
        vcircle.setY(80);
        vcircle.setPickOnBounds(true);
        vcircle.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.sgroup.clear();
            SketchIt.ncir=0;
            SketchIt.tool=4;
            System.out.println("Clicked4!");
        });
        //vcircle.setFitWidth(20);
        vrectangle.setY(160);
        vrectangle.setX(14);
        vrectangle.setPickOnBounds(true);
        vrectangle.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.sgroup.clear();
            SketchIt.nrec=0;
            SketchIt.tool=5;
            System.out.println("Clicked5!");
        });
        // vrectangle.setFitWidth(20);
        vfill.setX(80);
        vfill.setY(160);
        vfill.setPickOnBounds(true);
        vfill.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.sgroup.clear();
            SketchIt.tool=6;
            System.out.println("Clicked6!");
        });
        vl1.setX(30);
        vl1.setY(400);
        vl1.setPickOnBounds(true);
        vl1.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.line=1;
            System.out.println("Clicked7!");
            for(shape i:SketchIt.cs){
                for(Integer aa: SketchIt.sgroup) {
                    if (i.getIdentity() == aa) {
                        i.getMyshape().getStrokeDashArray().clear();
                        i.getMyshape().getStrokeDashArray().addAll(1d);
                    }
                }
            }
        });
        vl2.setX(30);
        vl2.setY(415);
        vl2.setPickOnBounds(true);
        vl2.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.line=2;
            for(shape i:SketchIt.cs) {
                for (Integer aa : SketchIt.sgroup) {
                    if (i.getIdentity() == aa) {
                        i.getMyshape().getStrokeDashArray().clear();
                        i.getMyshape().getStrokeDashArray().addAll(4d);
                    }
                }
            }
        });
        vl3.setX(30);
        vl3.setY(430);
        vl3.setPickOnBounds(true);
        vl3.setOnMouseClicked((MouseEvent e) -> {
            // change functionality
            SketchIt.line=3;
            System.out.println("Clicked9!");
            for(shape i:SketchIt.cs){
                for(Integer aa: SketchIt.sgroup) {
                    if (i.getIdentity() == aa) {
                        i.getMyshape().getStrokeDashArray().clear();
                        i.getMyshape().getStrokeDashArray().addAll(2d);
                    }
                }
            }
        });
        // register with the model when we're ready to start receiving data
        model.addView(this);
    }

    // When notified by the model that things have changed,
    // update to display the new value
    public void updateView(int change) {
        System.out.println("View: updateView");
    }
}