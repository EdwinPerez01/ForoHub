package com.alura.ForoHub.controller;

import com.foro_hub.domain.topico.*;
import com.foro_hub.entity.Topic;
import com.foro_hub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

    @Tag(name = "Tópicos")
    @RestController
    @RequestMapping("/topicos")
    @RequiredArgsConstructor
    @SecurityRequirement(name = "bearer-key")
    public class TopicoController {

        @Autowired
        private final TopicService topicService;
        @Autowired
        private final TopicoService topicoService;
        @Autowired
        private final TopicoRepository topicoRepository;

        @Operation(summary = "Crea un tópico.")
        @PostMapping
        @Transactional
        public ResponseEntity<Topic> registrarTopico(@RequestBody @Valid DatosTopico datosTopico, UriComponentsBuilder uriComponentsBuilder) {
            var topico = topicoService.registrar(datosTopico);
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(topico);
        }

        @Operation(summary = "Lista todos los tópicos.")
        @GetMapping
        public ResponseEntity<Page<DatosListarTopico>> listarTopicos(@PageableDefault(size = 10) Pageable pageable) {
            return ResponseEntity.ok(topicoRepository.findAllByOrderByFechaCreacionAsc(pageable).map(DatosListarTopico::new));
        }

        @Operation(summary = "Detalla un tópico.")
        @GetMapping("/{id}")
        public ResponseEntity<DatosDetallesTopico> listarDetallesTopico(@PathVariable Long id) {
            topicoService.validarTopico(id);
            var datosTopico = new DatosDetallesTopico(topicoRepository.getReferenceById(id));
            return ResponseEntity.ok(datosTopico);
        }

        @Operation(summary = "Actualiza un tópico.")
        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<Topic> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico, UriComponentsBuilder uriComponentsBuilder) {
            var respuesta = topicoService.actualizar(datosActualizarTopico);
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(respuesta.getId()).toUri();
            return ResponseEntity.created(url).body(respuesta);
        }

        @Operation(summary = "Elimina un tópico.")
        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
            topicoService.validarTopico(id);
            topicoRepository.deleteById(topicoRepository.getReferenceById(id).getId());
            return ResponseEntity.noContent().build();
        }
    }
