package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.DormService;

import java.util.ArrayList;
import java.util.List;

public class DormServiceImpl implements DormService {

    @Inject
    private DormDao dao;

    @Override
    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(DormMetadata<T> metadata, DormFile file,
                                                                      List<DormArtifact<T>> dependencies, List<DormFile> optionnalFiles) {

        // make some validation
        // TODO: externalize this process, with annotation system for example
        if (null == metadata.getName() || metadata.getName().trim().isEmpty() || null == metadata.getVersion()
                || metadata.getVersion().trim().isEmpty() || null == metadata.getOrigin()
                || metadata.getOrigin().trim().isEmpty()) {

            throw new ArtifactException("Missing dorm params").type(ArtifactException.Type.NULL);
        }

        if (null == dependencies) {
            dependencies = new ArrayList<DormArtifact<T>>();
        }

        DormArtifact<T> artifact = new DormArtifact<T>(metadata, file, dependencies, optionnalFiles);

        artifact = dao.create(artifact);

        return artifact;
    }

    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(DormArtifact<T> artifact) {


        return artifact;
    }

    @Override
    public <T extends MetadataExtension> DormArtifact<T> getArtifact(DormMetadata<T> metadata) {

        DormArtifact<T> artifact = dao.getByMetadata(metadata);

        return artifact;
    }

    @Override
    public <T extends MetadataExtension> void removeArtifact(DormMetadata<T> metadata) {

        dao.removeByMetadata(metadata);
    }
}