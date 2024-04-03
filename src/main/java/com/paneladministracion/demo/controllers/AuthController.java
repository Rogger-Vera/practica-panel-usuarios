package com.paneladministracion.demo.controllers;

import com.paneladministracion.demo.models.Usuario;
import com.paneladministracion.demo.repository.UsuarioRepository;
import com.paneladministracion.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String loginUsuario(@RequestBody Usuario usuario){

        Usuario usuarioLogueado = usuarioRepository.obtenerUsusarioPorCredenciales(usuario);

        if(usuarioLogueado != null){
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
            return tokenJwt;
        }
        return "Fallo";
    }

}
