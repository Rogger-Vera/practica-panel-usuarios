package com.paneladministracion.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paneladministracion.demo.controllers.UsuarioController;
import com.paneladministracion.demo.models.Usuario;
import com.paneladministracion.demo.repository.IUsuarioRepository;
import com.paneladministracion.demo.service.UsuarioService;
import com.paneladministracion.demo.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;

    @Test
    public void deberiaObtenerUsuariosTest() throws Exception {
        List<Usuario> listaUsuarios = Arrays.asList(
                Usuario.builder().id(1L).nombre("Jose").apellido("Perez").email("jose@example.com").telefono("123456").contrasena("password").build(),
                Usuario.builder().id(2L).nombre("Maria").apellido("Vega").email("maria@example.com").telefono("654321").contrasena("password").build()
        );

        given(usuarioService.obtenerUsuarios()).willReturn(listaUsuarios);
        given(jwtUtil.create(anyString(), anyString())).willReturn("TOKEN");

        ResultActions response = mockMvc.perform(get("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    public void noDeberiaObtenerUsuariosTest() throws Exception {
        given(usuarioService.obtenerUsuarios()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    public void deberiaEliminarUsuarioTest() throws Exception {
        Long usuarioId = 1L;
        willDoNothing().given(usuarioService).eliminarUsuario(usuarioId);

        mockMvc.perform(delete("/api/usuario/{id}", usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void noDeberiaEliminarUsuarioTest() throws Exception {
        Long usuarioId = 1L;

        doThrow(new RuntimeException("Usuario no encontrado")).when(usuarioService).eliminarUsuario(usuarioId);

        mockMvc.perform(delete("/api/usuario/{id}", usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    public void deberiaRegistrarUsuarioTest() throws Exception {
        Usuario usuario = Usuario.builder().id(1L).nombre("Jose").apellido("Perez").email("jose@example.com").telefono("123456").contrasena("password").build();

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)));

    }

    @Test
    public void noDeberiaRegistrarUsuarioTest() throws Exception {
        Usuario usuario = Usuario.builder().id(1L).nombre("Jose").apellido("Perez").email("jose@example.com").telefono("123456").contrasena("password").build();

        doThrow(new RuntimeException("Error al registrar usuario")).when(usuarioService).registrarUsuario(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
