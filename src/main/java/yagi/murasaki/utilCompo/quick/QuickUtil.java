package yagi.murasaki.utilCompo.quick;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
* 手早くできればなあ
*/
public class QuickUtil {
	
	private Object that;
	private long cnt;
	static long heapCnt;
	
	public QuickUtil() {}
	/** * @param that 表示したいクラス名 */
	public QuickUtil(Object that) {
		this.that = that;
	}

	/**
	* 使用方法
	* 	import util.QuickUtil;
	* 	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	* 	protected void print(Object... objs) { qu.print(objs); }
	* @param objs 可変長Object
	*/
	public void print(Object... objs) {
		if(that == null) { System.out.print("インスタンス引数にthisがおすすめ。"); }
		
		System.out.print(cnt++ +" "+ print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print(" " + objs[i]);
		}
		System.out.println();
	}
	
	/**
	* 登録されたクラス名を表示
	* @return 「 !ClassName! 」
	*/
	public String print0() {
		if(that != null) {
			return " !" + that.getClass().getSimpleName() + "! ";
		} else {
			return " !" + getClass().getSimpleName() + "! ";
		}
	}
	
	/**
	* コレクションを順に表示
	* @param c コレクション
	*/
	public void printArr(Collection c) {
		for(Object obj : c) {
			print(obj);
		}
	}
	
	/**
	* static版可変長引数のprint
	* @param that このクラス名を行の先頭に表示する「!ClassName! obj obj ...」
	* @param objs 可変長Object
	*/
	static public void printS(Object that, Object... objs) {
		System.out.print(" !" + that.getClass().getSimpleName() + "! ");
		for(int i=0; i < objs.length; i++) {
			System.out.print(" " + objs[i]);
		}
		System.out.println();
	}


		/**
		* 引数で受け取った値が、期待する値の範囲内にない時調整する
		* @param num 受け取った値
		* @param min 納めたい最小値。以内
		* @param max 納めたい最大値。以内
		* @return min <= num <= max
		*/
		public int limitRegu(int num, int min, int max) {
			if(num < min) { return min; }
			if(num > max) { return max; }
			return num;
		}
	


}