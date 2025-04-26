package com.mallu.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotfoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	long feildValue;
	
	public ResourceNotfoundException(String resourceName, String fieldName, long feildValue) {
		super(String.format("%s not found with %s: %s",resourceName,fieldName,feildValue ));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.feildValue = feildValue;
	}
	
}
