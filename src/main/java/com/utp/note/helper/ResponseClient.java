package com.utp.note.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utp.note.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClient<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public static <T> ResponseClient<T> setOk(T data) {
        return ResponseClient.<T>builder()
                .code(Constant.CODE_OK)
                .message(Constant.MSG_OK)
                .data(data)
                .build();
    }

    public static <T> ResponseClient<T> setOk() {
        return ResponseClient.<T>builder()
                .code(Constant.CODE_OK)
                .message(Constant.MSG_OK)
                .build();
    }

    public static <T> ResponseClient<T> setEmpty(String mensaje) {
        return ResponseClient.<T>builder()
                .code(Constant.CODE_EMPTY)
                .message(mensaje)
                .build();
    }

    public static <T> ResponseClient<T> setError(String mensaje) {
        return ResponseClient.<T>builder()
                .code(Constant.CODE_ERROR)
                .message(mensaje)
                .build();
    }

    public static <T> ResponseClient<T> setBase(Integer codigo, String mensaje) {
        return ResponseClient.<T>builder()
                .code(codigo)
                .message(mensaje)
                .build();
    }

}
