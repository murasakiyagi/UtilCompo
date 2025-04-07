package yagi.murasaki.utilCompo.information;

import java.io.*;
import java.util.*;

/**
* インフォメーションを発する
* @author 作者：俺
* @version 1.00
*/
public interface InfoSubject {

	/**
	* 受け取るクラスの追加
	* @param obs 登録するオブザーバー
	*/
	public void registerObserver(InfoObserver obs);
	
	/**
	* 受け取るクラスの削除
	* @param obs 登録中のオブザーバー
	*/
	public void removeObserver(InfoObserver obs);
	
	/**
	* オブザーバーに通知
	*/
	public void notifyObservers();

}