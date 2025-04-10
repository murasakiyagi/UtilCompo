package yagi.murasaki.utilCompo.event;

import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.input.KeyEvent;


/**
* 対応キーを管理
*/
public class Keys {
	
	/** * 空のコンストラクタ */
	public Keys() {}
	
	/** * 各キーの状態 */
//	public static boolean 	isQ, isW, isP, 
	protected boolean 	isQ, isW, isO, isP, 
						isZ, isX, isC, isV, isB, isN, isM, 
						isUp, isDown, isLeft, isRight;

	/**
	* 入力キーの状態を保持
	* @param e キーイベント
	*/
	public void keyP(KeyEvent e) {
		switch(e.getCode()) {
			case UP:
				isUp = true;
				break;
			case DOWN:
				isDown = true;
				break;
			case LEFT:
				isLeft = true;
				break;
			case RIGHT:
				isRight = true;
				break;
			case Z:
				isZ = true;
				break;
			case X:
				isX = true;
				break;
			case C:
				isC = true;
				break;
			case V:
				isV = true;
				break;
			case B:
				isB = true;
				break;
			case N:
				isN = true;
				break;
			case M:
				isM = true;
				break;
			case Q:
				isQ = true;
				break;
			case W:
				isW = true;
				break;
			case O:
				isO = true;
				break;
			case P:
				isP = true;
				break;
			default:
				break;
		}
	}
	
	/**
	* 放されたキーの状態を戻す
	* @param e キーイベント
	*/
	public void keyR(KeyEvent e) {
		switch(e.getCode()) {
			case UP:
				isUp = false;
				break;
			case DOWN:
				isDown = false;
				break;
			case LEFT:
				isLeft = false;
				break;
			case RIGHT:
				isRight = false;
				break;
			case Z:
				isZ = false;
				break;
			case X:
				isX = false;
				break;
			case C:
				isC = false;
				break;
			case V:
				isV = false;
				break;
			case B:
				isB = false;
				break;
			case N:
				isN = false;
				break;
			case M:
				isM = false;
				break;
			case Q:
				isQ = false;
				break;
			case W:
				isW = false;
				break;
			case O:
				isO = false;
				break;
			case P:
				isP = false;
				break;
			default:
				break;
		}
	}

	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean Q() { return isQ; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean W() { return isW; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean O() { return isO; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean P() { return isP; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean Z() { return isZ; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean X() { return isX; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean C() { return isC; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean V() { return isV; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean B() { return isB; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean N() { return isN; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean M() { return isM; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean up() { return isUp; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean down() { return isDown; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean left() { return isLeft; }
	/**
	* キーの状態
	* @return キーの状態
	*/
	public boolean right() { return isRight; }

}