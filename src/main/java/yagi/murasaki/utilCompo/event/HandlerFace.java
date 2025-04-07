package yagi.murasaki.utilCompo.event;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.InputEvent;


// setOnxxxはSceneSetOnクラスで
public interface HandlerFace<T extends Event> {

	/** * press | scrool | action */
	public void handle(T e);
	/** * release */
	public void handle2(T e);
	/** * click */
	public void handle3(T e);
	/** * dragg */
	public void handle4(T e);
	/** * enter */
	public void handle5(T e);
	
}