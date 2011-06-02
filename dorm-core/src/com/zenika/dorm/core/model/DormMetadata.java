package com.zenika.dorm.core.model;

import com.zenika.dorm.core.service.DormService;

public final class DormMetadata<T extends MetadataExtension> {

	private String id;
	private String name;
	private String version;
	private String origin;

	private T extension;

	public DormMetadata() {

	}

	public DormMetadata(String name, String version) {
		this.name = name;
		this.version = version;
		this.origin = DormService.ORIGIN;
	}

	public DormMetadata(String name, String version, String origin) {
		this(name, version);
		this.origin = origin;
	}

	public DormMetadata(String id, String name, String version, String origin) {
		this(name, version, origin);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public T getExtension() {
		return extension;
	}

	public void setExtension(T extension) {
		this.extension = extension;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
