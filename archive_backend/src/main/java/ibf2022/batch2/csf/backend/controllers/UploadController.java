package ibf2022.batch2.csf.backend.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.services.ArchiveService;
import ibf2022.batch2.csf.backend.services.ImageService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Controller
@RequestMapping()
@CrossOrigin(origins = "*")
public class UploadController {

	@Autowired
	private ImageService imageSvc;

	@Autowired
	private ArchiveService archiveSvc;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path={"/upload"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<String> uploadImageZipFile(@RequestPart MultipartFile image, @RequestPart String name, @RequestPart String title, @RequestPart String comments, @RequestPart String uploadDate) {
		
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY, hh:mm:ss");
			Date upDate = formatter.parse(uploadDate);

			Archive archive = this.imageSvc.uploadImageToS3(image, name, title, comments, upDate);

			JsonObject payload = Json.createObjectBuilder().add("bundleId", archive.getBundleId()).build();
			
			return ResponseEntity.status(HttpStatus.OK).body(payload.toString());


        } catch (Exception ex) {

			JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());

        }

	}
	

	// TODO: Task 5
	@GetMapping(path={"/bundle/{bundleId}"})
	@ResponseBody
	public ResponseEntity<String> getArchiveDocument(@PathVariable String bundleId) {

		try {

			JsonObject payload = this.archiveSvc.getArchiveByBundleId(bundleId);

			return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

		} catch (Exception ex) {

			JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
		}
		

	}
	

	// TODO: Task 6
	@GetMapping(path={"/bundles"})
	@ResponseBody
	public ResponseEntity<String> getBundles() {

		try {

			JsonArray payload = this.archiveSvc.getArchiveBundles();
		
			return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

		} catch (Exception ex) {

			JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
		}

	}

}
