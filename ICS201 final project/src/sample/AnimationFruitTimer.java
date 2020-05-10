package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;

import static sample.Main.windowHeight;
import static sample.Main.windowWidth;

public class AnimationFruitTimer extends AnimationTimer {
    Fruit fruit;
    long startNanoTime;
    AnchorPane anchorPane;
    Score score;
    Rectangle rectangle1;
    Rectangle rectangle2;
    Rectangle rectangle3;
    Text scoreText;
    private static int mistake=0;
    private double fruitTime; // this time is taken when ever we make a fruit to move the fruit as a function of time
    Stage theStage;

    // basically we have everything that we need for motion in this class

    public AnimationFruitTimer(
            long startNanoTime, AnchorPane anchorPane, Score score, Rectangle rectangle1 , Rectangle rectangle2 , Rectangle rectangle3, Text scoreText, Stage theStage
    ) {
        this.fruit = makeRandomFruit();
        getRotateTransition().play();
        anchorPane.getChildren().add(this.fruit.getShape());
        this.startNanoTime = startNanoTime;
        this.fruitTime = startNanoTime;
        this.anchorPane = anchorPane;
        this.score = score;
        this.rectangle1= rectangle1;
        this.rectangle2 = rectangle2;
        this.rectangle3 = rectangle3;
        this.scoreText = scoreText;
        this.theStage = theStage;
    }

    private static Fruit makeRandomFruit() {
        int choice = (int) (Math.random()*9);
        switch (choice){
            case 1:
                return new Watermelon();
            case 2:
                return new Strawberry();
            case 3:
                return new Apple();
            case 4:
                return new Banana();
            case 5:
                return new Melon();
            case 6:
                return new Peach();
            case 7:
                return new EndGameBomb();
            case 8:
                return new MinusScoreBomb();
            default:
                return new Kiwi();
        }
    }

    @Override
    public void handle(long l) { //this method is being called on each frame
        double totalTime = (l - startNanoTime) / 1000000000.0; // the time passed since clicking play button
        double t = (l - fruitTime) / 1000000000.0; // the time passed since the last fruit spawn
        double x = (fruit.getXspeed()*t*21) + 0.06*totalTime;
        // here we are using the equation -> x = xInitial + vInitial*time + 0.5 * acceleration*t^2
        // and the xInitial is zero since it starts from the most left side
        // and the acceleration is zero in the x direction
        // and we added the 0.06*totalTime to make the object move faster and basically making the game harder when the game gets lengthier
        // the factor 21 was added to the equation after many trails to make the speed more reasonable
        double y = (-fruit.getYspeed()*t*2 + 0.5*9.8*t*t)*7.5;
        // here we are using the equation -> y = yInitial + vInitial*time + 0.5 * acceleration*t^2
        // here the signs are flipped since the y coordinated in the pane is opposite to the mathematical one
        // also the y initial was set already in the fruit making process
        // the acceleration was set to be 9.8 which is the earth acceleration near the surface
        // the 2 factor was multiplied to make the object go a little bit higher before going down
        // the 7.5 factor was made to make the motion faster to match the motion with the x axis

        // the repositioning is here rather than the Fruit class because it makes the motion smother
        fruit.getShape().setLayoutX(x);
        fruit.getShape().setLayoutY(y);
        // and we will update the coordinates variable for the fruit
        fruit.setyPosition(y);
        fruit.setxPosition(x);

        //if the object goes off boundaries
        if (fruit.getxPosition() > windowWidth || fruit.getyPosition() > windowHeight)
            offBoundariesFunction();

        // if the fruit or bomb is clicked
        fruit.getShape().setOnMouseClicked(mouseEvent -> {
            if(fruit instanceof EndGameBomb){
                endGameBombFunction(mouseEvent);
            }
            else if(fruit instanceof MinusScoreBomb ){ // if its a blue bomb
                minusScoreBombFunction(mouseEvent, totalTime);
            }
            else if(mouseEvent.getButton()==MouseButton.PRIMARY)  { // any fruit must be left clicked so no one can keep right clicking everything which will make the bombs idea useless
                fruitFunction(totalTime);
            }
        });
    }

    private void offBoundariesFunction() {
        anchorPane.getChildren().remove(fruit.getShape());
        mistake++;
        playSound("Confusion");
        colorTheXSigns();

        if (mistake >= 3) {
            // losing criteria
            playSound("Buzz");
            stop();
            try {
                showLosingScreen();
                mistake =0; // resitting the mistakes because if the player wants to play again it will bug
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        else {
            // if he didnt lose yet
            fruit = makeRandomFruit();
            getRotateTransition().play();
            anchorPane.getChildren().add(fruit.getShape());
            fruitTime = System.nanoTime();
        }
    }

    private void endGameBombFunction(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) { // bomb right click is lose;
            mistake = 3;
            colorTheXSigns();
            anchorPane.getChildren().remove(fruit.getShape());
            playSound("Buzz");
            stop();
            try {
                showLosingScreen();
                mistake = 0; // resetting the mistakes because if he is going to play again the mistakes will stay the same
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(mouseEvent.getButton()==MouseButton.SECONDARY){ // bomb left click is save

            if(mistake>-3){
                mistake--;
                colorTheXSigns();
            }

            playSound("Heal");
            splash();
            anchorPane.getChildren().remove(fruit.getShape());
            fruit.smash();
            fruit = makeRandomFruit();
            getRotateTransition().playFromStart();
            anchorPane.getChildren().add(fruit.getShape());
            fruitTime = System.nanoTime();
        }
    }

    private void minusScoreBombFunction(MouseEvent mouseEvent, double totalTime) {
        if (mouseEvent.getButton()==MouseButton.SECONDARY){ // if right clicked its a save
            calculateMinusScore(totalTime);
            scoreText.setText("" + score.getScore());
            splash();
            anchorPane.getChildren().remove(fruit.getShape());
            playSound("Coin2");
            fruit.smash();
        }
        else { // if its left clicked its a minus
            splash();
            anchorPane.getChildren().remove(fruit.getShape());
            playSound("Confusion");
            fruit.smash();
            calculateScore(totalTime); // mechanism for decreasing more points if the game is longer
            scoreText.setText("" + score.getScore());

        }
        fruit = makeRandomFruit();
        getRotateTransition().playFromStart();
        anchorPane.getChildren().add(fruit.getShape());
        fruitTime = System.nanoTime();

    }

    private void calculateScore(double totalTime) {
        if (totalTime > 60)
            score.addScore(fruit.getClickOnFruitScore() * 2);
        else if(totalTime > 100)
            score.addScore(fruit.getClickOnFruitScore()* 4);
        else if (totalTime > 120)
            score.addScore(fruit.getClickOnFruitScore()*6);
        else if (totalTime > 150)
            score.addScore(fruit.getClickOnFruitScore()*10);
        else
            score.addScore(fruit.getClickOnFruitScore());
    }

    private void calculateMinusScore(double totalTime) {
        if (totalTime > 60)
            score.addScore(fruit.getClickOnFruitScore() * -2);
        else if(totalTime > 100)
            score.addScore(fruit.getClickOnFruitScore()* -4);
        else if (totalTime > 120)
            score.addScore(fruit.getClickOnFruitScore()*-6);
        else if (totalTime > 150)
            score.addScore(fruit.getClickOnFruitScore()*-10);
        else
            score.addScore(-fruit.getClickOnFruitScore());
    }
    private void splash() {
        Circle splash = new Circle(30);
        splash.setFill(new ImagePattern(fruit.getSplashImage()));
        splash.setCenterY(fruit.getShape().getLayoutY() + fruit.getShape().getLayoutBounds().getMinY());
        splash.setCenterX(fruit.getShape().getLayoutX());
        // the fade is to remove the splash after a while
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10),splash);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        anchorPane.getChildren().add(splash);
    }

    private void fruitFunction(double totalTime) {
        splash();
        anchorPane.getChildren().remove(fruit.getShape());
        playSound("Score");
        fruit.smash();
        calculateScore(totalTime); // mechanism for adding more points if the game is longer
        scoreText.setText("" + score.getScore());
        fruit = makeRandomFruit();
        getRotateTransition().playFromStart();
        anchorPane.getChildren().add(fruit.getShape());
        fruitTime = System.nanoTime();
    }

    private void showLosingScreen() throws FileNotFoundException {
        Rectangle gameOverImage = new Rectangle(322,189);
        gameOverImage.setY(windowHeight/2.0 - gameOverImage.getHeight()/2.0 -200); // -200 to shift it a little bit higher to show the score below it
        gameOverImage.setX(windowWidth/2.0 - gameOverImage.getWidth()/2.0);
        gameOverImage.setFill(new ImagePattern(new Image("GameOver.png")));
        anchorPane.getChildren().add(gameOverImage);
        score.saveToFile();
        scoreText.setText("Your Score is: " + score.getScore());
        scoreText.setX(windowWidth/2.0 - 50);
        scoreText.setY(windowHeight/2.0);
        // back button
        Circle backButton = Main.getBackButton();
        backButton.setOnMouseClicked(mouseEvent -> theStage.setScene(Main.mainMenuScene));
        // try again button
        Circle tryAgainButton = new Circle(30);
        tryAgainButton.setFill(new ImagePattern(new Image("TryAgain.png")));
        anchorPane.getChildren().add(tryAgainButton);
        tryAgainButton.setCenterX(windowWidth/2.0);
        tryAgainButton.setCenterY(windowHeight - 150);
        tryAgainButton.setOnMouseClicked(mouseEvent -> Main.setStartGameScene(theStage));
        // leaderboard button
        Circle leaderboardButton = new Circle();
        leaderboardButton.setFill(new ImagePattern(new Image("LeaderBoardButton2.png")));
        leaderboardButton.setRadius(30);
        leaderboardButton.setCenterY(windowHeight -150);
        leaderboardButton.setCenterX(windowWidth*5/6.0);
        anchorPane.getChildren().add(leaderboardButton);
        leaderboardButton.setOnMouseClicked(mouseEvent -> {
            try {
                Main.setLeaderBoardScene(theStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void colorTheXSigns() {
        Image X = new Image("X.png");
        ImagePattern defaultImagePattern = new ImagePattern(X);
        Image RedX = new Image("RedX.png");
        ImagePattern redXImagePattern = new ImagePattern(RedX);
        Image GreenX = new Image("GreenX.png");
        ImagePattern greenXImagePattern = new ImagePattern(GreenX);
        switch (mistake) {
            case -3:
                rectangle3.setFill(greenXImagePattern);
                rectangle2.setFill(greenXImagePattern);
                rectangle1.setFill(greenXImagePattern);
                break;
            case -2:
                rectangle3.setFill(defaultImagePattern);
                rectangle2.setFill(greenXImagePattern);
                rectangle1.setFill(greenXImagePattern);
                break;
            case -1:
                rectangle1.setFill(greenXImagePattern);
                rectangle3.setFill(defaultImagePattern);
                rectangle2.setFill(defaultImagePattern);
                break;
            case 0:
                rectangle3.setFill(defaultImagePattern);
                rectangle2.setFill(defaultImagePattern);
                rectangle1.setFill(defaultImagePattern);
                break;
            case 1:
                rectangle1.setFill(redXImagePattern);
                rectangle3.setFill(defaultImagePattern);
                rectangle2.setFill(defaultImagePattern);
                break;
            case 2:
                rectangle1.setFill(redXImagePattern);
                rectangle2.setFill(redXImagePattern);
                rectangle3.setFill(defaultImagePattern);
                break;
            case 3:
                rectangle1.setFill(redXImagePattern);
                rectangle2.setFill(redXImagePattern);
                rectangle3.setFill(redXImagePattern);

        }
    }
    public RotateTransition getRotateTransition() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(15), fruit.getShape());
        rotateTransition.setByAngle(500);
        rotateTransition.setRate(4);
        return rotateTransition;
    }

    public static void playSound(String soundName){
        String sound = soundName.concat(".wav");
        Media loseSound= new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(loseSound);
        mediaPlayer.play();
    }
}