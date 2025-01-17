package com.utp.note.constant;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    public static final Integer CODE_OK = 0;
    public static final Integer CODE_EMPTY = 1;
    public static final Integer CODE_ERROR = -1;
    public static final String MSG_OK = "OK";
    public static final String AUTHORIZATION_BEARER = "Bearer ";
    public static final Integer JWT_TOKEN = 7;
    public static final String MSG_NOT_FOUND = "El recurso solicitado no fue encontrado - URL: %s";
    public static final String MSG_INTERNAL_SERVER_ERROR = "Ha ocurrido un error inesperado.";
    public static final String MSG_FORBIDDEN = "Acceso denegado: No tienes permisos para esta acción.";
    public static final String MSG_UNAUTHORIZED = "No estás autenticado. Por favor, inicia sesión.";
    public static final String MSG_USER_EXISTS = "La cuenta ya existe con este correo: %s";
    public static final String MSG_USER_NO_EXISTS = "Usuario no encontrado con el correo proporcionado.";
    public static final String MSG_INCORRECT_CREDENTIALS = "Credenciales incorrectas. Por favor, verifica tu correo y contraseña.";
    public static final String MSG_LOGIN_ERROR = "Ocurrió un error durante el inicio de sesión. Por favor, intenta nuevamente.";
    public static final String MSG_SAVE_NOTE = "Ocurrió un error al guardar la nota.";
    public static final String MSG_GET_NOTE = "Ocurrió un error al obtener notas.";
    public static final String MSG_GET_NOTE_EMPTY = "No se encontraron resultados.";


}
