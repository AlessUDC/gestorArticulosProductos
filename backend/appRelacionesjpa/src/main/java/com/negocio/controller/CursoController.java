package com.negocio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.negocio.entidades.Curso;
import com.negocio.repository.CursoRepository;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@PostMapping
	public Curso guardar(@RequestBody Curso curso) {
		return cursoRepository.save(curso);
	}

	@GetMapping
	public List<Curso> listar() {
		return cursoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Curso buscar(@PathVariable Integer id) {
		return cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Integer id) {
		cursoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Curso editar(@PathVariable Integer id, @RequestBody Curso cursoActualizado) {

		Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no existe"));

		curso.setNombre(cursoActualizado.getNombre());
		curso.setCreditos(cursoActualizado.getCreditos());

		return cursoRepository.save(curso);
	}
}

