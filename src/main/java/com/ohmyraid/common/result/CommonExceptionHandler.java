package com.ohmyraid.common.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.LimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CommonExceptionHandler {

        static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

        @ExceptionHandler(CommonException.class)
        public Object handleException(CommonException e, HttpServletRequest request,
                                      HttpServletResponse response) {

            if (e.getCause() != null) {
                logger.error(String.format("Service EXCEPTION[%s] : %s", e.getErrorCode(), e.getErrorMessage()), e);
            } else {
                logger.error(String.format("Service EXCEPTION[%s] : %s", e.getErrorCode(), e.getErrorMessage()));
            }

            HttpStatus status;
            if (e instanceof CommonInvalidInputException) {
                status = HttpStatus.FORBIDDEN;
            } else if (e instanceof CommonPageNotFoundException) {
                status = HttpStatus.NOT_FOUND;
            } else if (e instanceof CommonNoSessionException) {
                status = HttpStatus.UNAUTHORIZED;
            } else if (e instanceof CommonNoAuthenticationException) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                status = HttpStatus.OK;
            }

            ResultView<Object> result = new ResultView<Object>();
            result.setCode(e.getErrorCode());
            result.setMessage(e.getErrorMessage());
            result.setData(e.getResultData());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return handleExceptionInternal(e, result, headers, status, request);

        }


        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Object operation(Exception e, HttpServletRequest request, HttpServletResponse response) {

            logger.error("INTERNAL_SERVER_ERROR", e);

            ResultView<Object> result = new ResultView<Object>();
            result.setCode(ErrorResult.INTERNAL.getResultCode());
            result.setMessage(MessageUtils.getMessage(ErrorResult.INTERNAL.getResultMsg()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return handleExceptionInternal(e, result, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);

        }


        @ExceptionHandler(value = {IOException.class, JsonProcessingException.class, CommonBadRequestException.class})
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Object operateJsonBindingException(Exception e, HttpServletRequest request,
                                                  HttpServletResponse response) {
            if (e instanceof IOException && !(e instanceof JsonProcessingException)
                    && !(e.getCause() instanceof JsonProcessingException)) {
                return operation(e, request, response);
            }

            logger.error("BAD_REQUEST", e);

            ResultView<Object> result = new ResultView<Object>();
            result.setCode(ErrorResult.BAD_REQUEST.getResultCode());
            result.setMessage(MessageUtils.getMessage(ErrorResult.BAD_REQUEST.getResultMsg()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return handleExceptionInternal(e, result, headers, HttpStatus.BAD_REQUEST, request);

        }

        @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class,
                HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
                MissingServletRequestParameterException.class, ServletRequestBindingException.class,
                ConversionNotSupportedException.class, TypeMismatchException.class,
                HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
                MethodArgumentNotValidException.class, MissingServletRequestPartException.class,
                BindException.class, NoHandlerFoundException.class, LimitExceededException.class})
        public final Object handleServletException(Exception ex, HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {

            HttpHeaders headers = new HttpHeaders();
            HttpStatus status;
            Result result = ErrorResult.FAIL;
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                status = HttpStatus.METHOD_NOT_ALLOWED;
            } else if (ex instanceof HttpMediaTypeNotSupportedException) {
                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                status = HttpStatus.NOT_ACCEPTABLE;
            } else if (ex instanceof MissingServletRequestParameterException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof ServletRequestBindingException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof ConversionNotSupportedException) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                result = ErrorResult.INTERNAL;
            } else if (ex instanceof TypeMismatchException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof HttpMessageNotReadableException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof HttpMessageNotWritableException) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                result = ErrorResult.INTERNAL;
            } else if (ex instanceof MethodArgumentNotValidException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof MissingServletRequestPartException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof BindException) {
                status = HttpStatus.BAD_REQUEST;
                result = ErrorResult.BAD_REQUEST;
            } else if (ex instanceof NoHandlerFoundException) {
                status = HttpStatus.NOT_FOUND;
                result = ErrorResult.PAGE_NOT_FOUND;
            } else if (ex instanceof LimitExceededException){
                status = HttpStatus.TOO_MANY_REQUESTS;
                result = ErrorResult.TOO_MANY_REQUEST;
            } else {
                logger.warn("Unknown exception type: " + ex.getClass().getName());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                result = ErrorResult.INTERNAL;
            }

            logger.error("SERVLET_EXCEPTION : " + status.value() + " " + status.getReasonPhrase());

            ResultView<Object> body = new ResultView<Object>();
            body.setCode(result.getResultCode());
            body.setMessage(MessageUtils.getMessage(result.getResultMsg()));

            headers.setContentType(MediaType.APPLICATION_JSON);
            return handleExceptionInternal(ex, body, headers, status, request);

        }


        private ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                               HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
            if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
                request.setAttribute("javax.servlet.error.exception", ex);
            }
            return new ResponseEntity<Object>(body, headers, status);
        }
    }
