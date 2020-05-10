package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class BackButton implements EventHandler<MouseEvent> {

    public Circle getBackButton() {
        Circle circle = new Circle(30);
        circle.setFill(new ImagePattern(new Image("BackButton2.png")));
        return circle;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

    }
}
