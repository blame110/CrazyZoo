package FxApp.CrazyZoo.panel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// Importaciones necesarias para la interfaz y funcionalidad
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Clase ZipPanel
 * Panel de compresión de archivos que extiende GridPane.
 * Permite seleccionar varios archivos y comprimirlos en un archivo ZIP.
 */
public class ZipPanel extends GridPane {

	// Lista de archivos seleccionados por el usuario
	private List<File> selectedFiles = new ArrayList<>();

	// Campos de texto para el nombre del archivo zip y la ubicación donde guardarlo
	private TextField zipNameField;
	private TextField saveLocationField;

	// Área de texto para mostrar los archivos seleccionados
	private TextArea selectedFilesArea;

	// Constructor
	public ZipPanel() {
		// Configuramos el espacio entre elementos del GridPane
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(15));

		// Etiqueta y botón para seleccionar archivos
		Label filesLabel = new Label("Archivos a comprimir:");
		Button selectFilesBtn = new Button("Seleccionar Archivos");

		// Área donde se muestran los archivos seleccionados
		selectedFilesArea = new TextArea();
		selectedFilesArea.setEditable(false); // Solo lectura
		selectedFilesArea.setPrefHeight(100); // Altura

		// Etiqueta y campo de texto para el nombre del archivo ZIP de salida
		Label nameLabel = new Label("Nombre del ZIP:");
		zipNameField = new TextField("archivo.zip"); // Nombre por defecto

		// Etiqueta, campo y botón para elegir dónde guardar el archivo comprimido
		Label pathLabel = new Label("Guardar en:");
		saveLocationField = new TextField(); // Mostrará la ruta seleccionada
		saveLocationField.setEditable(false);

		Button choosePathBtn = new Button("Seleccionar Carpeta");

		// Botón para iniciar la compresión
		Button zipBtn = new Button("Comprimir");

		// Asignación de acciones a los botones
		selectFilesBtn.setOnAction(e -> selectFiles(getScene().getWindow()));
		choosePathBtn.setOnAction(e -> selectSaveLocation(getScene().getWindow()));
		zipBtn.setOnAction(e -> compressFiles());

		// Añadir los componentes al layout GridPane
		add(filesLabel, 0, 0);
		add(selectFilesBtn, 1, 0);
		add(selectedFilesArea, 0, 1, 2, 1); // Álarea de texto ocupa 2 columnas

		add(nameLabel, 0, 2);
		add(zipNameField, 1, 2);

		add(pathLabel, 0, 3);
		// Usamos un HBox para poner el campo de texto y botón juntos
		HBox pathBox = new HBox(10, saveLocationField, choosePathBtn);
		add(pathBox, 1, 3);

		// Botón de comprimir
		add(zipBtn, 1, 4);
	}

	/**
	 * Método para mostrar un FileChooser y permitir al usuario seleccionar múltiples archivos.
	 */
	private void selectFiles(Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar archivos");

		// Permite seleccionar múltiples archivos
		List<File> files = fileChooser.showOpenMultipleDialog(window);

		// Si el usuario ha seleccionado archivos, los guardamos e imprimimos en el área de texto
		if (files != null) {
			selectedFiles.addAll(files);
			updateSelectedFilesArea();
		}
	}

	/**
	 * Actualiza el área de texto para mostrar la lista de archivos seleccionados.
	 */
	private void updateSelectedFilesArea() {
		StringBuilder sb = new StringBuilder();

		// Vamos agregando cada ruta de archivo al TextArea
		for (File file : selectedFiles) {
			sb.append(file.getAbsolutePath()).append("\n");
		}
		selectedFilesArea.setText(sb.toString());
	}

	/**
	 * Muestra un DirectoryChooser para seleccionar la carpeta donde se guardará el zip.
	 */
	private void selectSaveLocation(Window window) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Seleccionar carpeta de destino");

		// Muestra el selector de carpetas
		File dir = directoryChooser.showDialog(window);
		if (dir != null) {
			// Mostramos la ruta elegida en el campo de texto
			saveLocationField.setText(dir.getAbsolutePath());
		}
	}

	/**
	 * Método que realiza la compresión de los archivos usando ZipOutputStream.
	 */
	private void compressFiles() {
		// Validamos que el usuario haya seleccionado archivos y una carpeta de destino
		if (selectedFiles.isEmpty()) {
			showAlert("Error", "Selecciona al menos un archivo.");
			return;
		}

		String savePath = saveLocationField.getText();
		String zipName = zipNameField.getText();

		if (savePath.isEmpty() || zipName.isEmpty()) {
			showAlert("Error", "Especifica una carpeta y un nombre para el archivo ZIP.");
			return;
		}

		// Creamos el archivo zip a partir del nombre y la ruta
		File zipFile = new File(savePath, zipName);

		try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
			// Buffer para leer los archivos
			byte[] buffer = new byte[1024];

			// Añadimos cada archivo al zip
			for (File file : selectedFiles) {
				try (FileInputStream fis = new FileInputStream(file)) {
					ZipEntry entry = new ZipEntry(file.getName());
					zos.putNextEntry(entry); // Creamos una entrada ZIP con el nombre del archivo

					int len;
					// Leemos el archivo y lo escribimos en el ZIP
					while ((len = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}

					zos.closeEntry(); // Terminamos esta entrada
				}
			}

			// Mostramos mensaje de éxito
			showAlert("Éxito", "Archivos comprimidos correctamente en:\n" + zipFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error", "Ha ocurrido un error al comprimir los archivos.");
		}
	}

	/**
	 * Método auxiliar para mostrar mensajes al usuario.
	 * @param title Título de la alerta.
	 * @param message Mensaje del contenido.
	 */
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Metodos para el menue
	 * 
	 */

	/**
	 * Abre el FileChooser para seleccionar archivos.
	 */
	public void abrirSelectorArchivos() {
		selectFiles(getScene().getWindow());
	}

	/**
	 * Abre el DirectoryChooser para seleccionar ubicación de guardado.
	 */
	public void abrirSelectorDestino() {
		selectSaveLocation(getScene().getWindow());
	}

	/**
	 * Establece el nombre del archivo ZIP desde una entrada externa.
	 */
	public void establecerNombreZip(String nombre) {
		zipNameField.setText(nombre);
	}

	/**
	 * Método que permite ejecutar la compresión externamente.
	 */
	public void ejecutarCompresion() {
		compressFiles();
	}

}