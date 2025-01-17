package com.utp.note.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {

    @JsonProperty("codigo")
    private Integer codigo;

    @JsonProperty("mensaje")
    private String mensaje;

}
