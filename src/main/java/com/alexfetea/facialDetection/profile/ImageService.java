package com.alexfetea.facialDetection.profile;

import com.amazonaws.util.IOUtils;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import java.io.*;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class ImageService {

    private final ImageDataAccessService imageDataAccessService;

    @Autowired
    public ImageService(ImageDataAccessService userProfileDataAccessService) {
        this.imageDataAccessService = userProfileDataAccessService;
    }

    ImageEntry getLatestImage() {
        return imageDataAccessService.getLatestImage();
    }

    public void uploadImage(UUID imageId, MultipartFile file) throws IOException {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        ImageEntry user = imageDataAccessService.getImage(imageId);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String filename = String.format(file.getOriginalFilename());

        File imageFile = facialDetection(file);

        InputStream inputStream =  new FileInputStream(imageFile);

        imageDataAccessService.addToImage(imageId, filename, inputStream);
        user.setImage(inputStream);
    }
    public byte[] downloadImage(UUID userProfileId) {
        InputStream inputStream = (imageDataAccessService.getImage(userProfileId)).getImage();
        if(inputStream==null) {
            return new byte[0];
        }
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

    private File facialDetection(MultipartFile file){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("./src/main/resources/haarcascade_frontalface_alt.xml");
        String test = "./src/main/java/com/alexfetea/facialDetection/image.jpg";
        File imageFile = new File(test);

        try (OutputStream os = new FileOutputStream(imageFile)) {
            os.write(file.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mat image = Imgcodecs.imread(test);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        for (
                Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, rect, new Scalar(0, 0, 255), 5);
        }
        Imgcodecs.imwrite(test, image);
        return imageFile;
    }

}
