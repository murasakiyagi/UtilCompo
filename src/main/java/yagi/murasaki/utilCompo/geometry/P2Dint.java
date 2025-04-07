package yagi.murasaki.utilCompo.geometry;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

/**
* Point2Dの要素をintで表す
*/
public class P2Dint {

	private int x, y;
	private int hash = 0;

	/**
	* x == 0, y == 0のインスタンス
	*/
	public P2Dint() {
		this.x = 0;
		this.y = 0;
	}
	/**
	* 指定座標のインスタンス
	* @param x 横座標
	* @param y 縦座標
	*/
	public P2Dint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	* コピー
	* @return 同じ座標の新しいP2Dintオブジェクト
	*/
	public P2Dint copy() {
		return (new P2Dint(this.x, this.y));
	}

	/**
	* 座標を変更
	* @param x 横座標
	* @param y 縦座標
	*/
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	* 座標を変更
	* @param x 横座標
	*/
	public void setX(int x) { this.x = x; }
	/**
	* 座標を変更
	* @param y 縦座標
	*/
	public void setY(int y) { this.y = y; }
	/**
	* x座標を取得
	* @return x横座標
	*/
	public int x() { return x; }
	/**
	* y座標を取得
	* @return y縦座標
	*/
	public int y() { return y; }
	
	/**
	* この座標から追加分移動した、新しい座標を返す。この座標は残る
	* @param c 横座標に追加する値
	* @param r 縦座標に追加する値
	* @return この座標から追加分移動した、新しい座標
	*/
	public P2Dint add(int c, int r) {
		return new P2Dint( x + c, y + r );
	}
	
	/**
	* この座標から引数の座標値を追加して移動した、新しい座標を返す。この座標は残る
	* @param c この座標に追加する座標
	* @return この座標から追加分移動した、新しい座標
	*/
	public P2Dint add(P2Dint pi) {
		return new P2Dint( x + pi.x, y + pi.y );
	}
	
	/**
	* この座標から引数の座標値を追加して移動した、新しい座標を返す。この座標は残る
	* @param c この座標に追加する座標
	* @return この座標から追加分移動した、新しい座標
	*/
	public P2Dint remove(P2Dint pi) {
		return new P2Dint( x - pi.x, y - pi.y );
	}
	
	/**
	* Point2Dの値を少数切り捨て、この型にはめる
	* @param pd Point2D
	* @return 変換したP2Dintオブジェクト
	*/
	static public P2Dint cast(Point2D pd) {
		return (new P2Dint( (int)pd.getX(), (int)pd.getY() ));
	}
	
	/**
	* Point2Dの値を四捨五入して、この型にはめる
	* @param pd Point2D
	* @return 変換したP2Dintオブジェクト
	*/
	static public P2Dint castRound(Point2D pd) {
		int rndX = (int)Math.round( pd.getX() );
		int rndY = (int)Math.round( pd.getY() );
		return (new P2Dint( rndX, rndY ));
	}
	
	/**
	* Point2Dの値を切り上げして、この型にはめる
	* @param pd Point2D
	* @return 変換したP2Dintオブジェクト
	*/
	static public P2Dint castCeil(Point2D pd) {
		int ceilX = (int)Math.ceil( pd.getX() );
		int ceilY = (int)Math.ceil( pd.getY() );
		return (new P2Dint( ceilX, ceilY ));
	}
	
	/**
	* Point2Dリストの各値を四捨五入して、この型のリストにする
	* @param p2dList Point2Dのリスト
	* @param roundType 少数の丸め方。"ceil":切り上げ、"floor":切り捨て、その他:四捨五入
	* @return 変換したP2Dintリスト
	*/
	static public List<P2Dint> castList(List<Point2D> p2dList, String roundType) {
		List<P2Dint> list = new ArrayList<P2Dint>();
		for(Point2D pd : p2dList) {
			P2Dint kari;
			if(roundType.equals("ceil")) {
				kari = castCeil( pd );
			} else if(roundType.equals("floor")) {
				kari = cast( pd );
			} else {
				kari = castRound( pd );
			}
			list.add( kari );
		}
		return list;
	}
	
	
	/**
	* Point2Dに変換
	* @param pi P2Dint
	* @return Point2D
	*/
	static public Point2D recast(P2Dint pi) {
		return (new Point2D( pi.x(), pi.y() ));
	}
	
	/**
	* このクラスのリストをPoint2Dリストに変換
	* @param piList P2Dintリスト
	* @return Point2Dリスト
	*/
	static public List<Point2D> recast(List<P2Dint> piList) {
		List<Point2D> pdList = new ArrayList<Point2D>();
		for(P2Dint pi : piList) {
			pdList.add( recast(pi) );
		}
		return pdList;
	}
	
	
	/**
	* ハッシュコード。Point2Dのコードそのまま
	* @return ハッシュ値
	*/
	@Override
	public int hashCode() {
		if(hash == 0) {
			long bits = 7L;
			bits = 31L * bits + Double.doubleToLongBits(x());
			bits = 31L * bits + Double.doubleToLongBits(y());
			hash = (int) (bits ^ (bits >> 32));//＞＞シフト演算子
		}
		return hash;
	}
	
	/**
	* 座標値の比較。Point2Dのコードそのまま
	* @return 座標値x,yが同じであれば真。
	*/
	@Override
	public boolean equals(Object obj) {
		if(obj == this) { return true; }
		if(obj instanceof P2Dint) {
			P2Dint other = (P2Dint)obj;
			return x() == other.x() && y() == other.y();
		} else {
			return false;
		}
	}
	
	/**
	* 文字列表現
	* @return "P2Ding [x, y]"
	*/
	@Override
	public String toString() {
		return "P2Dint [x = " + x + ", y = " + y + "]";
	}

}