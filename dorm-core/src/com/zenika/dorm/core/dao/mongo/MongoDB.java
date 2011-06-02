package com.zenika.dorm.core.dao.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface MongoDB {

    public final static String HOST = "localhost";
    public final static String DATABASE = "dorm";

    public static String ARTIFACTS_COLLECTION = "artifacts";

    DB getDatabase();
    DBCollection getArtifactsCollection();
}
