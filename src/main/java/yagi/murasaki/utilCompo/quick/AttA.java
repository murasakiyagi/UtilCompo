package yagi.murasaki.utilCompo.quick;

import java.io.*;
import java.util.*;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* Attのシンプルな具象クラス
*/
public class AttA extends Att {

	/** * 空のコンストラクタ */
	public AttA() {}

	/**
	* 初期値ありのコンストラクタ
	* @param own 持ち主
	*/
	public AttA(Object own) {
		super(own);
	}
	
	/**
	* 初期値ありのコンストラクタ
	* @param map 属性マップ
	*/
	public AttA(Map<String, Object> map) {
		super(map);
	}
	
	/**
	* 初期値ありのコンストラクタ
	* @param num 番号
	* @param map 属性マップ
	*/
	public AttA(Object own, Map<String, Object> map) {
		super(own, map);
	}
	
	
	/**
	* このAttと同じ内容で新しいインスタンスを返す
	* @return ディープコピー
	*/
	@Override
	public Att copy() {
		Map<String, Object> newMap = new HashMap<String, Object>(map);
		return new AttA(own, newMap);
	}

}