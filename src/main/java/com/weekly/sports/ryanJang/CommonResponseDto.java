package com.weekly.sports.ryanJang;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Json으로 팔싱할 때 NON NULL이 있으면 NULL인 것들을 뺄 수 있다
public class CommonResponseDto {
    private String msg;
    private Integer statusCode;
}
