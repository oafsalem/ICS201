package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import javafx.scene.shape.Ellipse;

public class Banana extends Fruit {

    public Banana() {
        super();
        super.setImage(new Image("Banana.png", false));
        super.setSplashImage(new Image("YellowSplash.jpg"));
        super.setClickOnFruitScore(20);
        super.setXspeed(22);
        super.setYspeed(15);
        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(0);
        ellipse.setRadiusX(25);
        ellipse.setRadiusY(15);
        ellipse.setFill(new ImagePattern(super.getImage()));
        ellipse.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        ellipse.setStrokeType(StrokeType.OUTSIDE);
        ellipse.setStroke(Color.TRANSPARENT);
        ellipse.setStrokeWidth(60 - 30);
        super.setShape(ellipse);
    }

}
