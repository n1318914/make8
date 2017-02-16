package com.yundaren.filter.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springside.modules.web.MediaTypes;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = RuntimeException.class)
	public final ResponseEntity<?> handleRuntimeException(Exception ex, WebRequest request) {
//		log.error("cloud error message.", ex);
		throw new RuntimeException("cloud error message.", ex);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
//		ErrorJson errorJson = new ErrorJson();
//		errorJson.setErrCode(500);
//		errorJson.setErrMsg("系统运行异常");
//		return handleExceptionInternal(ex, errorJson, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
//		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<?> handleAllException(Exception ex, WebRequest request) {
		return handleRuntimeException(ex, request);
	}

	@Data
	private static class ErrorJson {
		private static final long serialVersionUID = 7500089189602336545L;
		private int errCode;
		private String errMsg;

		public String toString() {
			StringBuffer strBf = new StringBuffer();
			strBf.append(errCode);
			strBf.append(":");
			strBf.append(errMsg);
			return strBf.toString();
		}
	}
}
