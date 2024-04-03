package com.paneladministracion.demo.repository;

import com.paneladministracion.demo.models.Usuario;

import java.util.List;

public interface IUsuarioRepository {

    List<Usuario> obtenerUsuarios();

    void eliminarUsuario(Long id);

    void registrarUsuario(Usuario usuario);

    Usuario obtenerUsusarioPorCredenciales(Usuario usuario);
}

