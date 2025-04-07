package yagi.murasaki.utilCompo.gui;

import java.io.*;
import java.util.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.PickResult;
import javafx.scene.text.Text;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* 変数格納せずに、親ステージpareを取り込んでnewするだけ
* 
*/
public class SatelliteStage extends Stage {

	/**
	* 親ステージ
	*/
	protected Stage pare;
	
	protected Pane pane;
	protected int width, height;

	/**
	* newするだけ
	* @param pare 親ステージ（superじゃない）
	* @param width 横長さ
	* @param height 縦長さ
	* @param pos "ru":right up右上, "ld":left down, "rd", "lu"
	*/
	public SatelliteStage(Stage pare, int width, int height, String pos) {
		super();
		this.pare = pare;
		this.width = width;
		this.height = height;
		pane = new Pane();
		Scene scene = new Scene(pane, width, height);
		this.setTitle("engine.SatelliteStage");
		this.setScene(scene);
		this.show();
		position(pos);
	}
	
	/**
	* 配置調整
	* @param x 横位置
	* @param y 縦位置
	*/
	public void position(double x, double y) {
//		setX(pare.getX() + pare.getWidth());
//		setY(pare.getY() + pare.getHeight());
		setX(x);
		setY(y);
	}

		/**
		* 配置調整
		* @param pos "ru":right up右上, "ld":left down, "rd", "lu"
		*/
		public void position(String pos) {
			if(pos.equals("ru")) {//右上
				position(pare.getX() + pare.getWidth(), pare.getY());
			} else if(pos.equals("rd")) {//右下
				position(pare.getX() + pare.getWidth(), pare.getY() + height);
			} else if(pos.equals("lu")) {//左上
				position(pare.getX() - width, pare.getY());
			} else if(pos.equals("ld")) {//左下
				position(pare.getX() - width, pare.getY() + height);
			}
		}

	/**
	* 長さ調整
	* @param w 横長さ
	* @param h 縦長さ
	*/
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	/**
	* 「+" "+」でつなげるのめんどいから
	* @param objs 可変長Object
	*/
	public void print(Object... objs) {
		qu.print(objs);
	}

}