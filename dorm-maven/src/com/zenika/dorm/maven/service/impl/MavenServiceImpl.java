package com.zenika.dorm.maven.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.helper.MavenArtifactHelper;
import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.service.MavenService;

public class MavenServiceImpl implements MavenService {

    @Inject
    private DormService dormService;

    @Override
    public DormArtifact<DormMavenMetadata> pushArtifact(DormMavenMetadata mavenMetadata, DormFile file) {

        String extension = file.getExtension();

        // maven jar
        if (extension.equalsIgnoreCase("jar")) {
            // TODO: some jar logic
        }

        // maven pom
        else if (extension.equalsIgnoreCase("pom")) {


        } else {
            throw new MavenException("Incorrect file type").type(ArtifactException.Type.NULL);
        }

        // set extension as the maven metadata type
        mavenMetadata.setExtension(extension.toLowerCase());

        DormMetadata<DormMavenMetadata> metadata = MavenArtifactHelper.createDormMetadata(mavenMetadata);

//		DormArtifact<DormMavenMetadata> artifact = DormEcrComponentHelper.getComponent().getDormService()
//				.pushArtifact(metadata, file, null, null);

        return null;
    }

    @Override
    public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata mavenMetadata, String filename) {

        String extension = DormFileHelper.getExtensionFromFilename(filename);
        mavenMetadata.setExtension(extension);

        // get dorm metadata with maven
        DormMetadata<DormMavenMetadata> metadata = MavenArtifactHelper.createDormMetadata(mavenMetadata);

//		return DormEcrComponentHelper.getComponent().getDormService().getArtifact(metadata);
        return null;
    }

    @Override
    public void removeArtifact(DormMavenMetadata mavenMetadata, String filename) {

        String extension = DormFileHelper.getExtensionFromFilename(filename);
        mavenMetadata.setExtension(extension);

        // get dorm metadata with maven
        DormMetadata<DormMavenMetadata> metadata = MavenArtifactHelper.createDormMetadata(mavenMetadata);

//		DormEcrComponentHelper.getComponent().getDormService().removeArtifact(metadata);
    }

    public DormArtifact<DormMavenMetadata> getDependenciesFromPom(DormMavenMetadata pomMetadata) {

        MavenImporterService importerService = new MavenImporterService();
        DormArtifact<DormMavenMetadata> artifact = importerService.getFullArtifact(pomMetadata);

        return artifact;
    }

    private void mapMavenFile(DormMavenMetadata metadata, DormFile file) {

    }

}
