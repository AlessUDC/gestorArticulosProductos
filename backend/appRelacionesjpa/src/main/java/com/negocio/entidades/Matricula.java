package com.negocio.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(
    name = "matricula",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_alumno", "id_curso"})
    }
)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;

    @Column(nullable = false)
    private LocalDate fecha;

    /*
     Muchas matrículas pertenecen a un alumno
    */
    @ManyToOne
    @JoinColumn(name = "id_alumno")
    @JsonIgnoreProperties("matriculas")
    private Alumno alumno;

    /*
     Muchas matrículas pertenecen a un curso
    */
    @ManyToOne
    @JoinColumn(name = "id_curso")
    @JsonIgnoreProperties("matriculas")
    private Curso curso;

    // GETTERS Y SETTERS

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}


