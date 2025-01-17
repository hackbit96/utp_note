package com.utp.note.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utp.note.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClientList<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<T> data;

    public static <T> ResponseClientList<T> setOk(List<T> data) {
        return ResponseClientList.<T>builder()
                .code(Constant.CODE_OK)
                .message(Constant.MSG_OK)
                .data(data)
                .build();
    }

    public static <T> ResponseClientList<T> setOk() {
        return ResponseClientList.<T>builder()
                .code(Constant.CODE_OK)
                .message(Constant.MSG_OK)
                .build();
    }

    public static <T> ResponseClientList<T> setEmpty(String mensaje) {
        return ResponseClientList.<T>builder()
                .code(Constant.CODE_EMPTY)
                .message(mensaje)
                .build();
    }

    public static <T> ResponseClientList<T> setError(String mensaje) {
        return ResponseClientList.<T>builder()
                .code(Constant.CODE_ERROR)
                .message(mensaje)
                .build();
    }


}
