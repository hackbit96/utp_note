package com.utp.note.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    @NotNull(message = "El campo note es requerido")
    private String note;

    @NotNull(message = "El campo content es requerido")
    private String content;

}
