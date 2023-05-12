package ibf2022.batch2.csf.backend.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	/*
	 * db.archives.update({
	 * 	'bundleId': 'someid',
	 * 	'date': 'somedate',
	 * 	'title': 'sometitle',
	 * 	'name': 'somename',
	 * 	'comments': 'somecomments',
	 * },
	 * 	{$push : {'urls': 'someurl'}},
	 * 	{upsert: true}
	 * )
	 * 
	 */
	public Boolean recordBundle(Archive archive) {

		// convert list or urls to JsonArray
		JsonArrayBuilder jArrBld = Json.createArrayBuilder();

		for (String url : archive.getUrls()) {
			jArrBld.add(url);
		}

		// convert the Archive object to a JsonObject
		JsonObject json = Json.createObjectBuilder()
							.add("bundleId", archive.getBundleId())
							.add("date", archive.getDate().toString())
							.add("title", archive.getTitle())
							.add("name", archive.getName())
							.add("comments", archive.getComments())
							.add("urls", jArrBld)
							.build();

		Document toInsert = Document.parse(json.toString());

		Document insertedResult = mongoTemplate.insert(toInsert, "archives");

		return insertedResult.getObjectId("_id") != null ? true : false;
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundleByBundleId(/* any number of parameters here */) {
		return null;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
