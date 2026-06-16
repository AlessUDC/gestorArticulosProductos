package com.negocio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.negocio.dto.MatriculaRequest;
import com.negocio.entidades.*;
import com.negocio.repository.AlumnoRepository;
import com.negocio.repository.CursoRepository;
import com.negocio.repository.MatriculaRepository;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

	// Repository para operaciones CRUD sobre la tabla matricula
	@Autowired
	private MatriculaRepository matriculaRepository;

	// Repository para buscar alumnos en la base de datos
	@Autowired
	private AlumnoRepository alumnoRepository;

	// Repository para buscar cursos en la base de datos
	@Autowired
	private CursoRepository cursoRepository;

	// Registrar una nueva matrícula
	@PostMapping
	public Matricula matricular(@RequestBody MatriculaRequest request) {
		// Buscar alumno usando su ID
		Alumno alumno = alumnoRepository.findById(request.getIdAlumno())
				.orElseThrow(() -> new RuntimeException("Alumno no existe"));
		// Buscar curso usando su ID
		Curso curso = cursoRepository.findById(request.getIdCurso())
				.orElseThrow(() -> new RuntimeException("Curso no existe"));
		// Crear objeto matrícula
		Matricula matricula = new Matricula();
		// Relacionar matrícula con alumno
		matricula.setAlumno(alumno);
		// Relacionar matrícula con curso
		matricula.setCurso(curso);
		// Registrar fecha actual
		matricula.setFecha(LocalDate.now());
		// Guardar matrícula en MySQL
		return matriculaRepository.save(matricula);
	}

	@GetMapping
	public List<Matricula> listar() {
		return matriculaRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Integer id) {
		matriculaRepository.deleteById(id);
	}

	@GetMapping("/{id}")
	public Matricula buscar(@PathVariable Integer id) {
		return matriculaRepository.findById(id).orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
	}

	@PutMapping("/{id}")
	public Matricula editar(@PathVariable Integer id, @RequestBody MatriculaRequest request) {

		// Buscar matrícula por ID
		Matricula matricula = matriculaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Matrícula no existe"));

		// Buscar alumno por ID
		Alumno alumno = alumnoRepository.findById(request.getIdAlumno())
				.orElseThrow(() -> new RuntimeException("Alumno no existe"));

		// Buscar curso por ID
		Curso curso = cursoRepository.findById(request.getIdCurso())
				.orElseThrow(() -> new RuntimeException("Curso no existe"));

		// Actualizar relaciones
		matricula.setAlumno(alumno);
		matricula.setCurso(curso);

		// Guardar cambios
		return matriculaRepository.save(matricula);
	}

}

