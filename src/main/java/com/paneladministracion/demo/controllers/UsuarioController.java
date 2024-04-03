package com.paneladministracion.demo.controllers;

import com.paneladministracion.demo.models.Usuario;
import com.paneladministracion.demo.repository.UsuarioRepository;
import com.paneladministracion.demo.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario obtenerUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Luis");
        usuario.setApellido("Vega");
        usuario.setEmail("email@email.com");
        usuario.setTelefono("123456");
        usuario.setContrasena("asdf1234");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios")
    public List<Usuario> obtenerUsuarios(@RequestHeader(value = "Authorization") String token){
        if (!validarToken(token)){ return null; }

        return usuarioRepository.obtenerUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public void eliminarUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        if (!validarToken(token)){ return; }
        usuarioRepository.eliminarUsuario(id);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024, 1, usuario.getContrasena());
        usuario.setContrasena(hash);

        usuarioRepository.registrarUsuario(usuario);

    }

}
