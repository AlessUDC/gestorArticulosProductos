package com.negocio.entidades;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "alumno")
public class Alumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alumno")
	private Integer idAlumno;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private Integer edad;

	/*
	 Un alumno puede tener muchas matrículas
	 */
	@OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("alumno")
	private List<Matricula> matriculas = new ArrayList<>();

	// GETTERS Y SETTERS

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
}

