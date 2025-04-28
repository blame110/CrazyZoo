package FxApp.model;

import java.util.Date;

public class AnimalDO {

	private int idAnimal;
	private String nombre;
	private String especie;
	private Date fecha_nac;
	private char sexo;
	private int Jaula_idJaula;

	public AnimalDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnimalDO(int idAnimal, String nombre, String especie, Date fecha_nac, char sexo, int jaula_idJaula) {
		super();
		this.idAnimal = idAnimal;
		this.nombre = nombre;
		this.especie = especie;
		this.fecha_nac = fecha_nac;
		this.sexo = sexo;
		Jaula_idJaula = jaula_idJaula;
	}

	public int getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public int getJaula_idJaula() {
		return Jaula_idJaula;
	}

	public void setJaula_idJaula(int jaula_idJaula) {
		Jaula_idJaula = jaula_idJaula;
	}

}
