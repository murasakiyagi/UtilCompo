package utilCompo.information;

import java.io.*;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.input.PickResult;


public interface InfoObserver {

	public void update(PickResult picre, Node node);

}