package com.zenika.dorm.core.model;

import java.util.ArrayList;
import java.util.List;

public final class DormArtifact<T extends MetadataExtension> {

	private DormMetadata<T> metadata;
	private DormFile file;
	private List<DormArtifact<T>> dependencies;
	private List<DormFile> optionnalFiles;

	public DormArtifact() {
		this.dependencies = new ArrayList<DormArtifact<T>>();
		this.optionnalFiles = new ArrayList<DormFile>();
	}

	public DormArtifact(DormMetadata<T> metadata) {
		this();
		this.metadata = metadata;
	}

	public DormArtifact(DormMetadata<T> metadata, DormFile file,
			List<DormArtifact<T>> dependencies, List<DormFile> optionnalFiles) {

		this(metadata);

		this.file = file;
		this.dependencies = dependencies;
		this.optionnalFiles = optionnalFiles;
	}

	public DormMetadata<T> getMetadata() {
		return metadata;
	}

	public void setMetadata(DormMetadata<T> metadata) {
		this.metadata = metadata;
	}

	public DormFile getFile() {
		return file;
	}

	public void setFile(DormFile file) {
		this.file = file;
	}

	public List<DormArtifact<T>> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<DormArtifact<T>> dependencies) {
		this.dependencies = dependencies;
	}

	public List<DormFile> getOptionnalFiles() {
		return optionnalFiles;
	}

	public void setOptionnalFiles(List<DormFile> optionnalFiles) {
		this.optionnalFiles = optionnalFiles;
	}
}
