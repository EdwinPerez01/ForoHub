package com.alura.ForoHub.domain.respuesta.validaciones;

import com.alura.ForoHub.domain.respuesta.DatosRespuesta;
import com.alura.ForoHub.domain.usuario.UsuarioRepository;
import com.alura.ForoHub.infra.errores.ValidacionDeIntegridad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RespuestaUsuario implements IValidadorDeRespuestas {

    private final UsuarioRepository usuarioRepository;

    public void validar(DatosRespuesta datosRespuesta) {
        if (!usuarioRepository.findById(datosRespuesta.idUsuario()).isPresent()) {
            throw new ValidacionDeIntegridad("Usuario no encontrado");
        }
    }
}
