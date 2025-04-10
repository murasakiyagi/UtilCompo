package yagi.murasaki.utilCompo.event.eventAbst;

import java.io.*;
import java.util.*;
import java.util.function.*;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
//import javafx.scene.image.ImageView;
//import javafx.scene.Camera;
import javafx.scene.Scene;
//import javafx.scene.shape.Rectangle;
//import javafx.geometry.Point2D;
//import javafx.scene.layout.Pane;
//import javafx.geometry.Bounds;


import yagi.murasaki.utilCompo.quick.QuickUtil;
import yagi.murasaki.utilCompo.event.HandlerFace;
import yagi.murasaki.utilCompo.information.InfoSubject;
import yagi.murasaki.utilCompo.information.Information;

//MouseEventの座標について
//	getX(), getScreenX(), getSceneX()
//	getSceneXはSceneの相対位置で、マウスカーソルが移動していなくてもsceneの座標が変わればマウスカーソルの相対位置は変わる。
//	EventはNodeかSceneのどちらかのソースに適用される。getXは適用されたソースの相対位置
//	getScreenXはウィンドウの左上を起点にした絶対位置
//
//
//Node.mouseTransparent
//	マウスイベントに対する透過性
//
//Node.viewOrder
//	Nodeのレンダリング順序
//	通常、Paneなどが持つObservableListの子の順序でレンダリングされるが、このオーダーが優先される


/**
* マウスイベントの対応
* 
* 
* 
*/
public abstract class MouseHandlerAbst implements HandlerFace<MouseEvent> {

	//座標
	/** * start/final screenXY。PC画面の絶対値 */
	protected double ssx, ssy, fsx, fsy;
	/** * getX：イベントの元になったNodeの座標 */
	protected double sx, sy, fx, fy;
	/** * scene：の座標 */
	protected double csx, csy, cfx, cfy;
	/** * translate：移動分 */
	protected double tlx, tly;
//	Camera camera;
	/** * タイプ */
	protected String type;
	/** * 座標保持 */
	protected int row, col;
//	/** * 囲み */
//	Rectangle surround;
	
	/** * mauseで選択したノードの情報 */
	protected Information info;
	
	/** * エントリーするだけで反応するイベントのオンオフ。邪魔ならfalseにする */
	protected boolean isEntered;//
	/** * マウスの左クリック */
	protected boolean isPrimary;
	/** * マウスの右クリック */
	protected boolean isSecondary;
	
	/**
	* コンストラクタ
	* Informationインスタンスあり
	*/
	public MouseHandlerAbst() {
		this.info = new Information();
	}

//プレス
	/**
	* マウス・プレス・イベント対応
	* @param e マウスイベント
	*/
	public abstract void handle(MouseEvent e);

	/**
	* マウス・プレス・イベントの予備動作
	* @param e マウスイベント
	*/
	public void subHandle(MouseEvent e) {//press
		start(e);
		info.mouseSelect(e);

		if( e.isPrimaryButtonDown() ) {//このメソッドはクリックではできない
			print("プライマリ・プレス");
			isPrimary = true;
//			nodeInfo();
		} else if( e.isSecondaryButtonDown() ) {
			print("セカンダリ・プレス");
			isSecondary = true;
			isEntered = !isEntered;
			print("isEntered", isEntered);
		}
	}
	
		/**
		* mousePressed開始位置取得
		* @param e マウスイベント
		*/
		private void start(MouseEvent e) {//mousePressed
			ssx = e.getScreenX();
			ssy = e.getScreenY();
			sx = e.getX();
			sy = e.getY();
		}

		/**
		* mouseReleaseかdoraggにて呼び出し
		* @param e マウスイベント
		*/
		private void finish(MouseEvent e) {
			fsx = e.getScreenX();
			fsy = e.getScreenY();
			fx = e.getX();
			fy = e.getY();
		}
		
		/** * 選択されたノードを保持 */
		protected void nodeInfo() {
			Node nd = info.getNode();
				print("Node.typeSelect", nd.getTypeSelector());
		}

//リリース
	/**
	* マウス・リリース・イベント対応
	* @param e マウスイベント
	*/
	public void handle2(MouseEvent e) {//リリース
		if(isPrimary && isSecondary) {
			print("ボゥス・リリース（クリックより前）");
		} else if(isPrimary) {
			print("プライマリ・リリース（クリックより前）");
		} else {
			print("セカンダリ・リリース（クリックより前）");
		}
	}//release


//クリック。
	/**
	* マウス・クリック・イベント対応
	* @param e マウスイベント
	*/
	public void handle3(MouseEvent e) {//クリック
// 		クリックの場合ボタンダウンは絶対false
		if(isPrimary && isSecondary) {
			print("ボゥス・クリック");
			isPrimary = false;
			isSecondary = false;
		} else if(isPrimary) {
			print("プライマリ・クリック");
			isPrimary = false;
		} else {//secondary
			print("セカンダリ・クリック");
			isSecondary = false;
		}
	}//click


//ドラッグ
	/**
	* マウス・ドラッグ・イベント対応
	* @param e マウスイベント
	*/
	public void handle4(MouseEvent e) {//ドラッグ
		finish(e);
		if(isPrimary) {
//			cameraMove(e);
		} else {//secondary
			
		}
	}//dragg


//エントリー
	/**
	* マウス・エントリー・イベント対応
	* @param e マウスイベント
	*/
	public void handle5(MouseEvent e) {//エントリー（範囲内）
// 		NodeというかもうWindowに入った時だけ発生
// 		各Nodeの境界で発生すると思った。sceneに割り当ててるから
		if(isEntered) {
//			print("handle5 エントリード");
		}
	}//ENTERED
		


	/** * 便利機能 */
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	/**
	* 「+" "+」でつなげるのめんどいから
	* @param objs 可変長Object
	*/
	public void print(Object... objs) {
		qu.print(objs);
	}
	
	/**
	* 型名を調べる下よりシンプル
	* node.getTypeSelector() == node.getClass().getName()
	* @param obj 調べたいオブジェクト
	* @return オブジェクトの型名
	*/
	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}

}