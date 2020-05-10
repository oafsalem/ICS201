package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Apple extends Fruit {
    public Apple() {
        super();
        super.setImage(new Image("Apple.png", false));
        super.setSplashImage(new Image("YellowSplash.jpg"));
        super.setClickOnFruitScore(15);
        super.setXspeed(20);
        super.setYspeed(10);
        Circle circle = new Circle();
        circle.setRadius(25);
        circle.setFill(new ImagePattern(super.getImage()));
        circle.setCenterX(0);
        circle.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.TRANSPARENT);
        circle.setStrokeWidth(50 - 25);
        super.setShape(circle);
    }
}
