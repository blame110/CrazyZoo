package FxApp.CrazyZoo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class EjemploGridPane extends Application {

	@Override
	public void start(Stage stage) {

		String javaVersion = SystemInfo.javaVersion();
		var javafxVersion = SystemInfo.javafxVersion();

		//Definimos los paneles principales
		GridPane gPanel = new GridPane();
		ScrollPane sPane = new ScrollPane();

		//Controles del panel
		Label lblNombre = new Label("Nombre");
		TextField txtNombre = new TextField();
		Label lblEspecie = new Label("Especie");
		ChoiceBox chbEspecie = new ChoiceBox();
		chbEspecie.getItems().addAll("felino", "pez", "pajaro");
		Label lblSexo = new Label("Sexo");

		//Para que los radiobuttons sean un unico campo hay que meterlos en un togglegroup
		ToggleGroup tgSexo = new ToggleGroup();
		RadioButton radMasculino = new RadioButton("Masculino");
		RadioButton radFemenino = new RadioButton("Femenino");
		RadioButton radIndefinido = new RadioButton("Indefinido");
		tgSexo.getToggles().addAll(radMasculino, radFemenino, radIndefinido);

		Label lblEdad = new Label("Edad ");
		Slider sldEdad = new Slider(0, 200, 5);
		sldEdad.setShowTickLabels(true);

		sldEdad.setOnMouseReleased(e -> {
			lblEdad.setText("Edad " + ((int) sldEdad.getValue()));
		});

		//Por defecto la especie pez es la seleccionada
		chbEspecie.getSelectionModel().select(1);
		//Por defecto el sexo será indefinido
		radIndefinido.setSelected(true);

		//Ponemos espaciado entre los elementos
		gPanel.setVgap(20);
		gPanel.setHgap(20);

		//Espaciado entre bordes y contenido
		gPanel.setPadding(new Insets(10, 10, 10, 10));

		//añadimos al gridpanel los componentes
		gPanel.add(lblNombre, 0, 0);
		//Los ultimos dos numeros de la funcion add
		//indican la cantidad de columnas y filas que ocupa el elemento
		//Es similar a las propiedades colspan y rowspan de html
		gPanel.add(txtNombre, 1, 0, 3, 1);
		gPanel.add(lblEspecie, 0, 1);
		gPanel.add(chbEspecie, 1, 1, 3, 1);
		gPanel.add(lblSexo, 0, 2);
		gPanel.add(radFemenino, 1, 2);
		gPanel.add(radMasculino, 2, 2);
		gPanel.add(radIndefinido, 3, 2);

		//Añadimos 40 botones con un bucle
		for (int i = 4; i <= 14; i++)
			for (int j = 0; j < 4; j++) {
				Button btnPulsame = new Button("Pulsame en fila " + i + " y columna " + j);
				gPanel.add(btnPulsame, j, i);
			}

		//Pongo dentro del scroll panel el gridpane
		sPane.setContent(gPanel);

		var scene = new Scene(sPane, 400, 300);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}