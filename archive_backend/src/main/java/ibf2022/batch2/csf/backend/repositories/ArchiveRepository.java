package ibf2022.batch2.csf.backend.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

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

		Document toInsert = new Document();
		toInsert.put("bundleId", archive.getBundleId());
		toInsert.put("date", archive.getDate());
		toInsert.put("title", archive.getTitle());
		toInsert.put("name", archive.getName());
		toInsert.put("comments", archive.getComments());
		toInsert.put("urls", archive.getUrls());



		// // convert the Archive object to a JsonObject
		// JsonObject json = Json.createObjectBuilder()
		// 					.add("bundleId", archive.getBundleId())
		// 					.add("date", archive.getDate().toString())
		// 					.add("title", archive.getTitle())
		// 					.add("name", archive.getName())
		// 					.add("comments", archive.getComments())
		// 					.add("urls", jArrBld)
		// 					.build();


		// Document toInsert = Document.parse(json.toString());

		Document insertedResult = mongoTemplate.insert(toInsert, "archives");

		return insertedResult.getObjectId("_id") != null ? true : false;
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	/*
	 * db.archives.find(
	 * 	{'bundleId': 'someid'}
	 * )
	 */
	public List<Document> getBundleByBundleId(String bundleId) {

		Criteria criteria = Criteria.where("bundleId").is(bundleId);
		Query query = Query.query(criteria);
		
		return mongoTemplate.find(query, Document.class, "archives");
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	/*
	 * db.archives.find({}).sort({'date':-1, 'title': 1})
	 */
	public List<Document> getBundles() {

		Query query = Query.query(new Criteria())
							.with(Sort.by(Sort.Direction.DESC, "date")
										.by(Sort.Direction.ASC, "title"));

		List<Document> docs = mongoTemplate.find(query, Document.class, "archives");
		
		return docs;
	}


}
