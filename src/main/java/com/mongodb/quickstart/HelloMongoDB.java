package com.mongodb.quickstart;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloMongoDB {

    public static void main(String[] args) {

        String connectionString = "mongodb://localhost:27017";
        String dbName = "task_management";
        String tasksCollection = "tasks";
        String category = "category1";
        int taskNumber = 2;

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> tasks = database.getCollection(tasksCollection);
            TaskHandler handler = new TaskHandler(tasks);

            switch (taskNumber) {
                case 1:
                    handler.displayAllTasks();
                    break;
                case 2:
                    handler.displayOverdueTasks();
                    break;
                case 3:
                    handler.displayTaskByCategory(category);
                    break;
                case 4:
                    handler.displayAllSubtasksRelatedToSpecificCategor(category);
                    break;
                case 5:
                    Document document = createSimpleTask();
                    handler.insertTask(document);
                    break;
                case 6:
                    break;
                case 7:
                    handler.searchByWordInDescription("milk");
                    break;
                case 8:
                    handler.searchBySubtaskName("sub3");
                    break;
            }
        }
    }

    private static Document createSimpleTask() {
        Document document = new Document();
        document.append("name", "shopping");
        document.append("description", "buy car");
        document.append("category", "category3");

        document.append("date_of_creation", new Date());
        document.append("deadline", new Date(1644698565L));

        BasicDBList subtasks = new BasicDBList();
        BasicDBObject firstSubtask = new BasicDBObject();
        firstSubtask.put("name", "sub3");
        firstSubtask.put("description", "desc3");

        BasicDBObject secondSubtask = new BasicDBObject();
        secondSubtask.put("name", "sub4");
        secondSubtask.put("description", "desc4");

        subtasks.add(firstSubtask);
        subtasks.add(secondSubtask);

        document.append("subtasks", subtasks);
        return document;
    }
}
