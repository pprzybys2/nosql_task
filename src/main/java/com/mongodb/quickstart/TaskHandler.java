package com.mongodb.quickstart;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;

public class TaskHandler {
    MongoCollection<Document> tasks;

    public TaskHandler(MongoCollection<Document> tasks) {
        this.tasks = tasks;
    }

    void displayAllTasks(){
        FindIterable<Document> iterDoc = tasks.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    public void displayOverdueTasks() {
        FindIterable<Document> iterDoc = tasks.find(lt("deadline", new Date()));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    void displayTaskByCategory(String category ){
        Document doc = tasks.find(eq("category", category)).first();
        if (doc == null){
            System.out.println("No results found.");
        } else {
            System.out.println(doc.toJson());
        }
    }

    void insertTask(Document document){
        tasks.insertOne(document);
    }


    public void searchByWordInDescription(String word) {
        Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        Bson filter = Filters.regex("description", pattern);
        FindIterable<Document> iterDoc = tasks.find(filter);
        Iterator it = iterDoc.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    public void searchBySubtaskName(String name) {
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        Bson filter = Filters.regex("subtask", pattern);
        FindIterable<Document> iterDoc = tasks.find(filter);
        Iterator it = iterDoc.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    public void displayAllSubtasksRelatedToSpecificCategor(String category) {
        //TODO
    }
}
