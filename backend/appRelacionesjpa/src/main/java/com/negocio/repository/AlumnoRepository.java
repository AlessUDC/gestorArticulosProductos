package com.negocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.negocio.entidades.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

}

