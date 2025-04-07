package yagi.murasaki.utilCompo.information;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.Node;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import yagi.murasaki.utilCompo.quick.QuickUtil;

/**
* イベントの選択結果。
* PickResultの分解作業クラス。
* @author 作者：俺
* @version 1.00
*/
//イベントから情報を得る。Hander.handle(Event){info}
public class Information implements InfoSubject {

	/** * オブザーバーのリスト */
	private List<InfoObserver> obsList;

	/** * 選択結果 */
	private PickResult picre;
	/** * PickResultに含まれるNode */
	private Node node;
	/** * PickResultに含まれるPoint3DからZ軸を抜いたPoint2D */
	private Point2D pd;
	/** * PickResultに含まれるPoint3D */
	private Point3D pd3;
	/** * マウスイベントごとのpicreのプリントをするか */
	private boolean infoBool;

	/** * コンストラクタ */
	public Information() {
		obsList = new ArrayList<InfoObserver>();
	}

	/**
	* マウス・ピックリザルトを保持
	* @param me マウスイベント
	*/
	public void mouseSelect(MouseEvent me) {
		picre = me.getPickResult();
		if(infoBool) {
			print(picre);
		}
		extra();
		
		notifyObservers();
	}

	/** * picreからより詳細な情報を絞り出す。というより分解 */
	private void extra() {
		node = picre.getIntersectedNode();
		pd3 = picre.getIntersectedPoint();
		pd = new Point2D(pd3.getX(), pd3.getY());
	}

	/**
	* マウスイベントごとのpicreのプリントをするか。
	* @param bool するならtrue
	*/
	public void isInfo(boolean bool) {
		infoBool = bool;
	}

	/**
	* オブザーバー登録
	* @param obs 通知を受けたいクラス
	*/
	@Override
	public void registerObserver(InfoObserver obs) {
		obsList.add(obs);
	};
	
	/**
	* オブザーバー脱退
	* @param obs 脱退したいクラス
	*/
	@Override
	public void removeObserver(InfoObserver obs) {
		obsList.remove(obs);
	};
	
	/** * オブザーバーに通知する */
	@Override
	public void notifyObservers() {
		for(InfoObserver obs : obsList) {
			obs.update(picre, node);
		}
	};

	
	/**
	* ピックリザルトのノードを返す
	* @return ノード
	*/
	public Node getNode() { return node; }
	
	/**
	* ピックリザルトのノードの型
	* @return 型の文字列表現
	*/
	public String getNodeType() {
		return node.getTypeSelector();
	}
	
	/**
	* 選択座標
	* @return ２次元座標
	*/
	public Point2D getPd() { return pd; }
	
	/**
	* 選択座標
	* @return ３次元座標
	*/
	public Point3D getPd3() { return pd3; }

	/** * マスゲームの舞台 */
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	/** * 「 +" "+ 」いらず * @param objs 可変長Object */
	protected void print(Object... objs) { qu.print(objs); }

}