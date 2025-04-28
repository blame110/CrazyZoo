package FxApp.CrazyZoo.panel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import FxApp.model.AnimalDAO;
import FxApp.model.UtilsBD;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AnimalPanel extends GridPane {

	public AnimalPanel() {

		Label lblIdAnimal = new Label("Id Animal");
		Label lblNombre = new Label("Nombre");
		Label lblEspecie = new Label("Especie");
		Label lblFecNac = new Label("Fecha Nacimiento");
		Label lblSexo = new Label("Sexo");
		Label lblIdJaula = new Label("Id Jaula");

		//Ponemos separacion entre los elementos
		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		ArrayList<Node> listaElementos = new ArrayList<Node>();

		//Añadimos la cabecera
		listaElementos.add(lblIdAnimal);
		listaElementos.add(lblNombre);
		listaElementos.add(lblEspecie);
		listaElementos.add(lblFecNac);
		listaElementos.add(lblSexo);
		listaElementos.add(lblIdJaula);

		//añadimos al grid los elementos
		insertarFila(0, listaElementos);

		//Nos conectamos a Base de datos
		Connection con = UtilsBD.ConectarBD();

		//Cargamos desde BD todos los animales DO
		ResultSet rs = AnimalDAO.getAnimales(con);

	}

	/**
	 * Recibe un numero de fila y los elementos a insertar
	 * @param numFila
	 * @param listaElementos
	 */
	private void insertarFila(int numFila, ArrayList<Node> listaElementos) {

		//Recorremos todos los nodos de la lista que recibimos
		for (int i = 0; i < listaElementos.size(); i++) {
			this.add(listaElementos.get(i), i, numFila);
		}

	}

}
