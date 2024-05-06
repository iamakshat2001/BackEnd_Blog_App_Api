package com.blog.app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourseNotFoundException extends RuntimeException{

	String resourseName;
	String fieldName;
	Object fieldValue;

	public ResourseNotFoundException(String resourseName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s",resourseName,fieldName, fieldValue));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}








}
