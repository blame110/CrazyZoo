package FxApp.CrazyZoo.panel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import FxApp.model.AnimalDAO;
import FxApp.model.AnimalDO;
import FxApp.model.UtilsBD;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AnimalPanel extends GridPane {

	public AnimalPanel() {

		Button btnCrear = new Button("Insertar Animal Nuevo");
		this.add(btnCrear, 0, 0, 7, 1);

		Label lblIdAnimal = new Label("Id Animal");
		lblIdAnimal.setStyle("-fx-font-weight: bold;");
		Label lblNombre = new Label("Nombre");
		Label lblEspecie = new Label("Especie");
		Label lblFecNac = new Label("Fecha Nacimiento");
		Label lblSexo = new Label("Sexo");
		Label lblIdJaula = new Label("Id Jaula");
		Label lblEliminar = new Label("Eiminar");

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
		listaElementos.add(lblEliminar);

		//añadimos al grid los elementos
		insertarFila(1, listaElementos);

		//Nos conectamos a Base de datos
		Connection con = UtilsBD.ConectarBD();

		//Cargamos desde BD todos los animales DO
		ArrayList<AnimalDO> listaAnimales = AnimalDAO.getAnimalesDO(con);

		//Creamos un entero para definir la fila donde meter las label de cada animalDO
		int filaActual = 2;

		//Recorremos los datos de los animales
		for (AnimalDO animal : listaAnimales) {

			listaElementos = new ArrayList<Node>();

			Label IdAnimal = new Label();
			Label Nombre = new Label();
			Label Especie = new Label();
			Label FecNac = new Label();
			Label Sexo = new Label();
			Label IdJaula = new Label();
			Button btnEliminar = new Button("Eliminar");

			//Cargamos el texto de las etiquetas con los datos del animalDO Actual
			IdAnimal.setText(Integer.toString(animal.getIdAnimal()));
			Nombre.setText(animal.getNombre());
			Especie.setText(animal.getEspecie());
			FecNac.setText(animal.getFecha_nac().toString());
			Sexo.setText(Character.toString(animal.getSexo()));
			IdJaula.setText(Integer.toString(animal.getJaula_idJaula()));

			//Añadimos la cabecera
			listaElementos.add(IdAnimal);
			listaElementos.add(Nombre);
			listaElementos.add(Especie);
			listaElementos.add(FecNac);
			listaElementos.add(Sexo);
			listaElementos.add(IdJaula);
			listaElementos.add(btnEliminar);

			//añadimos al grid los elementos
			insertarFila(filaActual, listaElementos);

			//Añadimos un evento para que cuando se pulse el boton eliminar borre el animal de la fila actual
			btnEliminar.setOnAction(e -> {
				//Cuando se pulse el boton
				//llamo a la funcion deleteAnimal con la conexion y el idAnimal actual
				//Para borrarlo
				AnimalDAO.deleteAnimal(con, animal.getIdAnimal());
				this = new AnimalPanel();
			});

			//Pasamos a la siguiente fila
			filaActual++;
		}

		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
