package mongo;

import com.mongodb.*;
import data.ResultEntry;

import java.util.List;

public class MongoCreator {

    public void storeResultsInMongoDb(List<ResultEntry> dataSubjectIdsNumberPerUser) {
        final MongoClient mongoClient = new MongoClient("localhost", 27017);
        final DB database = mongoClient.getDB("myMongoDb");
        database.createCollection("results", null);

        final DBCollection collection = database.getCollection("results");
        final BasicDBObject document = new BasicDBObject();

        insertData(document, dataSubjectIdsNumberPerUser);
        collection.insert(document);
    }

    private void insertData(BasicDBObject document, List<ResultEntry> dataSubjectIdsNumberPerUser) {
        dataSubjectIdsNumberPerUser.forEach(element -> {
            document.put("viewerID", element.getViewerID());
            document.put("dataSubjectIds", element.getDataSubjectIds());
        });
    }
}