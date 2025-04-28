package FxApp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {

	protected String table;

	/**
	 * La función devuelve todos los empleados de la bd
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con los empleados
	 */
	public ResultSet getAll(Connection con) {

		try {
			//Con la conexion ya activa (con) crea una sentencia para poder ejecutar
			//sentencias sql
			Statement stmt = con.createStatement();

			//Directamente ejecutamos la sentencia
			//Si es una sentenci tipo select se hace con executeQuery
			//Esta nos devuelve un objeto de tipo ResultSet con los datos de la query
			ResultSet rs = stmt.executeQuery("select * from " + this.table);

			return rs;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * La función devuelve el empleado asociado a la id que introducimos
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con el empleado
	 */
	public ResultSet get(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "select * from " + this.table + " where id" + this.table + " = ?";

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
	 * La función elimina el empleado asociado a la id que introducimos
	 * @param con Conexion activa a la bd
	 * @return 1 si ha podido borrar al empleado
	 */
	public int delete(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "delete from " + this.table + " where id" + this.table + " = ?";

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

}
