package utilCompo.information;

import java.io.*;
import java.util.*;


public interface InfoSubject {

	public void registerObserver(InfoObserver obs);
	public void removeObserver(InfoObserver obs);
	public void notifyObservers();

}