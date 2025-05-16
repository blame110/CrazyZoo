package FxApp.CrazyZoo;

import FxApp.CrazyZoo.panel.AnimalPanel;
import FxApp.CrazyZoo.panel.SshPanel;
import FxApp.CrazyZoo.panel.ZipPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ZooApp extends Application {

	@Override
	public void start(Stage ventana) {

		BorderPane pContenedor = new BorderPane();

		//Definicion de objetos para el menu
		MenuBar barraMenu = new MenuBar();

		Menu mArchivo = new Menu("Archivo");
		Menu mHerramientas = new Menu("Herramientas");
		Menu mZip = new Menu("Comprimir");
		Menu mAyuda = new Menu("Ayuda");

		MenuItem iNuevo = new MenuItem("Nuevo..");
		MenuItem iAbrir = new MenuItem("Abrir..");
		MenuItem iCerrar = new MenuItem("Cerrar");
		MenuItem iSeleccionar = new MenuItem("Seleccionar Archivos..");
		MenuItem iDestino = new MenuItem("Destino");
		MenuItem iComprimir = new MenuItem("Comprimir");
		MenuItem iSeleccionar2 = new MenuItem("Seleccionar Archivos..");
		MenuItem iDestino2 = new MenuItem("Destino");
		MenuItem iComprimir2 = new MenuItem("Comprimir");
		MenuItem iAcercaDe = new MenuItem("Acerca de");
		MenuItem iPulsame = new MenuItem("Atrevete si tienes...");

		//añadimos los items a los menus
		mArchivo.getItems().addAll(iNuevo, iAbrir, mZip, iCerrar);
		mHerramientas.getItems().addAll(iSeleccionar2, iDestino2, iComprimir2);
		mAyuda.getItems().addAll(iAcercaDe, iPulsame);
		mZip.getItems().addAll(iSeleccionar, iDestino, iComprimir);

		//Ponemos en la barra de menu los menus
		barraMenu.getMenus().addAll(mArchivo, mHerramientas, mAyuda);

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

		//Creamos el panel ssh
		SshPanel sshP = new SshPanel();
		tSsh.setContent(sshP);

		//Creamos el panel de compresión
		ZipPanel zPanel = new ZipPanel();
		tZip.setContent(zPanel);

		//Añadimos las pestañas al panel pestañoso
		tabPes.getTabs().addAll(tAnimales, tZip, tSsh);

		//Metemos dentro del borderpane pcontenedor el menu y el panel de pestañas tabpane, tabPEs
		pContenedor.setTop(barraMenu);
		pContenedor.setCenter(tabPes);

		Scene scene = new Scene(pContenedor, 800, 600);
		ventana.setScene(scene);
		//Mostramos la ventana
		ventana.show();

		/******************************************
		 * EVENTOS
		 *****************************************/
		iCerrar.setOnAction(e -> {
			ventana.close();
		});

		iAbrir.setOnAction(e -> {
			FileChooser fChus = new FileChooser();
			fChus.showOpenDialog(ventana);
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

}
