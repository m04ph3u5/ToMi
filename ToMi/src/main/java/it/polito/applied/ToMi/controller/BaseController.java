package it.polito.applied.ToMi.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mongodb.MongoException;

import it.polito.applied.ToMi.exception.BadRequestException;
import it.polito.applied.ToMi.exception.ErrorInfo;
import it.polito.applied.ToMi.exception.ForbiddenException;
import it.polito.applied.ToMi.exception.NotFoundException;

public abstract class BaseController {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorInfo handleBadRequestException(BadRequestException e){
		ErrorInfo error = new ErrorInfo();
		error.setMessage(e.getMessage());
		error.setStatusCode("400");
		writeLog(e.getMessage(),"400");

		/*DA INSERIRE URL*/
		return error;
	}
	
	
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorInfo handleForbiddenException(ForbiddenException e){
		ErrorInfo error = new ErrorInfo();
		error.setMessage(e.getMessage());
		error.setStatusCode("403");
		writeLog(e.getMessage(),"403");

		/*DA INSERIRE URL*/
		return error;
	}
	
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorInfo handleNotFoundException(NotFoundException e){
		ErrorInfo error = new ErrorInfo();
		error.setMessage(e.getMessage());
		error.setStatusCode("404");
		/*DA INSERIRE URL*/		writeLog(e.getMessage(),"404");

		return error;
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorInfo handleBadCredentialsException(BadCredentialsException e){
		ErrorInfo error = new ErrorInfo();
		error.setMessage(e.getMessage());
		error.setStatusCode("403");
		/*DA INSERIRE URL*/		writeLog(e.getMessage(),"403");

		return error;
	}
	
	@ExceptionHandler(MongoException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handleMongoException(MongoException e){
		ErrorInfo error = new ErrorInfo();
		error.setMessage("Internal server error: "+e.getMessage());
		error.setStatusCode("500");
		writeLog(e.getMessage(),"500");
		return error;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorInfo handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("400");
		System.out.println("HttpMessageNotReadableException: "+e.getMessage());
		writeLog(e.getMessage(),"400");
		return error;
	}
	
	
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ErrorInfo handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("400");
		System.out.println("HttpMediaTypeNotAcceptableException: "+e.getMessage());
		error.setMessage(e.getMessage());
		writeLog(e.getMessage(),"400");

		return error;
	}
	
	
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorInfo handleAccessDeniedException(AccessDeniedException e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("403");
		System.out.println("FORBIDDEN");
		writeLog(e.getMessage(),"403");

		return error;
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorInfo handleFileNotFoundException(FileNotFoundException e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("404");
		System.out.println("File not found"+e.getMessage());
		writeLog(e.getMessage(),"404");
		return error;
	}
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorInfo handleIOException(IOException e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("400");
		System.out.println("io exception: "+e.getMessage());
		writeLog(e.getMessage(),"400");

		return error;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorInfo handleGenericException(Exception e){
		ErrorInfo error = new ErrorInfo();
		error.setStatusCode("400");
		System.out.println("generic exception: "+e.getClass()+" "+e.getMessage());
		writeLog(e.getMessage(),"400");
		return error;
	}
	
	private void writeLog(String message, String statusCode){
		System.err.println("EXCEPTION: "+statusCode+" "+message);
	}
}
