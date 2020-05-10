package sample;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public abstract class Fruit {
    private double Xspeed;
    private double Yspeed;
    private Image image;
    private Image splashImage;
    private int ClickOnFruitScore;
    private boolean isSmashed;
    private Shape shape;
    private double xPosition;
    private double yPosition;

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Image getImage() {
        return image;
    }

    public void setXspeed(double xspeed) {
        Xspeed = xspeed;
    }

    public void setYspeed(double yspeed) {
        Yspeed = yspeed;
    }

    public double getXspeed() {
        return Xspeed;
    }

    public double getYspeed() {
        return Yspeed;
    }

    public Fruit() {
        isSmashed= false;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setClickOnFruitScore(int clickOnFruitScore) {
        ClickOnFruitScore = clickOnFruitScore;
    }


    public int getClickOnFruitScore() {
        return ClickOnFruitScore;
    }

    public boolean isSmashed() {
        return isSmashed;
    }
    public void smash(){
        isSmashed = true;
    }
    public Image getSplashImage() {
        return splashImage;
    }
    public void setSplashImage(Image splashImage) {
        this.splashImage = splashImage;
    }
}
