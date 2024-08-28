//cbの感知クラス
package utilCompo.geometry;

import java.io.*;
import java.util.*;
import javafx.geometry.Point2D;

import utilCompo.quick.QuickUtil;


public class P2Dcustom extends Point2D {

	private static P2Dcustom STICYOU = new P2Dcustom(0,0);//static用
	private List<Integer> yonList;
	private List<Integer> fourList;

	public P2Dcustom() {
		super(0,0);
		init();
	}
	public P2Dcustom(double x, double y) {
		super(x,y);
		init();
	}
	public P2Dcustom(Point2D pd) {
		super(pd.getX(), pd.getY());
		init();
	}
	
	private void init() {
		yonList = List.of(0,1,2,3);//nesw用に作ったので、0~3
		fourList = new ArrayList<>();
		fourList.addAll(yonList);
//		this.print(fourList);
	}
	
	
	/**
	* Point2DはStringと同様、イミュータブルの様で(セッターが無い)、
	* 	データ隠蔽がされている様で、、、newを使わざるを得ない。
	*/
	public static P2Dcustom cast(Point2D pd) {
		return new P2Dcustom(pd);
	}
	
	/**
	* 進むべき方向の計算と加算を同時に
	* 
	* @param x 進む距離。大きさ
	* @param y 進む距離。大きさ
	* @param pd 目的座標
	*/
	public Point2D sigAdd(double x, double y, Point2D pd) {
		double addX = x * signumX(pd);//xyは進む距離
		double addY = y * signumY(pd);//signumは方角
		return this.add(addX, addY);//addの戻り値は(new)Point2D。thisの値は変わらない
	}
	
	/**
	* xyは最終的に{-1,0,1}のどれか
	*/
	public Point2D signumPd(Point2D pd) {
		double x, y;
		x = signumX(pd);
		y = signumY(pd);
		return (new Point2D(x, y));
	}
	
	public double signumX(Point2D pd) {//X軸の符号を得る
		return Math.signum(pd.getX() - this.getX());
	}
	
	public double signumY(Point2D pd) {//Y軸の符号を得る
		return Math.signum(pd.getY() - this.getY());
	}
	
	/**
	* 方向を文字列として表す
	*/
	public String bearing(Point2D pd) {
		String compass = "";//方角
		double x, y;
		
		x = signumX(pd);
		y = signumY(pd);
		
		if(y == 1) { compass += "s"; }
		if(y == -1) { compass += "n"; }
		if(y == 0) { compass += "0"; }
		
		if(x == 1) { compass += "e"; }
		if(x == -1) { compass += "w"; }
		if(x == 0) { compass += "0"; }

		return compass;
	}
	
	
	
	//@Overrideは戻り値が同じであること
	public void addC(double x, double y) {
		this.add(x,y);
	}
	
	
		/**
		* 戻り値をaddする
		* 	pd.add(neswNum(i, 2));
		* 第一引数により示す方向
		* 	7 0 4
		* 	3 * 1
		* 	6 2 5
		* 
		* @param num 方向を決める番号
		* @param scale ベクトルの大きさ
		*/
		public static Point2D neswNum(int num, int scale) {
			Point2D pd = Point2D.ZERO;
			int p = 1 * scale;
			int m = -1 * scale;
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
	* 
	* @param pd 現在地
	* @param tgtPd 目的地
	*/
	public static boolean isNext(Point2D pd, Point2D tgtPd) {
		boolean bool = false;
		for(int i = 0; i < 4; i++) {
			bool = tgtPd.equals( pd.add(neswNum(i, 1)) );
			if(bool) { break; }
		}
		return bool;
	}

	/**
	* 上下左右の＋１の座標配列を返す
	*/
	public static Point2D[] nexts(Point2D pd) {
		Point2D[] karis = new Point2D[4];
		for(int i = 0; i < 4; i++) {
			karis[i] = pd.add(neswNum(i, 1));
		}
		return karis;
	}

	/**
	* ランダムで隣のプラマイ１のPoint2Dを返す
	*/
	public static Point2D rndmNext() {
		int rndm = (int)(Math.random() * 4);
		return neswNum(rndm, 1);
	}

	/**
	* 非推奨
	*/
	public static Point2D rndmNextFour() {
		return neswNum(fourListStatic(), 1);
	}

		/**
		* 推奨。fourListを共有するべきでないので
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
		
		/**
		* 推奨。rndmNext()よりこっち
		* 使用するクラスで、pd.add(neswFour())
		*/
		public Point2D neswFour() {
			return neswNum(fourList(), 1);
		}
		
		
		/**
		* 非推奨
		*/
		public static int fourListStatic() {
			if(STICYOU.fourList.size() == 0) {
				STICYOU.fourList.addAll(STICYOU.yonList);
			}
			List<Integer> four = STICYOU.fourList;
			
			//four.size() == 4 なら kari == 0~3
			Integer kari = (int)(Math.random() * four.size());
			int result = four.get(kari);
			four.remove(result);
//			STICYOU.print(kari, four);
			
			return result;
		}

			public List<Integer> getFourList() { return fourList; }
			public void resetFourList() { fourList.addAll(yonList); }


		public static void fourListJikken() {
//			List<Integer> yonList = List.of(1,2,3,4);
//			List<Integer> fourList = new ArrayList<>();
//			fourList.addAll(yonList);
//			STICYOU.print(fourList);
			if(STICYOU.fourList.size() == 0) {
				STICYOU.fourList.addAll(STICYOU.yonList);
				STICYOU.print("Charge", STICYOU.fourList);
			}
			List<Integer> four = STICYOU.fourList;

			for(int i = 0; i < 4; i++) {
				Integer kari = (int)(Math.random() * four.size());
				four.remove(four.get(kari));
				STICYOU.print(kari, four);
			}
			
		}

//		public static void fourListClear() {
//			fourList.clear();
//		}

	/**
	* 角距離。直線ではなく２辺の距離
	*/
	public static double kakuKyori(Point2D pd, Point2D tgtPd) {
		double row = Math.abs(pd.getX() - tgtPd.getX());
		double col = Math.abs(pd.getY() - tgtPd.getY());
		return row + col;
	}

	/**
	* ２点間の直角距離。壁無視
	* dis : ピタゴラス。直線距離
	* pow : a^2 + b^2 = c^2 = dis
	* sqrt : (a==1)a^2=1, c^2 - a^2 = b^2 
	* 		c = sqrt(a^2 + b^2)
	*/
	public static double kyori(Point2D pd, Point2D tgtPd) {
		double dis = pd.distance(tgtPd);
		double pow = Math.pow(dis, 2);
		double sqrt = Math.sqrt(pow / 2) * 2;
		return Math.round(sqrt);
	}

		public static int kyoriInt(Point2D pd, Point2D tgtPd) {
			return (int)kyori(pd, tgtPd);
		}
	
	
		/**
		* あくまで実験なので
		*/
		private void jikken(int row, int col) {
			Point2D pda = Point2D.ZERO;
			Point2D pdb = new Point2D(row, col);//row + col = sqrt+1になって欲しい
			double dis = pda.distance(pdb);//ピタゴラス
			double pow = Math.pow(dis, 2);//a^2 + b^2 = c^2 = dis^2
			double sqrt = Math.sqrt(pow / 2) * 2;
			if(row == 0 || col == 0) {
				sqrt = row + col;
			}
			print("JIKKEN ", row, col, ":", dis, pow, sqrt);
		}

//	便利
	public static int intX(Point2D pd) {
		return (int)Math.round(pd.getX());
	}
	public static int intY(Point2D pd) {
		return (int)Math.round(pd.getY());
	}
	
	public static double x(Point2D pd) {
		return pd.getX();
	}
	public static double y(Point2D pd) {
		return pd.getY();
	}

	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
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