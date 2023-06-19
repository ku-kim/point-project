package com.github.kukim.point.api.common;

import com.github.kukim.point.core.exception.PointApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Throwable.class})
	protected CustomApiResponse<Void> handle(Throwable throwable) {
		log.error("[UnknownException] Occur exception.", throwable);
		return CustomApiResponseGenerator.FAILURE;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({PointApplicationException.class})
	protected CustomApiResponse<Void> handle(PointApplicationException applicationException) {
		log.error("[PointApplicationException] exception.", applicationException);
		return CustomApiResponseGenerator.of(applicationException.getCode(), applicationException.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
		MethodArgumentNotValidException.class,
		MissingRequestHeaderException.class,
		HttpMessageNotReadableException.class
	})
	public CustomApiResponse<Void> handleMethodArgumentNotValidException(Exception exception) {
		log.error("[BadRequest] exception", exception);
		return CustomApiResponseGenerator.of("S001", "요청 값 확인 필요");
	}
}
