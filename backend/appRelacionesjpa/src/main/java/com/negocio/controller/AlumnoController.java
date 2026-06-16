package com.negocio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.negocio.entidades.Alumno;
import com.negocio.repository.AlumnoRepository;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoRepository alumnoRepository;

	@PostMapping
	public Alumno guardar(@RequestBody Alumno alumno) {
		return alumnoRepository.save(alumno);
	}

	@GetMapping
	public List<Alumno> listar() {
		return alumnoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Alumno buscar(@PathVariable Integer id) {
		return alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Integer id) {
		alumnoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Alumno editar(@PathVariable Integer id, @RequestBody Alumno alumnoActualizado) {

		// Buscar alumno por ID
		Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno no existe"));

		// Actualizar datos
		alumno.setNombre(alumnoActualizado.getNombre());
		alumno.setEdad(alumnoActualizado.getEdad());

		// Guardar cambios
		return alumnoRepository.save(alumno);
	}
}


