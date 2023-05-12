package ibf2022.batch2.csf.backend.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;

import ibf2022.batch2.csf.backend.exception.ArchiveFailException;
import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired  
    private ImageRepository imageRepo;

    @Autowired
    private ArchiveRepository archiveRepo;

    @Transactional
    public Archive uploadImageToS3(MultipartFile image, String name, String title, String comments, Date uploadDate) throws IOException {

        // unzip files then upload the contents
        ZipInputStream isZip = new ZipInputStream(image.getInputStream());
        ZipEntry zipEntry = isZip.getNextEntry();
        List<String> urlList = new LinkedList<>();

        while (null != zipEntry) {

            // generate metadata
            ObjectMetadata metadata = this.generateMetadata(zipEntry);

            // convert zipentry to input stream
            byte[] imageBytes = IOUtils.toByteArray(isZip);
            ByteArrayInputStream imageIS = new ByteArrayInputStream(imageBytes);

            String fileUrl = this.imageRepo.upload(imageIS, metadata);
            urlList.add(fileUrl);
            
            zipEntry = isZip.getNextEntry();
            
        }

        // close the zip file after reading
        isZip.close();

        Archive archive = this.generateArchiveObject(uploadDate, title, name, comments, urlList);

        // archive a document to MongoDB
        Boolean result = this.archiveRepo.recordBundle(archive);
        
        if (result) {

            return archive;

        } else {
            
            throw new ArchiveFailException("Archive failed");

        }

    }

    public Archive generateArchiveObject(Date uploadDate, String title, String name, String comments, List<String> urls) {

        Archive archive = new Archive();

        String id = UUID.randomUUID().toString().substring(0, 8);

        archive.setBundleId(id);
        archive.setComments(comments);
        archive.setDate(uploadDate);
        archive.setName(name);
        archive.setTitle(title);
        archive.setUrls(urls);

        return archive;
    }

    public ObjectMetadata generateMetadata(ZipEntry entry) {

        ObjectMetadata metadata = new ObjectMetadata();

        // get file extension
        String fileName = entry.getName();
        String[] fileNameArr = fileName.split("\\.");
        String ext = fileNameArr[fileNameArr.length-1];

        // set usermetadata for file name
        Map<String, String> userData = new HashMap<>();
        userData.put("fileName", fileName);

        // set metadata
        metadata.setContentType("image" + ext);
        metadata.setContentLength(entry.getSize());
        metadata.setUserMetadata(userData);

        return metadata;
    }
    
}
