package com.negocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.negocio.entidades.Curso;

public interface CursoRepository  extends JpaRepository<Curso, Integer>{

}
