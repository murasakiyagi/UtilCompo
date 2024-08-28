package utilCompo.gui;

import java.io.*;
import java.util.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.PickResult;
import javafx.scene.text.Text;

import utilCompo.quick.QuickUtil;

/**
* デバッグ用なので、デザインとか気にしない
*/
public class SatelliteStage extends Stage {

	/**
	* 親ステージ
	*/
	protected Stage pare;
	protected Pane pane;
	protected int width, height;

	//インスタンスするだけで出る
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
	
	public void position(double x, double y) {
//		setX(pare.getX() + pare.getWidth());
//		setY(pare.getY() + pare.getHeight());
		setX(x);
		setY(y);
	}

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

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

}