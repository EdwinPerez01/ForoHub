package com.alura.ForoHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank @Email String correo,
        @NotBlank String contrasena) {
}

