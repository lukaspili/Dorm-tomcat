package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public interface MavenService {

	public DormArtifact<DormMavenMetadata> pushArtifact(
            DormMavenMetadata mavenMetadata, DormFile file);

	public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata mavenMetadata,
                                                       String filename);

	public void removeArtifact(DormMavenMetadata mavenMetadata, String filename);
}