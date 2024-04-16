package com.paneladministracion.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paneladministracion.demo.controllers.AuthController;
import com.paneladministracion.demo.models.Usuario;
import com.paneladministracion.demo.service.UsuarioService;
import com.paneladministracion.demo.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .contrasena("password")
                .build();
    }

    @Test
    public void testLoginUsuarioCorrecto() throws Exception {
        Usuario usuarioLogueado = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        given(usuarioService.obtenerUsusarioPorCredenciales(any(Usuario.class))).willReturn(usuarioLogueado);
        given(jwtUtil.create(anyString(), anyString())).willReturn("TOKEN");

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("TOKEN"));
    }

    @Test
    public void testLoginUsuarioFallo() throws Exception {
        given(usuarioService.obtenerUsusarioPorCredenciales(any(Usuario.class))).willReturn(null);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Fallo"));
    }

    // Método de utilidad
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
