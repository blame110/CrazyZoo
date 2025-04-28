package FxApp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadoDAO {

	/**
	 * La función devuelve todos los empleados de la bd
	 * @param con Conexion activa a la bd
	 * @return un ResultSet cargado con los empleados
	 */
	public static ResultSet getEmpleados(Connection con) {

		try {
			//Con la conexion ya activa (con) crea una sentencia para poder ejecutar
			//sentencias sql
			Statement stmt = con.createStatement();

			//Directamente ejecutamos la sentencia
			//Si es una sentenci tipo select se hace con executeQuery
			//Esta nos devuelve un objeto de tipo ResultSet con los datos de la query
			ResultSet rs = stmt.executeQuery("select * from empleados");

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
	public static ResultSet getEmpleado(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "select * from empleados where idempleados = ?";

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
	public static int deleteEmpleado(Connection con, int id) {

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "delete from empleados where idempleados = ?";

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
	 * Funcion que recibe una conexion y los datos del empleado con un empleadoDO
	 * Y inserta en BD el registro
	 * @param con Conexión activa a la BD
	 * @param empleado objeto de la clase EmpleadoDO con los datos a insertar
	 * @return -1 si falla o no hay datos y 1 si ha podido insertar el registro en BD
	 */
	public static int insertEmpleado(Connection con, AnimalDO empleado) {
		//Si el empleado esta vacio devolvemos error con un -1
		if (empleado == null)
			return -1;

		try {

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "INSERT INTO `frigopie`.`empleados` "
					+ "(`nombre`, `apellidos`, `edad`, `sueldo`, `puesto`) " + "VALUES (?,?,?,?,?);";

			PreparedStatement pstmt = con.prepareStatement(query);

			//Asignamos el parametro id en la primera interrogacion ( y la unica)
			pstmt.setString(1, empleado.getNombre());
			pstmt.setString(2, empleado.getApellidos());
			pstmt.setInt(3, empleado.getEdad());
			pstmt.setDouble(4, empleado.getSueldo());
			pstmt.setInt(5, empleado.getPuesto());

			//Una vez asignados los valores ejecutamos la query
			//update me devuelve la cantidad de registros afectados por la query
			int resultado = pstmt.executeUpdate();

			return resultado;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;

		}

	}

	/**
	 * Funcion que recibe un empleado y lo modifica en bd
	 * @param con Conexion activa
	 * @param empleado EmpleadoDO con los datos a actualizar
	 * @return -1 si el empleado esta vacio o hay fallo al actualizar 
	 * -2 si no existe en BD y 1 si lo ha actualizado
	 */
	public static int updateEmpleado(Connection con, AnimalDO empleado) {
		//Si el empleado esta vacio devolvemos error con un -1
		if (empleado == null)
			return -1;

		try {

			//Primero comprobamos si el empleado existe en bd
			ResultSet rs = getEmpleado(con, empleado.getIdEmpleado());
			//Si no esta devolvemos error
			if (!rs.next())
				return -2;

			//Para evitar la inyeccion sql los parametros de la query los metemos con ?
			//Y se los añadiremos despues de forma controlada utilizando preparedStatement
			String query = "UPDATE `frigopie`.`empleados` SET"
					+ " `nombre`=?, `apellidos`=?, `edad`=?, `sueldo`=?, `puesto`=? " + " WHERE `idempleados`=?;";

			PreparedStatement pstmt = con.prepareStatement(query);

			//Asignamos el parametro id en la primera interrogacion ( y la unica)
			pstmt.setString(1, empleado.getNombre());
			pstmt.setString(2, empleado.getApellidos());
			pstmt.setInt(3, empleado.getEdad());
			pstmt.setDouble(4, empleado.getSueldo());
			pstmt.setInt(5, empleado.getPuesto());
			pstmt.setInt(6, empleado.getIdEmpleado());

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
