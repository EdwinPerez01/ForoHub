package com.alura.ForoHub.controller;

import com.alura.ForoHub.domain.respuesta.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Respuestas")
@RestController
@RequestMapping("/respuestas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private final RespuestaService respuestaService;
    private final RespuestaRepository respuestaRepository;

    @Operation(summary = "Crea una respuesta.")
    @PostMapping
    @Transactional
    public ResponseEntity<Respuesta> registrarRespuesta(@RequestBody @Valid DatosRespuesta datosRespuesta) {
        var respuesta = respuestaService.registrar(datosRespuesta);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Lista todas las respuestas.")
    @GetMapping
    public ResponseEntity<Page<DatosListarRespuesta>> listarRespuestas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(respuestaRepository.findAll(pageable).map(DatosListarRespuesta::new));
    }

    @Operation(summary = "Actualiza una respuesta.")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Respuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        var respuesta = respuestaService.actualizar(id, datosActualizarRespuesta);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Elimina una respuesta.")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        respuestaService.validarRespuesta(id);
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
