package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Strawberry extends Fruit{
    public Strawberry() {
        super();
        super.setImage(new Image("Strawberry.png", false));
        super.setSplashImage(new Image("RedSplash.jpg"));
        super.setClickOnFruitScore(20);
        super.setXspeed(20);
        super.setYspeed(15);
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(new ImagePattern(super.getImage()));
        circle.setCenterX(0);
        circle.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        super.setShape(circle);
    }
}
