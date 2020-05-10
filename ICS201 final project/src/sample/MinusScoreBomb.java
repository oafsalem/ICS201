package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MinusScoreBomb extends Fruit {
    public MinusScoreBomb(){
        super();
        super.setClickOnFruitScore(-50);
        super.setImage((new Image("BlueBomb.png")));
        super.setSplashImage(new Image("BlueSplash.jpg"));
        super.setXspeed(25);
        super.setYspeed(10);
        Circle circle = new Circle();
        circle.setRadius(30);
        circle.setFill(new ImagePattern((super.getImage())));
        circle.setCenterY(Math.max(Main.windowHeight * Math.random(), Main.windowHeight/2.0));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.TRANSPARENT);
        circle.setStrokeWidth(60 - 30);
        super.setShape(circle);

    }

}
