package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Kiwi extends Fruit {
    public Kiwi() {
        super();
        super.setImage(new Image("Kiwi.png", false));
        super.setSplashImage(new Image("GreenSplash.jpg"));
        super.setClickOnFruitScore(15);
        super.setXspeed(20);
        super.setYspeed(10);
        Circle circle = new Circle();
        circle.setRadius(25);
        circle.setFill(new ImagePattern(super.getImage()));
        circle.setCenterX(0);
        circle.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        super.setShape(circle);
    }
}