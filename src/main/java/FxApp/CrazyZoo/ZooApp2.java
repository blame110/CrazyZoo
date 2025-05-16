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
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ZooApp2 extends Application {

	@Override
	public void start(Stage ventana) {
		// Creamos las pestañas
		TabPane tabPes = new TabPane();

		// Creamos las pestañas individuales
		Tab tAnimales = new Tab("Mostrar Animales");
		Tab tZip = new Tab("Comprimir");
		Tab tSsh = new Tab("Ssh");

		// Desactivamos la opción de cerrar las pestañas
		tAnimales.setClosable(false);
		tZip.setClosable(false);
		tSsh.setClosable(false);

		// Creamos los paneles
		AnimalPanel pAnimal = new AnimalPanel();
		ScrollPane scrollP = new ScrollPane(pAnimal);
		tAnimales.setContent(scrollP);

		SshPanel sshP = new SshPanel();
		tSsh.setContent(sshP);

		// NUEVO: Creamos el panel de compresión y lo guardamos en una variable
		ZipPanel zipPanel = new ZipPanel();
		tZip.setContent(zipPanel);

		// Añadimos las pestañas al tabpane
		tabPes.getTabs().addAll(tAnimales, tZip, tSsh);

		// ------------------------------------------
		// NUEVO: Creamos la barra de menús superior
		// ------------------------------------------
		MenuBar menuBar = new MenuBar();

		// Menú principal "Archivo"
		Menu menuArchivo = new Menu("Archivo");

		// Submenú "Comprimir"
		Menu menuComprimir = new Menu("Comprimir");

		// Opciones del submenú "Comprimir"
		MenuItem itemSeleccionarArchivos = new MenuItem("Seleccionar archivos...");
		MenuItem itemSeleccionarCarpeta = new MenuItem("Seleccionar carpeta destino...");
		MenuItem itemNombreZip = new MenuItem("Establecer nombre ZIP...");
		MenuItem itemComprimir = new MenuItem("Comprimir archivos");
		MenuItem itemIrAComprimir = new MenuItem("Ir a pestaña Comprimir");

		// Añadimos los ítems al menú "Comprimir"
		menuComprimir.getItems().addAll(itemSeleccionarArchivos, itemSeleccionarCarpeta, itemNombreZip,
				new SeparatorMenuItem(), itemComprimir, new SeparatorMenuItem(), itemIrAComprimir);

		// Añadimos "Comprimir" al menú principal
		menuArchivo.getItems().add(menuComprimir);

		// Añadimos el menú "Archivo" a la barra de menús
		menuBar.getMenus().add(menuArchivo);

		// ------------------------------------------
		// ACCIONES DE LOS MENÚS
		// ------------------------------------------

		itemSeleccionarArchivos.setOnAction(e -> {
			tabPes.getSelectionModel().select(tZip); // Aseguramos estar en pestaña "Comprimir"
			zipPanel.abrirSelectorArchivos();
		});

		itemSeleccionarCarpeta.setOnAction(e -> {
			tabPes.getSelectionModel().select(tZip);
			zipPanel.abrirSelectorDestino();
		});

		itemNombreZip.setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog("archivo.zip");
			dialog.setTitle("Nombre del ZIP");
			dialog.setHeaderText("Introduce el nombre del archivo ZIP");
			dialog.setContentText("Nombre:");

			dialog.showAndWait().ifPresent(nombre -> zipPanel.establecerNombreZip(nombre));
			tabPes.getSelectionModel().select(tZip);
		});

		itemComprimir.setOnAction(e -> {
			tabPes.getSelectionModel().select(tZip);
			zipPanel.ejecutarCompresion();
		});

		itemIrAComprimir.setOnAction(e -> {
			tabPes.getSelectionModel().select(tZip);
		});

		// ------------------------------------------
		// JPanel principal con BorderPane
		// ------------------------------------------
		BorderPane rootPane = new BorderPane();
		rootPane.setTop(menuBar); // Arriba: la barra de menú
		rootPane.setCenter(tabPes); // Centro: las pestañas

		Scene scene = new Scene(rootPane, 800, 600);
		ventana.setScene(scene);
		ventana.setTitle("CrazyZoo App");
		ventana.show();
	}

	public static void main(String[] args) {
		launch();
	}
}