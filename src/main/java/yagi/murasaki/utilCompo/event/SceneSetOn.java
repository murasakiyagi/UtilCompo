package yagi.murasaki.utilCompo.event;

import java.io.*;
import java.util.*;

import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.Camera;


/**
* シーンにイベントを紐付ける
*/
public abstract class SceneSetOn implements SetOnFace {

	/** * 受け取る */
	protected Scene scene;
	/** * サブクラスにて生成 */
	protected Keys keys;
	/** * サブクラスにて生成 */
	protected HandlerFace<KeyEvent> keyHand;
	/** * サブクラスにて生成 */
	protected HandlerFace<MouseEvent> mouseHand;
	/** * サブクラスにて生成 */
	protected HandlerFace<ScrollEvent> scrollHand;

	/**
	* サブクラスのコンストラクタで
	* 	super(
	* 		scene,
	* 		new KeyHandler(),,,
	* 	}
	* 
	* @param scene イベントと紐付けるシーン
	* @param keys キーの状態を管理
	* @param keyHand キー・イベント・ハンドラー
	* @param mouseHand マウス・イベント・ハンドラー
	* @param scrollHand スクロール・イベント・ハンドラー
	*/
	public SceneSetOn(
		Scene scene,
		Keys keys,
		HandlerFace<KeyEvent> keyHand,
		HandlerFace<MouseEvent> mouseHand, 
		HandlerFace<ScrollEvent> scrollHand
	) {
		this.scene = scene;
		this.keyHand = keyHand;
		this.mouseHand = mouseHand;
		this.scrollHand = scrollHand;
		this.keys = keys;
		setOn();
	}

	/**
	* シーンにイベントをセットする
	*/
	public void setOn() {
		scene.setOnKeyPressed(e -> keyHand.handle(e));
		scene.setOnKeyReleased(e -> keyHand.handle2(e));
		scene.setOnMousePressed(e -> mouseHand.handle(e));
		scene.setOnMouseReleased(e -> mouseHand.handle2(e));
		scene.setOnMouseClicked(e -> mouseHand.handle3(e));
		scene.setOnMouseDragged(e -> mouseHand.handle4(e));
		scene.setOnMouseEntered(e -> mouseHand.handle5(e));
		scene.setOnScroll(e -> scrollHand.handle(e));
	}

	
//	public void action(ActionEvent e) {
//
//	}
//
//	public void keyP(KeyEvent e) {
//		System.out.println("KEYTEST  PRESS");
//	}
//
//	public void keyR(KeyEvent e) {
//		System.out.println("KEYTEST  RELEASE");
//	}
//
//	public void mouseD(MouseEvent e) {
//		System.out.println("MOUSETEST  DRAGG");
//	}
//	
//	public void mouseC(MouseEvent e) {
//		System.out.println("MOUSETEST  CLICK");
//	}
//	
//	public void mouseP(MouseEvent e) {
//		System.out.println("MOUSETEST  PRESS");
//	}
//	
//	public void mouseR(MouseEvent e) {
//		System.out.println("MOUSETEST  RELEASE");
//	}
//	
//	public void mouseE(MouseEvent e) {
//		System.out.println("MOUSETEST  ENTER");
//	}
//
//	public void scroll(ScrollEvent e) {
//		System.out.println("SCROLLTEST  SCROLL");
//	}


}




