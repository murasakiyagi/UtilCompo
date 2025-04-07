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

	private static PointCustom KARI = new PointCustom(0,0);//static用
	private List<Integer> yonList;
	private List<Integer> fourList;

	public PointCustom() {
		super(0, 0);
		init();
	}
	public PointCustom(int x, int y) {
		super(x, y);
		init();
	}
	public PointCustom(Point pt) {
		super((int)pt.getX(), (int)pt.getY());
		init();
	}
	
	private void init() {
		yonList = List.of(0, 1, 2, 3);//nesw用に作ったので、0~3
		fourList = new ArrayList<>();
		fourList.addAll(yonList);
//		this.print(fourList);
	}
	
	
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
		* このPointCustom座標にxとy分移動した新しいPointCustomを返す
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
		* このPoint ptにxとy分移動した新しいPointを返す
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
	* 進むべき方向の計算と加算を同時に
	* 
	* @param x 進む距離。大きさ
	* @param y 進む距離。大きさ
	* @param pt 目的座標
	*/
	public Point sigAdd(int x, int y, Point pt) {
		int addX = x * signumX(pt);//xyは進む距離
		int addY = y * signumY(pt);//signumは方向
		return this.add(addX, addY);//addの戻り値は(new)Point。thisの値は変わらない
	}
	
	/**
	* xyは最終的に{-1,0,1}のどれか。
	* thisとptの比較
	* @param pt 目的座標
	*/
	public Point signumPos(Point pt) {
		int x, y;
		//signumXY()の中にthisが使われていることを忘れない
		x = signumX(pt);
		y = signumY(pt);
		return (new Point(x, y));
	}
	
	/**
	* X軸の符号を得る
	* 引数ptとthisの比較
	*/
	public int signumX(Point pt) {
		return (int)Math.signum(pt.getX() - this.getX());
	}

	/**
	* Y軸の符号を得る
	* 引数ptとthisの比較
	*/
	public int signumY(Point pt) {
		return (int)Math.signum(pt.getY() - this.getY());
	}
	
	/**
	* 方向を文字列として表す
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
	* 戻り値をaddするためのメソッド
	* 	pt.add(neswNum(i, 2));
	* 第一引数により示す方向
	* 	7 0 4
	* 	3 * 1
	* 	6 2 5
	* 
	* ＊メソッド名のNumは、戻り値がnumではなく引数
	*
	* @param num 方向を決める番号
	* @param scale ベクトルの大きさ
	*/
	static public Point neswNum(int num, int scale) {
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

//	static public int neswPd(Point pt, Point tgtPd) {
//		double col = Math.abs(tgtPd.getX() - pt.getX());
//		double row = Math.abs(tgtPd.getY() - pt.getY());
//		int csig = (int)Math.signum(col);
//		int rsig = (int)Math.signum(row);
//		if(csig == 0 && rsig == -1) { return 0; }
//		if(csig == 1 && rsig == 0) { return 1; }
//		if(csig == 0 && rsig == 1) { return 2; }
//		if(csig == -1 && rsig == 0) { return 3; }
//		if(csig == 1 && rsig == -1) { return 4; }
//		if(csig == 1 && rsig == 1) { return 5; }
//		if(csig == -1 && rsig == 1) { return 6; }
//		if(csig == -1 && rsig == -1) { return 7; }
//		return -1;
//	}


	/**
	* 目的地が現在地の隣(＋１)の地点か？
	* 
	* @param pt 現在地
	* @param tgtPd 目的地
	*/
	static public boolean isNext(Point pt, Point tgtPd) {
		boolean bool = false;
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 4; i++) {
			bool = tgtPd.equals( casted.add(neswNum(i, 1)) );
			if(bool) { break; }
		}
		return bool;
	}

	/**
	* 上下左右の＋１の座標配列を返す
	*/
	static public Point[] nexts(Point pt) {
		Point[] karis = new Point[4];
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 4; i++) {
			karis[i] = casted.add(neswNum(i, 1));
		}
		return karis;
	}

	static public Point[] eight(Point pt) {
		Point[] karis = new Point[8];
		PointCustom casted = PointCustom.cast(pt);
		for(int i = 0; i < 8; i++) {
			karis[i] = casted.add(neswNum(i, 1));
		}
		return karis;
	}

	/**
	* ランダムで隣のプラマイ１のPointを返す
	*/
	static public Point rndmNext() {
		int rndm = (int)(Math.random() * 4);
		return neswNum(rndm, 1);
	}

	/**
	* 推奨。rndmNext()よりこっち。連続して同じ値が出ない
	* 使用するクラスで、pt.add(neswFour())
	*/
	public Point neswFour() {
		return neswNum(fourList(), 1);
	}

		/**
		* 推奨。fourListをstaticで共有するべきでないので
		* fourListの中からランダムに選びその値を消すので、重複がない
		* このメソッドを５回呼ぶか、resetFourList()でfourListを元に戻す。
		* ５回目は-1確定
		*/
		public int fourList() {
			if(fourList.size() == 0) {
				fourList.addAll(yonList);//回復はするけど
				return -1;//返すのはneswNumにない８としていた時代もある
			}
			
			//four.size() == 4 なら kari == 0~3
			Integer kari = (int)(Math.random() * fourList.size());
			Integer result = fourList.get(kari);
			fourList.remove(result);
			//戻り値はyonListに準ずるので要確認
			return result;
		}
		
		
	public List<Integer> getFourList() { return fourList; }
	public void resetFourList() {
		fourList.clear();
		fourList.addAll(yonList);
	}


//距離
	/**
	* 角距離。直線ではなく２辺の距離
	*/
	static public int kakuKyori(Point pt, Point tgtPt) {
		double col = Math.abs(pt.getX() - tgtPt.getX());
		double row = Math.abs(pt.getY() - tgtPt.getY());
		return (int)(col + row);
	}

	/**
	* ２点間の直角距離。壁無視
	* dis : ピタゴラス。直線距離
	* pow : a^2 + b^2 = c^2 = dis
	* sqrt : (a==1)a^2=1, c^2 - a^2 = b^2 
	* 		c = sqrt(a^2 + b^2)
	*/
	static public double kyori(Point pt, Point tgtPt) {
		double dis = pt.distance(tgtPt);
		double pow = Math.pow(dis, 2);
		double sqrt = Math.sqrt(pow / 2) * 2;
		return Math.round(sqrt);
	}

		static public int kyoriInt(Point pt, Point tgtPt) {
			return (int)kyori(pt, tgtPt);
		}


//	便利
	static public int x(Point pt) {
		return (int)pt.getX();
	}
	static public int y(Point pt) {
		return (int)pt.getY();
	}


//キャスト

	/**
	* Pointの座標を元に、新しいPointCustomを返す
	* @param pt Pointオブジェクト
	*/
	static public PointCustom cast(Point pt) {
		return new PointCustom(pt);
	}



	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}



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