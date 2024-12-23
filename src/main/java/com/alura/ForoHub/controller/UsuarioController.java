package com.alura.ForoHub.controller;

import com.alura.ForoHub.domain.usuario.*;
import com.alura.ForoHub.domain.usuario.validaciones.IValidadorDeUsuarios;
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

@Tag(name = "Usuarios")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Operation(summary = "Crea un usuario.")
    @PostMapping
    @Transactional
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody @Valid DatosUsuario datosUsuario) {
        var usuario = usuarioService.registrar(datosUsuario);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Lista todos los usuarios.")
    @GetMapping
    public ResponseEntity<Page<DatosListarUsuario>> listadoUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(usuarioRepository.findAll(pageable).map(DatosListarUsuario::new));
    }

    @Operation(summary = "Actualiza un usuario.")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario actualizarDatosUsuario) {
        var usuario = usuarioService.actualizar(id, actualizarDatosUsuario);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Elimina un usuario.")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.validarUsuario(id);
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
