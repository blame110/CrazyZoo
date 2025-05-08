package FxApp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AnimalDAO {

	/**
	 * La función devuelve todos los animales de la bd
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con los animales
	 */
	public static ResultSet getAnimales(Connection con) {

		try {
			//Con la conexion ya activa (con) crea una sentencia para poder ejecutar
			//sentencias sql
			Statement stmt = con.createStatement();

			//Directamente ejecutamos la sentencia
			//Si es una sentenci tipo select se hace con executeQuery
			//Esta nos devuelve un objeto de tipo ResultSet con los datos de la query
			ResultSet rs = stmt.executeQuery("select * from animal");

			return rs;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * La función devuelve todos los animales de la bd
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con los animales
	 */
	public static ArrayList<AnimalDO> getAnimalesDO(Connection con) {

		try {

			ArrayList<AnimalDO> listaAnimales = new ArrayList<AnimalDO>();

			//Con la conexion ya activa (con) crea una sentencia para poder ejecutar
			//sentencias sql
			Statement stmt = con.createStatement();

			//Directamente ejecutamos la sentencia
			//Si es una sentenci tipo select se hace con executeQuery
			//Esta nos devuelve un objeto de tipo ResultSet con los datos de la query
			ResultSet rs = stmt.executeQuery("select * from animal");

			//Recorremos el resultset
			while (rs.next()) {

				//Creamos un animalDO
				AnimalDO animal = new AnimalDO();

				//Cargamos en el animalDO los datos del registro actual del resultset
				animal.setIdAnimal(rs.getInt("idanimal"));
				animal.setNombre(rs.getString("nombre"));
				animal.setEspecie(rs.getString("especie"));
				animal.setFecha_nac(rs.getDate("fecha_nac"));
				animal.setSexo(rs.getString("sexo").charAt(0));
				animal.setJaula_idJaula(rs.getInt("Jaula_idJaula"));

				//Ponemos el AnimalDO en la arraylist
				listaAnimales.add(animal);
			}

			return listaAnimales;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * La función devuelve el animal asociado a la id que introducimos
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con el animal
	 */
	public static ResultSet getAnimal(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "select * from animal where idanimal = ?";

			PreparedStatement pstmt = con.prepareStatement(query);

			//Asignamos el parametro id en la primera interrogacion ( y la unica)
			pstmt.setInt(1, id);

			//Una vez asignados los valores ejecutamos la query
			ResultSet rs = pstmt.executeQuery();

			return rs;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * La función elimina el animal asociado a la id que introducimos
	 * @param con Conexion activa a la bd
	 * @return 1 si ha podido borrar al animal
	 */
	public static int deleteAnimal(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "delete from animal where idanimal = ?";

			PreparedStatement pstmt = con.prepareStatement(query);

			//Asignamos el parametro id en la primera interrogacion ( y la unica)
			pstmt.setInt(1, id);

			//Una vez asignados los valores ejecutamos la query
			//update me devuelve la cantidad de registros afectados por la query
			int resultado = pstmt.executeUpdate();

			return resultado;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return -1;
	}

	/**
	 * Funcion que recibe una conexion y los datos del animal con un animalDO
	 * Y inserta en BD el registro
	 * @param con Conexión activa a la BD
	 * @param animal objeto de la clase AnimalDO con los datos a insertar
	 * @return -1 si falla o no hay datos y 1 si ha podido insertar el registro en BD
	 */
	public static int insertAnimal(Connection con, AnimalDO animal) {
		//Si el animal esta vacio devolvemos error con un -1
		if (animal == null)
			return -1;

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "INSERT INTO `CrazyZoo`.`animal` "
					+ "(`nombre`, `especie`, `fecha_nac`, `sexo`, `Jaula_idJaula`) " + "VALUES (?,?,?,?,?);";

			PreparedStatement pstmt = con.prepareStatement(query);

			//Convertimos a Date de tipo sql la fecha que recibimos de nacimiento
			java.sql.Date fechaNacSql = new java.sql.Date(animal.getFecha_nac().getYear(),
					animal.getFecha_nac().getMonth(), animal.getFecha_nac().getDay());

			//Asignamos el parametro id en la primera interrogacion ( y la unica)
			pstmt.setString(1, animal.getNombre());
			pstmt.setString(2, animal.getEspecie());

			pstmt.setDate(3, fechaNacSql);

			pstmt.setString(4, Character.toString(animal.getSexo()));
			pstmt.setInt(5, animal.getJaula_idJaula());

			//Una vez asignados los valores ejecutamos la query
			//update me devuelve la cantidad de registros afectados por la query
			int resultado = pstmt.executeUpdate();

			return resultado;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;

		}

	}

}
