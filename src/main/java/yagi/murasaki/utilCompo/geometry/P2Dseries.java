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

	/** * Point2Dカスタム */
	private static P2Dcustom pdc = new P2Dcustom();
	/** * staticメソッドを使えるように */
	private static final P2Dseries KARI = new P2Dseries();

	/** * 分母 denomi */
	private int deno;
	/** * 移動ルート */
	private List<Point2D> routeList;
	
	/** * からのコンストラクタ */
	public P2Dseries() {}
	/**
	* コンストラクタ
	* @param routeList 移動ルート
	* @param deno 分母。分割数
	*/
	public P2Dseries(List<Point2D> routeList, int deno) {
		this.routeList = routeList;
		this.deno = deno;
	}


	/**
	* このクラスの目的メソッド<br>
	* リストの地点間を分割して新しいリストを返す。<br>
	* denomi:分母denomination、nume:分子numerator<br>
	* list.get(0) - list.get(1) == 2<br>
		denomi == 2<br>
		list.get(0) - list.get(1) == 1;<br>
		list.get(1) - list.get(2) == 1;<br>
	* 
	* @param pdList	pdList。分割元のリスト。
	* @param denomi	分母。１フレームで進む距離が変わる
	* @param herePd	現在地をリストにないなら入れる。このポイントは返されるリストに含まれない
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
		* ここからあそこまでを分割してリストに格納する
		* @param list 入れ物
		* @param herePd ここ
		* @param therePd あそこ
		* @param denomi 分割数
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
		* ここからあそこまでを分割してリストにする
		* @param herePd ここ
		* @param therePd あそこ
		* @param denomi 分割数
		* @return list ここからあそこまでを分割したリスト
		*/
		public static List<Point2D> chopList(Point2D herePd, Point2D therePd, int denomi) {//１マスを分解
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
	* @return 隣の要素との距離を格納したリスト
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
		* evenDistanceのテストサンプル{(0,0),(0,1),(2,1),(2,2),(3,3)}
		* @return evenDistanceに渡すテストサンプル
		*/
		private static List<Point2D> evenSample() {
			List<Point2D> kariList = List.of(
				new Point2D(0,0),
				new Point2D(0,1),
				new Point2D(2,1),
				new Point2D(2,2),
				new Point2D(3,3)
			);
			return kariList;
		}

//ceil切り上げ、floor切り捨て、round四捨五入
// [moto.size()-1]について一応書く。２点間を渡る時の距離は１である。３点間を渡る時の距離は２である。

	/**
	* <p>motoListのある地点から次の地点までの差を１として、この１をdenomiで分割して渡る時、
		どこまでが元の地点でどこからが次の地点に含まれるかを決定する。<br>
		progCntはmotoListのどの地点に所属するのか決定して、その点を返す
		</p>
	* routeListDがrouteListをdivision()などで分割したもので、Dの現在地はrouteListのどこかを決定する<br>
	* motoListの一歩に満たない場合は０。切り捨てする場合に使用する<br>
	* 	motoListのindex最大値 == progCnt.Max / denomi<br>
	* 	6 == 120 / 20<br>
	* @param motoList 分割していないリスト
	* @param progCnt 分割したリストの進捗値、現在値。
	* @param denomi motoの１マスを分割する分母
	* @return 進捗値が所属する元のリストのPoint2D
	*/
	public static Point2D passFloor(List<Point2D> motoList, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 1 : denomi;//0回避
		int max = (motoList.size() -1) * denomi;//リストをdenomiで分割した時の数量。
		if(progCnt > max) {
			progCnt = max;
		}
		int p = progCnt / denomi;
		return motoList.get(p);
	}

	/**
	* <p>motoListのある地点から次の地点までの差を１として、この１をdenomiで分割して渡る時、
		どこまでが元の地点でどこからが次の地点に含まれるかを決定する。<br>
		progCntはmotoListのどの地点に所属するのか決定して、その点を返す
		</p>
	* routeListDがrouteListをdivision()などで分割したもので、Dの現在地はrouteListのどこかを決定する<br>
	* 切り上げ。motoListを超えれば１。<br>
	* 	motoListのindex最大値 == progCnt.Max / denomi<br>
	* 	6 == 120 / 20<br>
	* @param motoList 分割していないリスト
	* @param progCnt 分割したリストの進捗値、現在値。
	* @param denomi motoの１マスを分割する分母
	* @return 進捗値が所属する元のリストのPoint2D
	*/
	public static Point2D passCeil(List<Point2D> motoList, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 1 : denomi;
		int max = (motoList.size() - 1) * denomi;
		if(progCnt > max) {
			progCnt = max;
		}
		int p = progCnt / denomi + 1;
		if(p >= motoList.size() - 1) {
			p = motoList.size() - 1;
		}
		return motoList.get(p);
	}
	
	/**
	* <p>motoListのある地点から次の地点までの差を１として、この１をdenomiで分割して渡る時、
		どこまでが元の地点でどこからが次の地点に含まれるかを決定する。<br>
		progCntはmotoListのどの地点に所属するのか決定して、その点を返す
		</p>
	* routeListDがrouteListをdivision()などで分割したもので、Dの現在地はrouteListのどこかを決定する<br>
	* 四捨五入。<br>
	* 	motoListのindex最大値 == progCnt.Max / denomi<br>
	* 	6 == 120 / 20<br>
	* @param motoList 分割していないリスト
	* @param progCnt 分割したリストの進捗値、現在値。
	* @param denomi motoの１マスを分割する分母
	* @return 進捗値が所属する元のリストのPoint2D
	*/
	public static Point2D passRound(List<Point2D> motoList, int progCnt, int denomi) {
		denomi = (denomi == 0) ? 2 : denomi;//ここだけハーフがあるので２。
		int max = (motoList.size() - 1) * denomi;
		if(progCnt > max) {
			progCnt = max;
		}
		int herf = denomi / 2;
		int p = progCnt / denomi - 1;
		int n = progCnt % denomi;
		p = p + n / herf;
		return motoList.get(p);
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

}

