package com.alexfetea.awsimageupload.FacialDetection;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FacialDetection {

    public static File detect(MultipartFile file) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("./src/main/resources/haarcascade_frontalface_alt.xml");
        String test = "./src/main/java/com/alexfetea/awsimageupload/image.jpg";
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
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 0, 255), 5);
        }
        Imgcodecs.imwrite(test, image);
        return imageFile;
    }
}
