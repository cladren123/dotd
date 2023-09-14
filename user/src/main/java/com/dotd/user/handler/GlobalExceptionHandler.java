package com.dotd.user.handler;


import com.dotd.user.exception.FieldDataException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


/*
전역적으로 예외 처리
컨트롤러에서 발생하는 예외들을 한 곳에서 일관되게 처리할 수 있다.
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 에러 위치 표시 로그
    public void errorSpotLog(Exception ex) {
        // 에러 로그 생성
        StackTraceElement[] stackTrace = ex.getStackTrace();
        String className;
        String methodName;
        for (StackTraceElement element : stackTrace) {
            className = element.getClassName();
            methodName = element.getMethodName();
            if (className.contains(".service.") || className.contains(".controller.")) {
                log.error("에러 발생 위치 : {}.{}", className, methodName);
                break;
            }
        }
        log.error("메시지 : {}", ex.getMessage());
    }


    // 원본
    // value의 커스텀한 에러를 넣는다.
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        // 에러 응답 생성
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode("500");
        errorDetails.setMessage(ex.getMessage());

        Map<String, String> detailsMap = new HashMap<>();
        detailsMap.put("field", "example field");
        detailsMap.put("reason", "example reason");
        errorDetails.setDetails(detailsMap);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(errorDetails);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 필드가 비어있는 예외를 처리하는 메소드
    @ExceptionHandler(value = {FieldDataException.class})
    public ResponseEntity<Object> FieldDataExceptions(FieldDataException ex, WebRequest request) {

        errorSpotLog(ex);

        // 에러 응답 생성
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode("400");
        errorDetails.setMessage("field is Missing");

        Map<String, String> detailsMap = new HashMap<>();


        detailsMap.put("field", ex.getField());
        detailsMap.put("reason", ex.getReason());

        errorDetails.setDetails(detailsMap);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(errorDetails);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    // 에러 응답
    @Data
    public class ErrorResponse {
        private ErrorDetails error;
    }

    // 에러 응답 디테일
    @Data
    public class ErrorDetails {
        private String code;
        private String message;
        private Map<String, String> details;
    }

}
