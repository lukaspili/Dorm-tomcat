package com.zenika.dorm.core.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.lang.reflect.Field;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MongoMapping {

    public static <T extends MetadataExtension> BasicDBObject getDocumentFromMetadata(DormMetadata<T> metadata) {

        BasicDBObject document = new BasicDBObject();
        document.put("name", metadata.getName());
        document.put("version", metadata.getVersion());
        document.put("origin", metadata.getOrigin());

        return document;
    }

    public static <T extends MetadataExtension> BasicDBObject getDocumentFromArtifact(DormArtifact<T> artifact) {

        BasicDBObject document = getDocumentFromMetadata(artifact.getMetadata());

        try {
            mapSpecificMetadata(artifact.getMetadata().getExtension(), document);
        } catch (IllegalAccessException e) {
            throw new CoreException("Cannot map specific metadata by reflection", e);
        }

        return document;
    }

    public static <T extends MetadataExtension> void mapSpecificMetadata(T metadata, BasicDBObject object) throws IllegalAccessException {

        if (null == metadata) {
            return;
        }

        Class<? extends MetadataExtension> reflect = metadata.getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            // TODO: Add required field checking, by annotation for example ?

            // get the field value
            Object value = field.get(metadata);

            // file metadata
            if (Field.class.equals(field.getType()) && null != value) {

                //TODO: Add file
            } else {
                object.put(field.getName(), value);
            }
        }
    }

    public static <T extends MetadataExtension> DormArtifact<T> getArtifactFromDocument(DBObject document) {

        // create the new dorm artifact that will contain the newly mapped
        // metadata
        DormArtifact<T> artifact = new DormArtifact<T>();

        //DormMetadata<T> metadata = new DormMetadata<T>();

        // create the new dorm metadata that will be mapped with the document
        // model
        DormMetadata<T> metadata = mapDocumentToDormMetadata(document);
        artifact.setMetadata(metadata);

        DormFile file = mapDocumentModelToDormFile(document);
        artifact.setFile(file);

        // create and map metadata extension if exists
        if (null != metadata.getExtension()) {
            try {
                // get the mapped metadata extension
                T metadataExtension = mapDocumentModelToMetadataExtension(
                        document, metadata);

                // and set it to the mapped metadata
                metadata.setExtension(metadataExtension);

            } catch (Exception e) {
                throw new CoreException(
                        "Cannot map from ECR document model to metadata extension");
            }
        }

        return artifact;
    }

    protected static <T extends MetadataExtension> DormMetadata<T> mapDocumentToDormMetadata(
            DBObject document) {

        DormMetadata<T> mappedMetadata = new DormMetadata<T>();

        try {
            // dorm schema mapping
            mappedMetadata.setId(document.get("_id").toString());
            mappedMetadata
                    .setName(document.get("name").toString());
            mappedMetadata.setVersion(document.get("version")
                    .toString());
            mappedMetadata.setOrigin(document.get("origin")
                    .toString());

        } catch (NullPointerException e) {
            throw new CoreException(
                    "Empty requiered informations in the ECR document model");
        }

        return mappedMetadata;
    }

    /**
     * Get the DormFile from the document model dorm schema
     */
    protected static DormFile mapDocumentModelToDormFile(DBObject document) {

        DormFile dormFile = new DormFile();

        return dormFile;
    }

    protected static <T extends MetadataExtension> T mapDocumentModelToMetadataExtension(
            DBObject document, DormMetadata<T> metadata)
            throws IllegalArgumentException, IllegalAccessException {

        T metadataExtension;

        try {
            // TODO: Correct this if possible ?
            @SuppressWarnings("unchecked")
            Class<T> extensionClass = (Class<T>) metadata.getExtension()
                    .getClass();

            // create new instance of metadata extension by reflection
            metadataExtension = extensionClass.newInstance();
        } catch (Exception e) {
            throw new CoreException(
                    "Cannot create new instance of metadata extension");
        }

        Class<? extends MetadataExtension> reflect = metadataExtension
                .getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            // get the property matching the field's name
            Object property = document.get(field.getName());

            // and set it in the metadata
            field.set(metadataExtension, property);
        }

        return metadataExtension;
    }
}
