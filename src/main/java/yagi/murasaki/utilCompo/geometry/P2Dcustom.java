//cbの感知クラス
package yagi.murasaki.utilCompo.geometry;

import java.io.*;
import java.util.*;
import java.awt.Point;
import javafx.geometry.Point2D;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* Point2Dカスタム
* 
* 
* 
*/
public class P2Dcustom extends Point2D {

	/** * staticメソッドを使えるように */
	private static P2Dcustom KARI = new P2Dcustom(0,0);//static用
	/** * ０〜３ */
	private List<Integer> yonList;
	/** * yonListコピー */
	private List<Integer> fourList;

	/** * 0,0座標 */
	public P2Dcustom() {
		super(0,0);
		init();
	}
	/**
	* 指定座標
	* @param x 横座標
	* @param y 縦座標
	*/
	public P2Dcustom(double x, double y) {
		super(x,y);
		init();
	}
	/**
	* 他の座標参照
	* @param pd 他の座標
	*/
	public P2Dcustom(Point2D pd) {
		super(pd.getX(), pd.getY());
		init();
	}
	
	/** * 初期化 */
	private void init() {
		yonList = List.of(0,1,2,3);//next用に作ったので、0~3
		fourList = new ArrayList<>();
		fourList.addAll(yonList);
//		this.print(fourList);
	}
	
	
	/**
	* Point2DはStringと同様、イミュータブルの様で(セッターが無い)、
	* 	データ隠蔽がされている様で、、、newを使わざるを得ない。
	* @param pd Point2Dオブジェクト
	* @return 新しいP2Dcustom
	*/
	public static P2Dcustom cast(Point2D pd) {
		return new P2Dcustom(pd);
	}

	/**
	* SE Pointの座標を元に、新しいFX Point2Dを返す
	* @param pt Pointオブジェクト
	* @return 新しいPoint2D
	*/
	static public Point2D fxp2d(Point pt) {
		return new Point2D(pt.getX(), pt.getY());
	}

	/**
	* FX Point2Dの座標を元に、新しいSE Pointを返す。少数１位をラウンドしてある。
	* @param pd FX Point2Dオブジェクト
	* @return 新しいPoint
	*/
	static public Point deFxp2d(Point2D pd) {
		return new Point(intX(pd), intY(pd));
	}

	/**
	* SE Pointのリストから、FX Point2Dのリストを返す
	* @param list SE Pointのリスト
	* @return 新しいPoint2Dリスト
	*/
	static public List<Point2D> fxList(List<Point> list) {
		List<Point2D> pdList = new ArrayList<>();
		for(Point pt : list) {
			pdList.add(new Point2D(pt.getX(), pt.getY()));
		}
		return pdList;
	}

	/**
	* FX Point2Dのリストから、SE Pointのリストを返す
	* @param list SE Pointのリスト
	* @return 新しいPointリスト
	*/
	static public List<Point> deFxList(List<Point2D> list) {
		List<Point> ptList = new ArrayList<>();
		for(Point2D pd : list) {
			ptList.add(deFxp2d(pd));
		}
		return ptList;
	}


//方向
	/**
	* ここから進むべき方向の計算と加算を同時に
	* tgtPdがここより右上にある場合、signumXYは+1,-1
	* 	this.add(x * 1, y * -1)
	* @param x 進む距離。大きさ
	* @param y 進む距離。大きさ
	* @param tgtPd 方向を特定するための目的座標
	* @return 移動ぶんを加えた新しい座標
	*/
	public Point2D sigAdd(double x, double y, Point2D tgtPd) {
		double addX = x * signumX(tgtPd);//xyは進む距離
		double addY = y * signumY(tgtPd);//signumは方角
		return this.add(addX, addY);//addの戻り値は(new)Point2D。thisの値は変わらない
	}
	
	/**
	* この座標に対する指定座標からXY軸のプラマイの符号を得て、その値からPoint2Dを作る<br>
		指定座標 - この座標<br>
		例、pd:(1, 4) - this:(2, 2)<br>
		return (-1, 1);<br>

	* @param pd 目標座標
	* @return  XYが[-1, 0, 1]のどれかで作られたPoint2D
	*/
	public Point2D signumPd(Point2D pd) {
		double x, y;
		//signumXY()の中にthisが使われていることを忘れない
		x = signumX(pd);
		y = signumY(pd);
		return (new Point2D(x, y));
	}
	
	/**
	* この座標に対する指定座標からX軸のプラマイの符号を得る<br>
		指定座標 - この座標
	* @param pd 目標座標
	* @return  [-1, 0, 1]のどれか
	*/
	public double signumX(Point2D pd) {//X軸の符号を得る
		return Math.signum(pd.getX() - this.getX());
	}
	
	/**
	* この座標に対する指定座標からY軸のプラマイの符号を得る<br>
		指定座標 - この座標
	* @param pd 目標座標
	* @return  [-1, 0, 1]のどれか
	*/
	public double signumY(Point2D pd) {//Y軸の符号を得る
		return Math.signum(pd.getY() - this.getY());
	}
	
	/**
	* この座標から指定の座標の方向を文字列として表す<br>
		nw n0 ne<br>
		0w 00 0e<br>
		sw s0 se<br>
	* @param pd 目標座標
	* @return この座標から指定座標を見た時の、文字列として表現する方角
	*/
	public String bearing(Point2D pd) {
		StringBuilder compass = new StringBuilder();//方角
		double x, y;
		
		x = signumX(pd);
		y = signumY(pd);
		
		if(y == 1) { compass.append("s"); } else 
		if(y == -1) { compass.append("n"); } else 
		if(y == 0) { compass.append("0"); }
		
		if(x == 1) { compass.append("e"); } else 
		if(x == -1) { compass.append("w"); } else 
		if(x == 0) { compass.append("0"); }

		return compass.toString();
	}
	
	
	/**
	* 戻り値をaddするためのメソッド<br>
	* 	pd.add(directMove(i, 2));<br>
	* 第一引数により示す方向<br>
	* 	7 0 4<br>
	* 	3 * 1<br>
	* 	6 2 5<br>
	* 
	* @param num 方向を決める番号
	* @param scale ベクトルの大きさ
	* @return numより示す方向にscale分先の座標
	*/
	public static Point2D directMove(int num, double scale) {
		Point2D pd = Point2D.ZERO;
		double p = 1 * scale;
		double m = -1 * scale;
		if(num == 0) { pd = pd.add(0, m); } else //n↑
		if(num == 1) { pd = pd.add(p, 0); } else //e→
		if(num == 2) { pd = pd.add(0, p); } else //s↓
		if(num == 3) { pd = pd.add(m, 0); } else //w←
		if(num == 4) { pd = pd.add(p, m); } else //ne
		if(num == 5) { pd = pd.add(p, p); } else //es
		if(num == 6) { pd = pd.add(m, p); } else //sw
		if(num == 7) { pd = pd.add(m, m); }//wn
		return pd;
	}

	/**
	* 目的地が現在地の隣(＋１)の地点か？
	* @param pd 現在地
	* @param tgtPd 目的地
	* @return 隣同士なら真
	*/
	public static boolean isNext(Point2D pd, Point2D tgtPd) {
		boolean bool = false;
		for(int i = 0; i < 4; i++) {
			bool = tgtPd.equals( pd.add(directMove(i, 1)) );
			if(bool) { break; }
		}
		return bool;
	}

	/**
	* 指定座標の上下左右の＋１の座標配列を返す。指定座標は含まない
	* @param pd 指定座標
	* @return 隣の座標４つが入った配列
	*/
	public static Point2D[] nexts(Point2D pd) {
		Point2D[] karis = new Point2D[4];
		for(int i = 0; i < 4; i++) {
			karis[i] = pd.add(directMove(i, 1));
		}
		return karis;
	}

	/**
	* 指定座標の上下左右および四隅の＋１の座標配列を返す。指定座標は含まない
	* @param pd 指定座標
	* @return 隣の座標８つが入った配列
	*/
	public static Point2D[] eight(Point2D pd) {
		Point2D[] karis = new Point2D[8];
		for(int i = 0; i < 8; i++) {
			karis[i] = pd.add(directMove(i, 1));
		}
		return karis;
	}

	/**
	* ランダムで隣のプラマイ１のPoint2Dを返す
	* @return 適当な隣の座標
	*/
	public static Point2D randomNext() {
		int rndm = (int)(Math.random() * 4);
		return directMove(rndm, 1);
	}

	/**
	* randomNext()の連続して同じ値が出ない版<br>
	* 使用するクラスで、pd.add(nextFour())
	* @return 適当な隣の座標
	*/
	public Point2D nextFour() {
		return directMove(fourList(false), 1);
	}

		/**
		* fourListの中からランダムに選びその値を消すので、重複がない
		* このメソッドを５回呼ぶか、resetFourList()でfourListを元に戻す。
		* ５回目は-1確定
		* @param reset 真ならfourListを回復する
		* @return 0 〜　３のどれか
		*/
		public int fourList(boolean reset) {
			if(reset) {
				resetFourList();
			}
			if(fourList.size() == 0) {
				resetFourList();//回復はするけど
				return -1;//返すのはdirectMoveにない８としていた時代もあるので注意
			}
			
			//four.size() == 4 なら kari == 0~3
			Integer kari = (int)(Math.random() * fourList.size());
			Integer result = fourList.get(kari);
			fourList.remove(result);

			return result;
		}
		
	/**
	* fourListを返す
	* @return fourList
	*/
	public List<Integer> getFourList() { return fourList; }

	/** * fourListを回復 */
	public void resetFourList() {
		fourList.clear();
		fourList.addAll(yonList);
	}


//距離
	/**
	* マンハッタン距離。直線ではなく２辺の距離
	* @param pd ここから
	* @param tgtPd そこ
	* @return 距離
	*/
	public static double kakuKyori(Point2D pd, Point2D tgtPd) {
		double col = Math.abs(pd.getX() - tgtPd.getX());
		double row = Math.abs(pd.getY() - tgtPd.getY());
		return col + row;
	}

//	便利

	/** 
	* Point2Dのxのint値
	* @param pd Point2D
	* @return Point2Dのxのint値
	*/
	public static int intX(Point2D pd) {
		return (int)Math.round(pd.getX());
	}
	/** 
	* Point2Dのyのint値
	* @param pd Point2D
	* @return Point2Dのyのint値
	*/
	public static int intY(Point2D pd) {
		return (int)Math.round(pd.getY());
	}
	
	/** 
	* Point2Dのx値
	* @param pd Point2D
	* @return Point2Dのx値
	*/
	public static double x(Point2D pd) {
		return pd.getX();
	}
	/** 
	* Point2Dのy値
	* @param pd Point2D
	* @return Point2Dのy値
	*/
	public static double y(Point2D pd) {
		return pd.getY();
	}

	/** * 便利屋 */
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	/**
	* 「+" "+」がめんどいので
	* @param objs 可変長オブジェクト
	*/
	public void print(Object... objs) {
		qu.print(objs);
	}


		/**
		* 実験跡
		*/
		public static void fourListJikken() {
//			List<Integer> yonList = List.of(1,2,3,4);
//			List<Integer> fourList = new ArrayList<>();
//			fourList.addAll(yonList);
//			KARI.print(fourList);
			if(KARI.fourList.size() == 0) {
				KARI.fourList.addAll(KARI.yonList);
				KARI.print("Charge", KARI.fourList);
			}
			List<Integer> four = KARI.fourList;

			for(int i = 0; i < 4; i++) {
				Integer kari = (int)(Math.random() * four.size());
				four.remove(four.get(kari));
				KARI.print(kari, four);
			}
		}




/*	super.classのメソッド
	注意：addなどは（pd = pd.add(x,y)）で変更する。voidでないので。

	p2d add(double,double/p2d)//加算
	p2d subtract(double,double/p2d)//減算
	p2d midpoint(double,double/p2d)//中間点
	double getXY()
	bool equals(obj)//座標が同じでtrue
	double distance(double,double/p2d)//距離
	
*/
}