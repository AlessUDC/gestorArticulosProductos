package com.negocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.negocio.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
