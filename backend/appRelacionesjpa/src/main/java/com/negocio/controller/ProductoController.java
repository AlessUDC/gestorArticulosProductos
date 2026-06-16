package com.negocio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.negocio.entidades.Producto;
import com.negocio.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // GET /api/productos  →  listar todos
    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // GET /api/productos/{id}  →  buscar por ID
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    // POST /api/productos  →  crear nuevo producto
    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // PUT /api/productos/{id}  →  actualizar producto existente
    @PutMapping("/{id}")
    public Producto editar(@PathVariable Integer id, @RequestBody Producto productoActualizado) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no existe con id: " + id));

        producto.setNombre(productoActualizado.getNombre());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setImagenUrl(productoActualizado.getImagenUrl());

        return productoRepository.save(producto);
    }

    // DELETE /api/productos/{id}  →  eliminar producto
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        productoRepository.deleteById(id);
    }
}
