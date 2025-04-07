package yagi.murasaki.utilCompo.main;

import java.io.*;
import java.util.*;
import java.awt.Point;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

//import yagi.murasaki.utilCompo.quick.time.Timer;

/**
* 動作実験用として汎用的に使う
*/
public class UtilCompo extends Application {

    private Pane p = new Pane();
    List<Point> plist = new ArrayList<>();
    List<Rectangle> reclist = new ArrayList<>();
    
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        launch(args);
    }
    
    public void start(Stage stage) throws Exception {
//		Pane p = new Pane();
        Scene scene = new Scene(p, 300, 300);
        stage.setScene(scene);
        stage.show();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
//                reclist.add(new Rectangle(11 * j, 11 * i, 10, 10));
                plist.add(new Point(j, i));
            }
        }
        
		//ゲームループの起動。必須
		new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				animationLoop();
			}
		}.start();
	}

    long loopCnt = 0;//Long.MIN_VALUE;
    private void animationLoop() {
        loopCnt++;
        bifor();
    }

    int fcnt = 0;
    private boolean bifor() {
        if(fcnt < reclist.size()) {
            if(loopCnt % 30 == 0) {
                p.getChildren().add(reclist.get(fcnt++));
                System.out.println("JOIs");
            }
            return true;
        } else {
            return false;
        }
    }

}