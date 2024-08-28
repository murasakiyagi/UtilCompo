package utilCompo.quick;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import javafx.geometry.Point2D;

public class QuickUtil {
	
	private Object that;
	private long cnt;
	static long heapCnt;
	
	public QuickUtil() {}
	public QuickUtil(Object that) {
		this.that = that;
	}
	
	public int xI(Point2D pd) { return (int)pd.getX(); }
	public int yI(Point2D pd) { return (int)pd.getY(); }
	public double x(Point2D pd) { return pd.getX(); }
	public double y(Point2D pd) { return pd.getY(); }
	

	/**
	* 使用方法
	* 	import util.QuickUtil;
	* 	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	* 	protected void print(Object... objs) { qu.print(objs); }
	*/
	public void print(Object... objs) {
		if(that == null) { System.out.print("インスタンス引数にthisがおすすめ。"); }
		
		System.out.print(cnt++ +" "+ print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print(" " + objs[i]);
		}
		System.out.println();
	}
	public String print0() {
		if(that != null) {
			return " !" + that.getClass().getSimpleName() + "! ";
		} else {
			return " !" + getClass().getSimpleName() + "! ";
		}
	}
	public void printArr(Collection c) {
		for(Object obj : c) {
			print(obj);
		}
	}
	
//-------------

//時間計測
	long now = 0;
	long past = 0;
	/**
	* 指定のミリ秒たったらtrue
	*/
	public boolean time(long l) {
		now = System.currentTimeMillis();
		if(now - past > l) {
			past = now;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	* このクラスを呼び出すときの、Consumerの使い方（作り方）
	* 	適当なクラスで引数が一つのメソッドを用意 Tekito.method(Object o)
	* 	Consumerにメソッドを格納する
	* 		Consumer<Object> cons = Tekito::method;
	* 		(Pane.getChildren().add(T)の場合 -> Pane.getChildren()::add)
	*/
	public void timeIsArr(long milli, Collection<? extends Object> collect, Consumer<Object> cnsm) {
		Iterator ite = collect.iterator();
		while(ite.hasNext()) {
			if(time(500)) {
				cnsm.accept(ite.next());
			}
		}
	}


}