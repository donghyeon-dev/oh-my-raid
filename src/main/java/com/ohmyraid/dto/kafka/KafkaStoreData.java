package com.ohmyraid.dto.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class KafkaStoreData<T> {

    @ApiModelProperty(value = "메소드명", example = "getChractersByAccount")
    private String methodName;

    @ApiModelProperty(value = "메소드 파라미터")
    private  T targetParameter;

    @ApiModelProperty(value = "파라미터 클래스")
    private Class<?> parameterTargetClass;
}
