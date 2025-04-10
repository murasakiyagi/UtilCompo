package yagi.murasaki.utilCompo.quick;

import java.io.*;
import java.util.*;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* 属性Attribute
*/
public abstract class Att {

	/** * この属性の持ち主 */
	protected Object own = null;
	/** * 属性マップ */
	protected Map<String, Object> map = new HashMap<>();


//===============
	/** * 空のコンストラクタ */
	public Att() {}

	/**
	* 初期値ありのコンストラクタ
	* @param own 持ち主
	*/
	public Att(Object own) {
		this.own = own;
	}

	/**
	* 初期値ありのコンストラクタ
	* @param map 属性マップ
	*/
	public Att(Map<String, Object> map) {
		this.map = map;
	}

	/**
	* 初期値ありのコンストラクタ
	* @param own 持ち主
	* @param map 属性マップ
	*/
	public Att(Object own, Map<String, Object> map) {
		this.own = own;
		this.map = map;
	}

//==================

	/**
	* 持ち主入力
	* @param own このObjctの持ち主
	*/
	public void setOwn(Object own) { this.own = own; }

	/**
	* 持ち主ゲット
	* @return 持ち主
	*/
	public Object getOwn() { return own; }

	/**
	* 名前をつけて格納する
	* @param key マップに入れるキー
	* @param value マップに入れるオブジェクト
	*/
	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	/**
	* マップから値を返す。戻り値をキャストして使う
	* @param key マップのキー
	* @return キーのバリュー
	*/
	public Object get(String key) {
		return map.get(key);
	}
		
		/**
		* マップの値をint値として返す。元の型がIntegerでなければ０を返す
		* @param key キー
		* @return int値としてのバリュー
		*/
		public int intValue(String key) {
			if(map.get(key) instanceof Integer) {
				return (int)map.get(key);
			} else {
				return 0;
			}
		}
		
		/**
		* マップの値をdouble値として返す。元の型がDoubleでなければ０を返す
		* @param key キー
		* @return double値としてのバリュー
		*/
		public double doubleValue(String key) {
			if(map.get(key) instanceof Double) {
				return (double)map.get(key);
			} else {
				return 0;
			}
		}
		
	/**
	* 属性マップの要素を消す
	* @param key キー
	*/
	public void remove(String key) {
		map.remove(key);
	}
	
	/**
	* 属性マップをゲット
	* @return 属性マップ
	*/
	public Map<String, Object> map() {
		return map;
	}

	/**
	* 持っているデータをプリント
	*/
	public void report() {
		print("\n------ ATT REPORT ------");
		print("OWN : ", own);
		print("MAP---");
		for(String s : map.keySet()) {
			print("  ", s, ": ", map.get(s));
		}
		print("\n--- ATT REPORT  END ---\n");
	}

	/**
	* このAttと同じ内容で新しいインスタンスを返す
	* @return このAttと同じ内容の新しいインスタンス
	*/
	abstract public Att copy();
	
	/** * 便利なはず。サブクラスも大丈夫 */
	protected QuickUtil qu = new QuickUtil(this);
	/**
	* 「+" "+」でつなげるのめんどいから
	* @param objs 可変長Object
	*/
	public void print(Object... objs) {
		qu.print(objs);
	}
	

}