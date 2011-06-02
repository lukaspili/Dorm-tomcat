package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class ArtifactException extends CoreException {
	
	protected Type type;
	
	public static enum Type {
		FORBIDDEN, NULL, ERROR
	};

	public ArtifactException(String message) {
		super(message);
	}
	
	public ArtifactException type(Type type) {
		this.type = type;
		return this;
	}
	
	public Type getType() {
		return type;
	}

}
