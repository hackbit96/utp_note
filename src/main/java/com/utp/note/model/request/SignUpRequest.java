package com.utp.note.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotNull(message = "El campo nombre es requerido")
    private String firstName;

    @NotNull(message = "El campo apellido es requerido")
    private String lastName;

    @NotNull(message = "El campo correo electrónico es requerido")
    private String email;

    @NotNull(message = "El campo contraseña es requerido")
    private String password;
}
