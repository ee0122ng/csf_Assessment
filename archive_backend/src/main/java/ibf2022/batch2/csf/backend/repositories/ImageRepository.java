package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	public static final String BUCKETNAME = "soonhang-csfassessment";
	public static final String ENDPOINT_URL = "https://soonhang-csfassessment.sgp1.digitaloceanspaces.com";

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public String upload(ByteArrayInputStream imageIS, ObjectMetadata metadata) {


		PutObjectRequest putReq = new PutObjectRequest(BUCKETNAME, "image/%s".formatted(metadata.getUserMetadata().get("fileName")), imageIS, metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

		// TODO: need to set version
        s3.putObject(putReq);
		
		// sample endpoint format: https://soonhang-csfassessment.sgp1.digitaloceanspaces.com/myObjectc1746282.blob.png
		String fileUrl = "https;//" + BUCKETNAME + ".sgp1.digitaloceanspaces.com/" + metadata.getUserMetadata().get("fileName");
		
		return fileUrl;
	}
}
