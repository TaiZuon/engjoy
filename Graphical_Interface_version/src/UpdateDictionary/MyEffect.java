package UpdateDictionary;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MyEffect {
        //fade in
    public static void fadeIn(Node a, double milisec) {
        a.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.millis(milisec), a);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    //fade out
    public static void fadeOut(Node a, double milisec) {
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.millis(milisec), a);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    //move 1 dir
    public static void moveIn(Node a, int x, int y, double milisec) {
        TranslateTransition paneTransition = new TranslateTransition(javafx.util.Duration.millis(milisec), a);
        paneTransition.setToX(x);
        paneTransition.setToY(y);
        paneTransition.play();
    }

    //move to pos x, y
    public static void moveTo(Node a, int x, int y, double milisec) {
        TranslateTransition paneTransition = new TranslateTransition(javafx.util.Duration.millis(milisec), a);
        paneTransition.setToX(x - a.getLayoutX());
        paneTransition.setToY(y - a.getLayoutY());
        paneTransition.play();
    }
    //move 
    public static void moveBacknFord() {

    }

    //grow 
    public static void growPane(Pane a, int oldwidth, int oldheight, int width, int height, double milisec) {
        // Create a transition that changes the pane size
        Transition transition = new Transition () {
          {
            // Set the cycle duration to 2 seconds
            setCycleDuration (Duration.millis(milisec));
            // Set the number of cycles to 2
            setCycleCount (1);
            // Set the auto reverse to true
            setAutoReverse (false);
          }
      
          @Override
          protected void interpolate (double fraction) {
            // Set the prefWidth and prefHeight according to the fraction
            a.setPrefWidth (oldwidth * (1 - fraction) + width * fraction);
            a.setPrefHeight (oldheight * (1 - fraction) + height * fraction);
          }
        };

        // Play the transition
        transition.play ();
    }

    //thu nho
    public static void shrinkPane(Pane a, int oldwidth, int oldheight, int width, int height, double milisec) {
        // Create a transition that changes the pane size
        Transition transition = new Transition () {
          {
            // Set the cycle duration to 2 seconds
            setCycleDuration (Duration.millis(milisec));
            // Set the number of cycles to 1
            setCycleCount (1);
            // Set the auto reverse to false
            setAutoReverse (false);
          }
      
          @Override
          protected void interpolate (double fraction) {
            // Set the prefWidth and prefHeight according to the fraction
            // Assume x and y are the desired width and height
            a.setPrefWidth (oldwidth * (1 - fraction) + width * fraction);
            a.setPrefHeight (oldheight * (1 - fraction) + height * fraction);
          }
    };

// Play the transition
transition.play ();

    }
};
