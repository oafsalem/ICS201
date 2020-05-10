package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main extends Application {
    public static final int windowWidth= 1024;
    public static final int windowHeight=700;
    public static Scene mainMenuScene;
    // these variables are defined here because they are needed in all setScene methods and in the animation timer class too.
    // with public modifier because these are public info.
    @Override
    public void start(Stage theStage)
    { // Main menu Setup
        theStage.setTitle( "Fruit Smash" );
        AnchorPane mainMenuAnchorPane = new AnchorPane();
        mainMenuAnchorPane.setMinHeight(windowHeight);
        mainMenuAnchorPane.setMinWidth(windowWidth);
        mainMenuAnchorPane.getChildren().add(getBackground());
        // game title image setup
        Rectangle gameTitle = new Rectangle();
        gameTitle.setWidth(355);
        gameTitle.setHeight(355);
        gameTitle.setX(windowWidth/2.0 - gameTitle.getWidth()/2.0);
        gameTitle.setY(40);
        gameTitle.setFill(new ImagePattern(new Image("GameTitle.png")));
        mainMenuAnchorPane.getChildren().add(gameTitle);
        // play button (it's a rectangle so we can fill it with a specific design) setup
        Rectangle playButton = new Rectangle();
        playButton.setFill(new ImagePattern(new Image("PlayButtonEdited2.png")));
        playButton.setHeight(50);
        playButton.setWidth(120);
        playButton.setX(windowWidth/2.0 -playButton.getWidth()/2.0);
        playButton.setY(windowHeight/2.0 + playButton.getHeight()/2.0 + 50);
        mainMenuAnchorPane.getChildren().add(playButton);
        playButton.setOnMouseClicked(mouseEvent -> setStartGameScene(theStage));
        // leaderboard button (rectangle) setup
        Circle leaderboardButton = new Circle();
        leaderboardButton.setFill(new ImagePattern(new Image("LeaderBoardButton2.png")));
        leaderboardButton.setRadius(40);
        leaderboardButton.setCenterY(windowHeight*2/3.0);
        leaderboardButton.setCenterX(windowWidth-150);
        mainMenuAnchorPane.getChildren().add(leaderboardButton);
        leaderboardButton.setOnMouseClicked(mouseEvent -> {
            try {
                setLeaderBoardScene(theStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        // help button (rectangle) setup
        Circle helpButton = new Circle(40);
        helpButton.setCenterX(150);
        helpButton.setCenterY(windowHeight*2/3.0);
        mainMenuAnchorPane.getChildren().add(helpButton);
        helpButton.setFill(new ImagePattern(new Image("Help2.png")));
        helpButton.setOnMouseClicked(mouseEvent -> setHelpScene(theStage));
        // credits button (rectangle) setup
        Rectangle creditsButton = new Rectangle();
        creditsButton.setWidth(300);
        creditsButton.setHeight(50);
        creditsButton.setFill(new ImagePattern(new Image("Credits.png")));
        creditsButton.setX(windowWidth/2.0 -creditsButton.getWidth()/2.0);
        creditsButton.setY(windowHeight/2.0 + playButton.getHeight()/2.0 + 150);
        mainMenuAnchorPane.getChildren().add(creditsButton);
        creditsButton.setOnMouseClicked(mouseEvent -> setCreditsScene(theStage));
        // showing the scene
        mainMenuScene = new Scene(mainMenuAnchorPane,windowWidth, windowHeight);
        theStage.setScene(mainMenuScene);
        theStage.show();
    }

    public static Rectangle getBackground(){  // background making method
        Rectangle background = new Rectangle(windowWidth , windowHeight);
        background.setFill(new ImagePattern(new Image("Background1.jpg")) );
        return background;

    }

    public static void setStartGameScene(Stage theStage){
        AnchorPane gameAnchorPane = new AnchorPane();
        gameAnchorPane.getChildren().add(getBackground());
        // showing Score
        Score score = new Score();
        Text scoreText = new Text("Score: "+score.getScore());
        scoreText.setX(windowWidth - 100);
        scoreText.setY(75);
        scoreText.setScaleX(3);
        scoreText.setScaleY(3);
        gameAnchorPane.getChildren().add(scoreText);
        // making 3 rectangles for the three  signs in the top of the page
        Rectangle rectangle1 = new Rectangle(30, 30);
        Rectangle rectangle2 = new Rectangle(30, 30);
        Rectangle rectangle3 = new Rectangle(30, 30);
        showXSigns(gameAnchorPane, rectangle1, rectangle2, rectangle3);
        final long startNanoTime = System.nanoTime(); // this time will be used to make the game harder while time passes.
        AnimationFruitTimer timer = new AnimationFruitTimer( //the Animation fruit timer implies the AnimationTimer Class which is responsible for the graphical motion
                startNanoTime, gameAnchorPane, score, rectangle1, rectangle2, rectangle3, scoreText, theStage
        );
        timer.start(); // start the motion by calling the handle method in the AnimationFruitTimer class on each frame
        // see the handle method in the AnimationFruitTimer for more details about the motion.
        Scene scene = new Scene(gameAnchorPane , windowWidth, windowHeight);
        theStage.setScene(scene);
    }

    private static void showXSigns(AnchorPane anchorPane, Rectangle rectangle1 , Rectangle rectangle2 ,Rectangle rectangle3) {
        Image x = new Image("X.png");
        rectangle1.setX(windowWidth-30);
        rectangle1.setY(15);
        rectangle2.setX(windowWidth-70);
        rectangle2.setY(15);
        rectangle3.setX(windowWidth-110);
        rectangle3.setY(15);
        rectangle1.setFill(new ImagePattern(x));
        rectangle2.setFill(new ImagePattern(x));
        rectangle3.setFill(new ImagePattern(x));
        anchorPane.getChildren().add(rectangle1);
        anchorPane.getChildren().add(rectangle2);
        anchorPane.getChildren().add(rectangle3);
    }

    public static void setLeaderBoardScene(Stage theStage) throws FileNotFoundException {
        AnchorPane leaderboardAnchorPane = new AnchorPane();
        leaderboardAnchorPane.getChildren().add(getBackground());
        //making the leaderboard sign (rectangle)
        Rectangle leaderBoardSign = new Rectangle(345, 173);
        leaderBoardSign.setFill(new ImagePattern(new Image("LeaderboardTitle.png")));
        leaderBoardSign.setX(windowWidth/2.0 - leaderBoardSign.getWidth()/2.0);
        leaderBoardSign.setY(windowHeight/2.0 + leaderBoardSign.getHeight()/2.0 - 400); // -400 is to shift it up
        leaderboardAnchorPane.getChildren().add(leaderBoardSign);
        // the get scores return all the scores in descending order
        ArrayList<Integer> scores = Score.getScores();
        for (int i =0; i < scores.size() && i < 5; i++){ // we will print the first 5 or if the file contains less it will print the number available
            Text scoreText = new Text(String.format("\t\t\t%d-----------------%d",i+1, scores.get(i)));
            scoreText.setScaleY(2);
            scoreText.setScaleX(2);
            scoreText.setX(windowWidth/2.0 - 175);
            scoreText.setY(windowHeight/3.0 + i*30);
            leaderboardAnchorPane.getChildren().add(scoreText);
        }
        Circle backButton = getBackButton();
        leaderboardAnchorPane.getChildren().add(backButton);
        backButton.setOnMouseClicked(mouseEvent -> theStage.setScene(mainMenuScene));
        theStage.setScene(new Scene(leaderboardAnchorPane, windowWidth, windowHeight));
    }

    public static Circle getBackButton() {
        Circle backButton = new Circle(30);
        backButton.setFill(new ImagePattern(new Image("BackButton2.png")));
        backButton.setCenterX(windowWidth/6.0);
        backButton.setCenterY(windowHeight - 150);
        return backButton;
    }

    private void setHelpScene(Stage theStage){
        AnchorPane mainHelpAnchorPane = new AnchorPane();
        mainHelpAnchorPane.getChildren().add(getBackground());
        String mainHelp = "this game goal is to click on the fruits before they leave the screen"+
                "\nand you have 3 mistakes"+
                "\nevery fruit has it's score and the goal is to aim for a high score each time"+
                "\nthere are two special fruits that are bomb shaped click on the next page to see details ";
        addHelpText(mainHelp, mainHelpAnchorPane);
        Circle backButton = getBackButton();
        mainHelpAnchorPane.getChildren().add(backButton);
        backButton.setOnMouseClicked(mouseEvent -> theStage.setScene(mainMenuScene));
        Circle forwardButton = getForwardButton();
        mainHelpAnchorPane.getChildren().add(forwardButton);
        forwardButton.setOnMouseClicked(mouseEvent -> set2ndHelpScene(theStage));
        Scene scene = new Scene(mainHelpAnchorPane,windowWidth,windowHeight);
        theStage.setScene(scene);
    }

    private void addHelpText(String mainHelp, AnchorPane anchorPane) {
        Text helpText = new Text();
        helpText.setText(mainHelp);
        anchorPane.getChildren().add(helpText);
        helpText.setTextAlignment(TextAlignment.CENTER);
        helpText.setY(windowHeight/2.5);
        helpText.setX(windowWidth/2.0-210);
        helpText.setScaleX(2);
        helpText.setScaleY(2);
        helpText.setLineSpacing(10);
    }

    private Circle getForwardButton() {
        Circle forwardButton = new Circle();
        forwardButton.setRadius(30);
        forwardButton.setFill(new ImagePattern(new Image("ForwardButton.jpg")));
        forwardButton.setCenterX(windowHeight+180);
        forwardButton.setCenterY(windowHeight-150);
        return forwardButton;
    }

    private void set2ndHelpScene(Stage theStage) {
        AnchorPane secondHelpAnchorPane = new AnchorPane();
        secondHelpAnchorPane.getChildren().add(getBackground());
        // showing the red bomb to explain its function
        Circle redBombImage = new Circle();
        redBombImage.setRadius(50);
        redBombImage.setFill(new ImagePattern(new Image("RedBomb.png")));
        secondHelpAnchorPane.getChildren().add(redBombImage);
        redBombImage.setCenterX(windowWidth/2.0);
        redBombImage.setCenterY(windowHeight/2.0-270);
        // text showing
        String secondHelp = "this fruit is a Bomb and when you click it with left click, it explodes and the game will end"+
                "\n and you can't let it go away it will count as a mistake"+
                "\nbut you can click on it with the right click and it will give you one bonus mistake up to 3"+
                "\nthe extra chances are denoted with green X ";
        addHelpText(secondHelp , secondHelpAnchorPane);
        //
        Circle backButton = getBackButton();
        secondHelpAnchorPane.getChildren().add(backButton);
        backButton.setOnMouseClicked(mouseEvent -> setHelpScene(theStage));
        Circle forwardButton = getForwardButton();
        secondHelpAnchorPane.getChildren().add(forwardButton);
        forwardButton.setOnMouseClicked(mouseEvent -> set3rdHelpScene(theStage));
        Scene scene = new Scene(secondHelpAnchorPane,windowWidth,windowHeight);
        theStage.setScene(scene);
    }

    private void set3rdHelpScene(Stage theStage) {
        AnchorPane thirdHelpAnchorPane = new AnchorPane();
        thirdHelpAnchorPane.getChildren().add(getBackground());
        // showing the blue bomb to explain its function
        Circle blueBomb = new Circle();
        blueBomb.setRadius(50);
        blueBomb.setFill(new ImagePattern(new Image("BlueBomb.png")));
        thirdHelpAnchorPane.getChildren().add(blueBomb);
        blueBomb.setCenterX(windowWidth/2.0);
        blueBomb.setCenterY(windowHeight/2.0-270);
        //text showing
        String thirdHelp = "this fruit is a Bomb and when you click it with left click, it decreases your score"+
                "\n and you can't let it go away it will count as a mistake"+
                "\nbut you can click on it with the right click and it will give you a bonus to the score"+
                "\njust like a normal fruit ";
        addHelpText(thirdHelp, thirdHelpAnchorPane);
        //
        Circle backButton = getBackButton();
        thirdHelpAnchorPane.getChildren().add(backButton);
        backButton.setOnMouseClicked(mouseEvent -> set2ndHelpScene(theStage));
        Scene scene = new Scene(thirdHelpAnchorPane,windowWidth,windowHeight);
        theStage.setScene(scene);
    }

    private void setCreditsScene(Stage theStage) {
        AnchorPane creditsAnchorPane = new AnchorPane();
        creditsAnchorPane.getChildren().add(getBackground());
        // showing the ics department logo
        ImagePattern imagePattern = new ImagePattern(new Image("ICSDepartmentLogo.png"));
        Rectangle rectangle = new Rectangle(350 , 82);
        rectangle.setY(20);
        rectangle.setX(windowWidth/2.0 - rectangle.getWidth()/2.0);
        rectangle.setFill(imagePattern);
        creditsAnchorPane.getChildren().add(rectangle);
        //credits text showing
        Text text = new Text();
        text.setText("Fruit Smash" +
                "\nKing Fahd University of Petroleum and Minerals" +
                "\nICS201 Term 192 Final Project, Group-25" +
                "\nInstructors:" +
                "\nDr.Yahya Garout & Dr.Abdulmajeed Othman" +
                "\nGroup Members who Designed and Programmed The game:" +
                "\nMohammed Hosam & Osama Salem");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setY(220);
        text.setX(windowWidth/2.0 - 170);
        text.setScaleX(2);
        text.setScaleY(2);
        text.setLineSpacing(10);
        creditsAnchorPane.getChildren().add(text);
        //
        Circle backButton = getBackButton();
        creditsAnchorPane.getChildren().add(backButton);
        backButton.setCenterX(windowWidth/6.0);
        backButton.setCenterY(windowHeight - 150);
        backButton.setOnMouseClicked(mouseEvent -> theStage.setScene(mainMenuScene));
        Scene scene = new Scene(creditsAnchorPane , windowWidth , windowHeight);
        theStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}