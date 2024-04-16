package com.paneladministracion.demo.service;

import com.paneladministracion.demo.models.Usuario;
import com.paneladministracion.demo.repository.IUsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UsuarioService{
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;
    public List<Usuario> obtenerUsuarios() {
        return iUsuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        iUsuarioRepository.deleteById(id);
    }

    public void registrarUsuario(Usuario usuario) {
        iUsuarioRepository.save(usuario);
    }

    public Usuario obtenerUsusarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> usuariosEncontrados = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        if (usuariosEncontrados.isEmpty()){
            return null;
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(usuariosEncontrados.get(0).getContrasena(), usuario.getContrasena())) {
            return usuariosEncontrados.get(0);
        }
        return null;
    }

}
