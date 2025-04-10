package yagi.murasaki.utilCompo.geometry;

import java.io.*;
import java.util.*;
import java.awt.Point;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* Java SE Pointの至らないところを補完する
* FX Point2Dは使わない
*/
public class PointCustom extends Point {

	/** * staticメソッドを使えるように */
	private static PointCustom KARI = new PointCustom(0,0);//static用
	/** * ０〜３ */
	private List<Integer> yonList;
	/** * yonListコピー */
	private List<Integer> fourList;

	/** * 0,0座標 */
	public PointCustom() {
		super(0, 0);
		init();
	}
	/**
	* 指定座標
	* @param x 横座標
	* @param y 縦座標
	*/
	public PointCustom(int x, int y) {
		super(x, y);
		init();
	}
	/**
	* 他の座標参照
	* @param pt 他の座標
	*/
	public PointCustom(Point pt) {
		super((int)pt.getX(), (int)pt.getY());
		init();
	}
	
	/** * 初期化 */
	private void init() {
		yonList = List.of(0, 1, 2, 3);//nesw用に作ったので、0~3
		fourList = new ArrayList<>();
		fourList.addAll(yonList);
//		this.print(fourList);
	}
	
	
	/**
	* 新しい(0,0)のこのクラスオブジェクト
	* @return 新しいP2Dcustom
	*/
	public PointCustom zero() {
		return new PointCustom(0, 0);
	}

	/**
	* このPointCustom座標にxとy分移動した新しいPointCustomを返す
	* @param x 追加するx座標
	* @param y 追加するx座標
	* @return 新しいPointCustom
	*/
	public PointCustom add(int x, int y) {
		return new PointCustom(this.x + x, this.y + y);
	}

		/**
		* この座標にxとy分移動した新しいPointCustomを返す
		* @param pt 追加する座標を持つPoint
		* @return 新しいPointCustom
		*/
		public PointCustom add(Point pt) {
			return this.add((int)pt.getX(), (int)pt.getY());
		}

		/**
		* Point p1にxとy分移動した新しいPointを返す
		* @param pt1 元のPoint
		* @param pt2 追加する座標を持つPoint
		* @return 新しいPoint
		*/
		static public Point add(Point pt1, Point pt2) {
			int x = (int)(pt1.getX() + pt2.getX());
			int y = (int)(pt1.getY() + pt2.getY());
			return new Point(x, y);
		}

		/**
		* 指定座標にxとy分移動した新しいPointを返す
		* @param pt 元のPoint
		* @param x 追加するx座標
		* @param y 追加するx座標
		* @return 新しいPoint
		*/
		static public Point add(Point pt, int x, int y) {
			int xx = (int)pt.getX() + x;
			int yy = (int)pt.getY() + y;
			return new Point(xx, yy);
		}

//方向
	/**
	* ここから進むべき方向の計算と加算を同時に
	* tgtPdがここより右上にある場合、signumXYは+1,-1
	* 	this.add(x * 1, y * -1)
	* @param x 進む距離。大きさ
	* @param y 進む距離。大きさ
	* @param pt 方向を特定するための目的座標
	* @return 移動ぶんを加えた新しい座標
	*/
	public Point sigAdd(int x, int y, Point pt) {
		int addX = x * signumX(pt);//xyは進む距離
		int addY = y * signumY(pt);//signumは方向
		return this.add(addX, addY);//addの戻り値は(new)Point。thisの値は変わらない
	}
	
	/**
	* この座標に対する指定座標からXY軸のプラマイの符号を得て、その値からPointを作る<br>
		指定座標 - この座標<br>
		例、pt:(1, 4) - this:(2, 2)<br>
		return (-1, 1);<br>

	* @param pt 目標座標
	* @return  XYが[-1, 0, 1]のどれかで作られたPoint
	*/
	public Point signumPt(Point pt) {
		int x, y;
		//signumXY()の中にthisが使われていることを忘れない
		x = signumX(pt);
		y = signumY(pt);
		return (new Point(x, y));
	}
	
	/**
	* この座標に対する指定座標からX軸のプラマイの符号を得る<br>
		指定座標 - この座標
	* @param pt 目標座標
	* @return  [-1, 0, 1]のどれか
	*/
	public int signumX(Point pt) {
		return (int)Math.signum(pt.getX() - this.getX());
	}

	/**
	* この座標に対する指定座標からY軸のプラマイの符号を得る<br>
		指定座標 - この座標
	* @param pt 目標座標
	* @return  [-1, 0, 1]のどれか
	*/
	public int signumY(Point pt) {
		return (int)Math.signum(pt.getY() - this.getY());
	}
	
	/**
	* この座標から指定の座標の方向を文字列として表す<br>
		nw n0 ne<br>
		0w 00 0e<br>
		sw s0 se<br>
	* @param pt 目標座標
	* @return この座標から指定座標を見た時の、文字列として表現する方角
	*/
	public String bearing(Point pt) {
		String compass = "";//方角
		double x, y;
		
		x = signumX(pt);
		y = signumY(pt);
		
		if(y == 1) { compass += "s"; }
		if(y == -1) { compass += "n"; }
		if(y == 0) { compass += "0"; }
		
		if(x == 1) { compass += "e"; }
		if(x == -1) { compass += "w"; }
		if(x == 0) { compass += "0"; }

		return compass;
	}
	
	
	/**
	* 戻り値をaddするためのメソッド<br>
	* 	pt.add(directMove(i, 2));<br>
	* 第一引数により示す方向<br>
	* 	7 0 4<br>
	* 	3 * 1<br>
	* 	6 2 5<br>
	*
	* @param num 方向を決める番号
	* @param scale ベクトルの大きさ
	* @return numより示す方向にscale分先の座標
	*/
	static public Point directMove(int num, int scale) {
		PointCustom pt = new PointCustom(0, 0);
		int p = 1 * scale;
		int m = -1 * scale;
		if(num == 0) { pt = pt.add(0, m); } else //n↑
		if(num == 1) { pt = pt.add(p, 0); } else //e→
		if(num == 2) { pt = pt.add(0, p); } else //s↓
		if(num == 3) { pt = pt.add(m, 0); } else //w←
		if(num == 4) { pt = pt.add(p, m); } else //ne
		if(num == 5) { pt = pt.add(p, p); } else //es
		if(num == 6) { pt = pt.add(m, p); } else //sw
		if(num == 7) { pt = pt.add(m, m); }//wn
		return pt;
	}

	/**
	* 目的地が現在地の隣(＋１)の地点か？
	* @param pt 現在地
	* @param tgtPt 目的地
	* @return 隣同士なら真
	*/
	static public boolean isNext(Point pt, Point tgtPt) {
		boolean bool = false;
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 4; i++) {
			bool = tgtPt.equals( casted.add(directMove(i, 1)) );
			if(bool) { break; }
		}
		return bool;
	}

	/**
	* 指定座標の上下左右の＋１の座標配列を返す。指定座標は含まない
	* @param pt 指定座標
	* @return 隣の座標４つが入った配列
	*/
	static public Point[] nexts(Point pt) {
		Point[] karis = new Point[4];
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 4; i++) {
			karis[i] = casted.add(directMove(i, 1));
		}
		return karis;
	}

	/**
	* 上下左右斜めの＋１の座標配列を返す
	* @param pt 指定座標
	* @return 隣の座標８つが入った配列
	*/
	static public Point[] eight(Point pt) {
		Point[] karis = new Point[8];
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 8; i++) {
			karis[i] = casted.add(directMove(i, 1));
		}
		return karis;
	}

	/**
	* ランダムで隣のプラマイ１のPointを返す
	* @return 適当な隣の座標
	*/
	static public Point rndmNext() {
		int rndm = (int)(Math.random() * 4);
		return directMove(rndm, 1);
	}

	/**
	* randomNext()の連続して同じ値が出ない版<br>
	* 使用するクラスで、pt.add(nextFour())
	* @return 適当な隣の座標
	*/
	public Point nextFour() {
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
				fourList.addAll(yonList);//回復はするけど
				return -1;//返すのはdirectMoveにない８としていた時代もある
			}
			
			//four.size() == 4 なら kari == 0~3
			Integer kari = (int)(Math.random() * fourList.size());
			Integer result = fourList.get(kari);
			fourList.remove(result);
			//戻り値はyonListに準ずるので要確認
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
	* @param pt ここから
	* @param tgtPt そこ
	* @return 距離
	*/
	static public int kakuKyori(Point pt, Point tgtPt) {
		double col = Math.abs(pt.getX() - tgtPt.getX());
		double row = Math.abs(pt.getY() - tgtPt.getY());
		return (int)(col + row);
	}


//	便利
	/** 
	* Pointのxのint値
	* @param pt Point
	* @return Pointのxのint値
	*/
	static public int x(Point pt) {
		return (int)pt.getX();
	}
	/** 
	* Point2Dのyのint値
	* @param pt Point
	* @return Pointのyのint値
	*/
	static public int y(Point pt) {
		return (int)pt.getY();
	}


//キャスト

	/**
	* Pointの座標を元に、新しいPointCustomを返す
	* @param pt Pointオブジェクト
	* @return PointCustomオブジェクト
	*/
	static public PointCustom cast(Point pt) {
		return new PointCustom(pt);
	}



	/** * 便利機能 */
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	/**
	* 「+" "+」でつなげるのめんどいから
	* @param objs 可変長Object
	*/
	public void print(Object... objs) {
		qu.print(objs);
	}


		/** * fourList実験 */
		static public void fourListJikken() {
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
	注意：addなどは（pt = pt.add(x,y)）で変更する。voidでないので。

	p2d add(double,double/p2d)//加算
	p2d subtract(double,double/p2d)//減算
	p2d midpoint(double,double/p2d)//中間点
	double getXY()
	bool equals(obj)//座標が同じでtrue
	double distance(double,double/p2d)//距離
	
*/
}