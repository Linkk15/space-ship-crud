package spaceships.crud.infrastructure.adapters.rest.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import spaceships.crud.domain.enums.SpaceShipErrorLevelEnum;
import spaceships.crud.domain.exceptions.*;
import spaceships.crud.domain.models.SpaceShipError;

@ControllerAdvice(basePackages = "spaceships.crud.infrastructure.adapters.rest.controller")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Slf4j
public class SpaceShipControllerAdvice {

  private static final String ERROR = "ERROR";

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = BAD_REQUEST)
  public ResponseEntity<SpaceShipError> badRequestException(
      HttpMessageNotReadableException ex, WebRequest request) {
    log.trace(ex.getMessage(), ERROR);
    return error(
        BAD_REQUEST, SpaceShipErrorLevelEnum.ERROR, ex.getMessage(), request.getDescription(false));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = INTERNAL_SERVER_ERROR)
  public ResponseEntity<SpaceShipError> genericErrorHandler(
      Exception exception, WebRequest request) {
    log.trace(exception.getMessage(), ERROR);
    return error(
        INTERNAL_SERVER_ERROR,
        SpaceShipErrorLevelEnum.CRITICAL,
        exception.getMessage(),
        request.getDescription(false));
  }

  @ExceptionHandler(TimeoutException.class)
  @ResponseStatus(value = REQUEST_TIMEOUT)
  public ResponseEntity<SpaceShipError> timeoutExceptionHandler(
      TimeoutException exception, WebRequest request) {
    log.trace(exception.getMessage(), ERROR);
    return error(
        REQUEST_TIMEOUT,
        SpaceShipErrorLevelEnum.ERROR,
        exception.getMessage(),
        request.getDescription(false));
  }

  @ExceptionHandler({DataIntegrityViolationException.class, SpaceShipDataBaseErrorException.class})
  public ResponseEntity<SpaceShipError> dataIntegrityViolationExceptionHandler(
      DataIntegrityViolationException exception) {
    return error(
        INTERNAL_SERVER_ERROR,
        SpaceShipErrorLevelEnum.ERROR,
        "Data Integrity error",
        ExceptionUtils.getRootCause(exception).getMessage());
  }

  @ExceptionHandler(value = {RuntimeException.class, SpaceShipInternalServerException.class})
  public ResponseEntity<SpaceShipError> unknownExceptionHandler(RuntimeException exception) {
    return error(
        INTERNAL_SERVER_ERROR,
        SpaceShipErrorLevelEnum.CRITICAL,
        "Unknown exception",
        exception.getMessage());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(value = BAD_REQUEST)
  public ResponseEntity<SpaceShipError> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException ex) {
    final var errorBuilder = SpaceShipError.builder();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            objectError -> {
              var objectName = objectError.getObjectName();

              if (objectError instanceof FieldError fieldError) {
                objectName = fieldError.getField();
              }

              SpaceShipError.builder()
                  .code(objectError.getCode())
                  .message(objectError.getDefaultMessage())
                  .level(SpaceShipErrorLevelEnum.CRITICAL)
                  .description(objectName)
                  .build();
            });
    return new ResponseEntity<>(errorBuilder.build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SpaceShipNotFoundException.class)
  public ResponseEntity<SpaceShipError> spaceShipNotFoundExceptionHandler(
      SpaceShipNotFoundException exception) {
    return error(
        NOT_FOUND, SpaceShipErrorLevelEnum.ERROR, "SpaceShip not found", exception.getMessage());
  }

  @ExceptionHandler(SpaceShipExistsException.class)
  public ResponseEntity<SpaceShipError> spaceShipExistsExceptionHandler(
      SpaceShipNotFoundException exception) {
    return error(
        NOT_FOUND,
        SpaceShipErrorLevelEnum.ERROR,
        "SpaceShip already exists",
        exception.getMessage());
  }

  private ResponseEntity<SpaceShipError> error(
      HttpStatus code, SpaceShipErrorLevelEnum level, String message, String description) {
    return new ResponseEntity<>(
        SpaceShipError.builder()
            .code(code.name())
            .message(message)
            .level(level)
            .description(description)
            .build(),
        code);
  }
}
