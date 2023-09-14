package com.dotd.user.exception;


/*

데이터가 들어오지 않을 때 처리하는 에러 처리

 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDataException extends RuntimeException{

    private String field;
    private String reason;

}
