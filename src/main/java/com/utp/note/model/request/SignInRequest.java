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
public class SignInRequest {

    @NotNull(message = "El campo correo electrónico es requerido")
    private String email;

    @NotNull(message = "El campo contraseña es requerido")
    private String password;

}
