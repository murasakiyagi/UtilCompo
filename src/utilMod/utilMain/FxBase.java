package utilMain;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class FxBase extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 700, 500);
		stage.setScene(scene);
		stage.show();
	}

}
