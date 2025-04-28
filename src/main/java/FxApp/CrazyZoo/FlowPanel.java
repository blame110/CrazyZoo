package FxApp.CrazyZoo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class FlowPanel extends Application {

	@Override
	public void start(Stage stage) {

		String javaVersion = SystemInfo.javaVersion();
		var javafxVersion = SystemInfo.javafxVersion();

		FlowPane fPanel = new FlowPane();

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

		//Ponemos espaciado entre los elementos
		fPanel.setVgap(20);
		fPanel.setHgap(20);

		//Espaciado entre bordes y contenido
		fPanel.setPadding(new Insets(10, 10, 10, 10));

		//Por defecto la especie pez es la seleccionada
		chbEspecie.getSelectionModel().select(1);

		//añadimos al panelflow los componentes
		fPanel.getChildren().addAll(lblNombre, txtNombre, lblEspecie, chbEspecie, lblSexo, radMasculino, radFemenino,
				radIndefinido, lblEdad, sldEdad);

		var scene = new Scene(fPanel, 400, 300);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}