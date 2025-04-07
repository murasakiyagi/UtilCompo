package yagi.murasaki.utilCompo.geometry;

import java.io.*;
import java.util.*;
import javafx.geometry.Point2D;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* series:一連
* P2Dの一連のリストを処理する
*/
public class P2Dseries {

	private static P2Dcustom pdc = new P2Dcustom();
	private static final P2Dseries KARI = new P2Dseries();

	/**
	* 分母。移動速度
	*/
	private int deno;
	private List<Point2D> routeList;//受け取る
	
	public P2Dseries() {}
	public P2Dseries(List<Point2D> routeList, int deno) {
		this.routeList = routeList;
		this.deno = deno;
	}


	/**
	* このクラスの目的メソッド
	* リストの地点間を分割して新しいリストを返す。
	* denomi:分母denomination、nume:分子numerator
	* list.get(0) - list.get(1) == 2
		denomi == 2
		list.get(0) - list.get(1) == 1;
		list.get(1) - list.get(2) == 1;
	* 
	* @param pdList	pdList。分割元。
	* @param denomi	分母。１フレームで進む距離が変わる
	* @param herePd	現在地をリストにないなら入れる。
	* @return 分割されたリスト
	*/
	public static List<Point2D> division(List<Point2D> pdList, int denomi, Point2D herePd) {
		List<Point2D> kariList = new ArrayList<>();
		
		if(denomi <= 0) { denomi = 1; }//エラー回避
		
		//現在地をないなら入れる。このポイントは返されるリストに含まれない
		if( !pdList.contains(herePd) ) {
			//このメソッドの引数に使われるpdListの変化は、メソッド外でも保持される。なのでこのメソッドの後は、(pdList.size() > 0)である。
			pdList.add(0, herePd);
		}
		
		for(int i = 1; i < pdList.size(); i++) {
			littleAdd(kariList, pdList.get(i-1), pdList.get(i), denomi);
		}
		
		return kariList;
	}
	
		/**
		* リストの地点間を分割して新しいリストを返す。
		* 
		* @param pdList	pdList。分割元。
		* @param denomi	分母。１フレームで進む距離が変わる
		* @param c 現在地の横軸値
		* @param r 現在地を縦軸値
		* @return 分割されたリスト
		*/
		public static List<Point2D> division(List<Point2D> pdList, int denomi, int c, int r) {
			return division(pdList, denomi, new Point2D(c, r));
		}


		/**
		* １マスを分割したリストを得る（list.add）。fractの値を変えれば色々使える。
		* divitionで使われる場合、list.size()==0の状態で渡される。
		* 
		* @param herePd ここ
		* @param therePd あそこ
		*/
		public static void littleAdd(List<Point2D> list, Point2D herePd, Point2D therePd, int denomi) {
			pdc = P2Dcustom.cast(herePd);//キャスト
			
			for(double nume = 1; nume <= denomi; nume++) {
				double fract = nume / denomi;//fractの訳は「断片」だが、ここでの意味は1フレームで進む距離
				//pdc.sigAdd() : hereとthereを比較し、xyのそれぞれ[1,-1,0]いずれかのsignumを取得する。
				//				このsignum（方向）とfractという量のベクトルを返す。
				list.add( pdc.sigAdd(fract, fract, therePd) );
			}
		}

		/**
		* hereとthereは一マス違いという前提
		* 元になるリストがいらない。
		*/
		public static List<Point2D> inchStep(Point2D herePd, Point2D therePd, int denomi) {//１マスを分解
			pdc = P2Dcustom.cast(herePd);//キャスト
			List<Point2D> list = new ArrayList<Point2D>();
//			double distance = herePd.distance(therePd);
			for(double nume = 1; nume <= denomi; nume++) {
				double fract = nume / denomi;
				list.add( pdc.sigAdd(fract, fract, therePd) );//sigAdd(double x, double y, P2D pd)
			}
			return list;
		}

	/**
	* リストのP2Dが等間隔で並んでいるか。
	* 戻り値と別にターミナルに出力される。
	* @param list 調べるリスト
	* @return 隣の要素との距離。距離は直線でなく縦横の距離
	*/
	public static List<Double> evenDistance(List<Point2D> list) {
		List<Double> kariList = new ArrayList<>();
		if(list.size() >= 2) {
			double even = 0;
			for(int i = 1; i < list.size(); i++) {
				//最初の比較値
				double kyori = pdc.kakuKyori(list.get(i-1), list.get(i));
				kariList.add(kyori);
				if(even == 0) {
					even = kyori;
				} else {
					if(even == kyori) {
						KARI.print(true);
					} else {
						KARI.print(false);
					}
				}
			}
		}
		return kariList;
	}

		/**
		* evenDistanceのテストサンプル
		* @return evenDistanceに渡すテストサンプル
		*/
		private static List<Point2D> evenSample() {
			List<Point2D> kariList = List.of(
				new Point2D(0,0),
				new Point2D(0,1),
				new Point2D(2,1),
				new Point2D(2,2)
			);
			return kariList;
		}

//ceil切り上げ、floor切り捨て、round四捨五入
//ある地点から次の地点までの差は１である。
//	この１を分割して渡る時、どこまでが元の地点でどこからが次の地点に含まれるかを決定する
	/**
	* routeListDがrouteListをdivisionなどで分割したもので、Dの現在地はrouteListのどこかを決定する
	* motoの一歩に満たない場合は０。切り捨て（する場合に使用する）
	* 	moto.size(index最大値) == progCnt.Max / denomi
	* 	6 == 120 / 20
	* [moto.size()-1]について一応書く。２点間を渡る時の距離は１である。３点間を渡る時の距離は２である。
	* @param moto 分割していないリスト
	* @param progCnt 分割したリストの進捗値、現在値
	* @param denomi motoの１マスを分割する分母
	* @return 進捗値が所属する元のリストのPoint2D
	*/
	public static Point2D passFloor(List<Point2D> moto, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 1 : denomi;//0回避
		int max = (moto.size() - 1) * denomi;//リストをdenomiで分割した時の数量
		if(progCnt > max) {
			progCnt = max;
		}
		int p = progCnt / denomi;
		return moto.get(p);
	}

	/**
	* routeListDがrouteListをdivisionなどで分割したもので、Dの現在地はrouteListのどこかを決定する
	* 切り上げ
	*/
	public static Point2D passCeil(List<Point2D> moto, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 1 : denomi;
		int max = (moto.size() - 1) * denomi;
		if(progCnt > max) {
			progCnt = max;
		}
		int p = progCnt / denomi + 1;
		if(p >= moto.size() - 1) {
			p = moto.size() - 1;
		}
		return moto.get(p);
	}
	
	/**
	* routeListDがrouteListをdivisionなどで分割したもので、Dの現在地はrouteListのどこかを決定する
	* 四捨五入
	*/
	public static Point2D passRound(List<Point2D> moto, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 2 : denomi;//ここだけハーフがあるので２。
		int max = (moto.size() - 1) * denomi;
		if(progCnt > max) {
			progCnt = max;
		}
		int herf = denomi / 2;
		int p = progCnt / denomi - 1;
		int n = progCnt % denomi;
		p = p + n / herf;
		return moto.get(p);
	}

	/**
	* デバッグ用インフォメーション
	* 適宜変更する
	*/
	private void info(int r, int c, List<Point2D> moto, List<Point2D> saki) {
		print(r, c, moto.size(), moto.toString(), saki.size());
	}

	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

//		static private void littleAdd(List<Point2D> list, Point2D herePd, Point2D therePd, int denomi) {
//			for(double nume = 1; nume <= denomi; nume++) {
//				list.add( littleMove(herePd, therePd, nume, denomi) );
//			}
//		}
//		
//			static private Point2D littleMove(Point2D herePd, Point2D therePd, double nume, int denomi) {
//				//(double)(nume/denomi)にしてはいけない！絶対！ int同士の割り算で返されるのはintである！！
//				double fract = nume / denomi;//変数の訳は「断片」だが、ここでの意味は進む距離
//				pdc = P2Dcustom.cast(herePd);//キャスト
//				//sigAdd() : hereとthereの比較で+-の符号を得、fractionで量を与える。つまりベクトル
//				return pdc.sigAdd(fract, fract, therePd);//sigAdd(double x, double y, P2D pd)
//			}

}






