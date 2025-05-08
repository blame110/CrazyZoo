package FxApp.CrazyZoo.window;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddAnimalWindow extends Stage {

	public AddAnimalWindow() {
		//Definimos los paneles principales
		GridPane gPanel = new GridPane();

		//Controles del panel
		Label lblNombre = new Label("Nombre");
		TextField txtNombre = new TextField();

		Label lblEspecie = new Label("Especie");
		ChoiceBox chbEspecie = new ChoiceBox();
		chbEspecie.getItems().addAll("felino", "pez", "pájaro", "canino", "equino", "bovino", "porcino", "ovino",
				"caprino", "reptil", "anfibio", "insecto", "arácnido", "crustáceo", "molusco", "marsupial", "primate",
				"roedor", "cetáceo", "murciélago", "mustélido", "cérvido", "elefante", "rinoceronte", "hipopótamo",
				"jirafa", "canguro", "koala", "ornitorrinco", "equidna", "lemur", "orangután", "gorila", "chimpancé",
				"bonobo", "lobo", "zorro", "oso", "león", "tigre", "leopardo", "guepardo", "pantera", "jaguar", "hiena",
				"lince", "puma", "nutria", "comadreja", "armadillo", "oso hormiguero", "perezoso", "tapir", "manatí",
				"delfín", "ballena", "orca", "tiburón", "raya", "anguila", "salmón", "atún", "bacalao", "pez payaso",
				"pez globo", "caballito de mar", "pulpo", "calamar", "sepia", "medusa", "estrella de mar",
				"erizo de mar", "cangrejo", "langosta", "camarón", "caracol", "almeja", "ostra", "abeja", "mariposa",
				"escarabajo", "hormiga", "mosquito", "libélula", "grillo", "saltamontes", "mantis religiosa", "araña",
				"escorpión", "ácaro", "ciempiés", "milpiés", "salamandra", "rana", "sapo", "tritón", "cocodrilo",
				"caimán", "tortuga", "serpiente", "lagarto", "iguana", "camaleón", "gecko", "tuátara", "águila", "búho",
				"halcón", "cóndor", "buitre", "cuervo", "gorrión", "canario", "pingüino", "avestruz", "emu", "cisne",
				"pato", "ganso", "flamenco", "pelícano", "garza", "cigüeña", "pavo real", "faisán", "codorniz",
				"paloma", "tucán", "loro", "guacamayo", "cacatúa", "colibrí", "kiwi", "albatros", "frailecillo",
				"cisne negro", "cisne blanco");

		Label lblSexo = new Label("Sexo");

		//Para que los radiobuttons sean un unico campo hay que meterlos en un togglegroup
		ToggleGroup tgSexo = new ToggleGroup();
		RadioButton radMasculino = new RadioButton("Masculino");
		RadioButton radFemenino = new RadioButton("Femenino");
		RadioButton radIndefinido = new RadioButton("Indefinido");
		tgSexo.getToggles().addAll(radMasculino, radFemenino, radIndefinido);

		Label lblFecNac = new Label("Fecha Nacimiento ");
		// DatePicker para seleccionar el día
		DatePicker fecNac = new DatePicker();
		fecNac.setPromptText("Selecciona un día");

		//Jaula
		Label lblJaula = new Label("Jaula");
		ChoiceBox chbJaula = new ChoiceBox();
		for (int i = 1; i < 100; i++) {
			chbJaula.getItems().addAll(new Integer(i));
		}

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

		var scene = new Scene(gPanel, 500, 400);

		this.setScene(scene);

	}

}
