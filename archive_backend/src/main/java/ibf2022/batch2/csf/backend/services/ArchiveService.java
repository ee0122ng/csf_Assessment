package ibf2022.batch2.csf.backend.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.bson.Document;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.exception.ResourceNotFoundException;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveRepository archiveRepo;

    public JsonObject getArchiveByBundleId(String bundleId) {
        
        List<Document> docs = this.archiveRepo.getBundleByBundleId(bundleId);

        if (docs.isEmpty()) {

            throw new ResourceNotFoundException("%s not found in the database".formatted(bundleId));

        } else {

            Document doc = docs.get(0);

            JsonObject json = this.convertDocToJson(doc);

            return json;

        }
        
    }

    public JsonArray getArchiveBundles() {

        List<Document> docs = this.archiveRepo.getBundles();

        if (docs.isEmpty()) {

            throw new ResourceNotFoundException("Storage is empty");

        }

        JsonArrayBuilder jArrBld = Json.createArrayBuilder();

        for(Document d : docs) {
            JsonObject j = this.convertDocToJson(d);
            jArrBld.add(j);
        }

        JsonArray jArr = jArrBld.build();

        return jArr;
        
    }

    public JsonObject convertDocToJson(Document doc) {

        JsonArrayBuilder jArrBld = Json.createArrayBuilder();
        List<String> urls = doc.getList("urls", String.class);
        for(String u : urls) {
            jArrBld.add(u);
        }

        LocalDateTime d = doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        JsonObject json = Json.createObjectBuilder()
                            .add("bundleId", doc.getString("bundleId"))
                            .add("uploadDate", d.toString())
                            .add("title", doc.getString("title"))
                            .add("name", doc.getString("name"))
                            .add("comments", doc.getString("comments"))
                            .add("urls", jArrBld)
                            .build();

        return json;

    }
    
}
