package com.alura.ForoHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {

    private String titulo;
    private String mensaje;
    private String status;
}
