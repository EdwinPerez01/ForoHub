package com.alura.ForoHub.domain.respuesta.validaciones;

import com.alura.ForoHub.domain.domain.respuesta.DatosRespuesta;
import com.alura.ForoHub.domain.domain.topico.TopicoRepository;
import com.alura.ForoHub.domain.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopico implements IValidadorDeRespuestas {
    @Autowired
    TopicoRepository topicoRepository;

    public void validar(DatosRespuesta datosRespuesta) {
        if (!topicoRepository.findById(datosRespuesta.idTopico()).isPresent()) {
            throw new ValidacionDeIntegridad("topico no econtrado");
        }
    }
}