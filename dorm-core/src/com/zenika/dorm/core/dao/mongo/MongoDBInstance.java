package com.zenika.dorm.core.dao.mongo;

import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.zenika.dorm.core.exception.RepositoryException;

import java.net.UnknownHostException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public class MongoDBInstance implements MongoDB {

    private Mongo mongo;
    private DB db;

    public MongoDBInstance() {

        try {
            mongo = new Mongo("localhost");
        } catch (UnknownHostException e) {
            throw new RepositoryException("Unable to connect to mongo db at " + HOST);
        }

        db = mongo.getDB(DATABASE);
    }

    @Override
    public DB getDatabase() {
        return db;
    }

    @Override
    public DBCollection getArtifactsCollection() {
        return db.getCollection(ARTIFACTS_COLLECTION);
    }
}