package FxApp.CrazyZoo;

import FxApp.CrazyZoo.panel.AnimalPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ZooApp extends Application {

	@Override
	public void start(Stage ventana) {

		//Creamos el panel de las pestañas
		TabPane tabPes = new TabPane();
		//Pestañas para el panel
		Tab tAnimales = new Tab("Mostrar Animales");
		Tab tZip = new Tab("Comprimir");
		Tab tSsh = new Tab("Ssh");

		//Ponemos closable a false para que no salga la x de cerrar pestaña
		tAnimales.setClosable(false);
		tZip.setClosable(false);
		tSsh.setClosable(false);

		//Creamos el gridpane de animales
		AnimalPanel pAnimal = new AnimalPanel();
		//Metemos el panel de animales en un scroll
		ScrollPane scrollP = new ScrollPane(pAnimal);

		//Añadimos el panel a la pestaña
		tAnimales.setContent(scrollP);

		//Añadimos las pestañas al panel pestañoso
		tabPes.getTabs().addAll(tAnimales, tZip, tSsh);

		Scene scene = new Scene(tabPes, 800, 600);
		ventana.setScene(scene);
		//Mostramos la ventana
		ventana.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

}
