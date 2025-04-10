package yagi.murasaki.utilCompo.event;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.InputEvent;


/**
* setOnxxxはSceneSetOnクラスで
* 
* 
* 
*/
public interface HandlerFace<T extends Event> {

	/**
	* press | scrool | action
	* @param e 各種イベント
	*/
	public void handle(T e);
	/**
	* release
	* @param e 各種イベント
	*/
	public void handle2(T e);
	/**
	* click
	* @param e 各種イベント
	*/
	public void handle3(T e);
	/**
	* dragg
	* @param e 各種イベント
	*/
	public void handle4(T e);
	/**
	* enter
	* @param e 各種イベント
	*/
	public void handle5(T e);
	
}