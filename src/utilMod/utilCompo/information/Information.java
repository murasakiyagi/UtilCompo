package utilCompo.information;

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

import utilCompo.quick.QuickUtil;

//イベントから情報を得る。Hander.handle(Event){info}
public class Information implements InfoSubject {

	private List<InfoObserver> obsList;

	PickResult picre;
	Node node;
	Point2D pd;
	Point3D pd3;

	public Information() {
		obsList = new ArrayList<InfoObserver>();
	}

	/**
	* マウスイベントで使う
	*/
	public void mouseSelect(MouseEvent me) {
		picre = me.getPickResult();
			print(picre);
			extra();
		
		notifyObservers();
	}

	/**
	* picreからより詳細な情報を絞り出す
	*/
	private void extra() {
		node = picre.getIntersectedNode();
		pd3 = picre.getIntersectedPoint();
		pd = new Point2D(pd3.getX(), pd3.getY());
	}

	@Override
	public void registerObserver(InfoObserver obs) {
		obsList.add(obs);
	};
	
	@Override
	public void removeObserver(InfoObserver obs) {
		obsList.remove(obs);
	};
	
	@Override
	public void notifyObservers() {
		for(InfoObserver obs : obsList) {
			obs.update(picre, node);
		}
	};

	

	public Node getNode() { return node; }
	public String getNodeType() {
		return node.getTypeSelector();
	}
	public Point2D getPd() { return pd; }
	public Point3D getPd3() { return pd3; }

	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) { qu.print(objs); }

}