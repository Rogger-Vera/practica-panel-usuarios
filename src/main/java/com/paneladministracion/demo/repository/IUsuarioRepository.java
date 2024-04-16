package com.paneladministracion.demo.repository;

import com.paneladministracion.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

}

