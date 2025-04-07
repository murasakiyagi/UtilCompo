package yagi.murasaki.utilCompo.information;

import java.io.*;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.input.PickResult;

/**
* インフォメーションを受け取る
* @author 作者：俺
* @version 1.00
*/
public interface InfoObserver {

	/**
	* イベントの選択物を受け取る
	* @param picre 選択イベントの結果。コンテナオブジェクト
	* @param node 選択されたノードでもいいし、違くてもいい。
	*/
	public void update(PickResult picre, Node node);

}