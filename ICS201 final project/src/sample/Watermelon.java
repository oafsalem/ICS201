package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Watermelon extends Fruit{

    public Watermelon() {
        super();
        super.setImage(new Image("Watermelon.png", false));
        super.setSplashImage(new Image("RedSplash.jpg"));
        super.setClickOnFruitScore(10);
        super.setXspeed(15);
        super.setYspeed(10);
        Circle circle = new Circle();
        circle.setRadius(30);
        circle.setFill(new ImagePattern(super.getImage()));
        circle.setCenterX(0);
        circle.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.TRANSPARENT);
        circle.setStrokeWidth(60 - 30);
        super.setShape(circle);
    }

}
