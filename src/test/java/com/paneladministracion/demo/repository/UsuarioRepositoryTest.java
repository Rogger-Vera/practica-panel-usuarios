package com.paneladministracion.demo.repository;

import com.paneladministracion.demo.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = Usuario.builder()
                .nombre("Christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
    }

    @Test
    void testGuardarEmpleado(){
        Usuario ususario1 = Usuario.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        Usuario usuarioGuardado = iUsuarioRepository.save(ususario1);

        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testListarEmpleados(){
        Usuario usuario1 = Usuario.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();
        iUsuarioRepository.save(usuario1);
        iUsuarioRepository.save(usuario);

        List<Usuario> listaUsuarios = iUsuarioRepository.findAll();

        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(2);
    }

    @Test
    void testObtenerEmpleadoPorId(){
        iUsuarioRepository.save(usuario);

        Usuario usuarioBD = iUsuarioRepository.findById(usuario.getId()).get();

        assertThat(usuarioBD).isNotNull();
    }

    @Test
    void testEliminarEmpleado(){
        iUsuarioRepository.save(usuario);

        iUsuarioRepository.deleteById(usuario.getId());
        Optional<Usuario> usuarioObtenido = iUsuarioRepository.findById(usuario.getId());

        assertThat(usuarioObtenido).isEmpty();
    }
}
