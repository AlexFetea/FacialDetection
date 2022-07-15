package com.alexfetea.facialDetection.profile;

import com.alexfetea.facialDetection.datastore.ImageDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.UUID;

@Repository
public class ImageDataAccessService {

    private final ImageDataStore imageDataStore;

    @Autowired
    public ImageDataAccessService(ImageDataStore imageDataStore) {
        this.imageDataStore = imageDataStore;
    }

    ImageEntry getLatestImage() {
        return imageDataStore.getLatestImage();
    }
    public ImageEntry getImage(UUID userProfileId) {
        return imageDataStore.getUserProfile(userProfileId);
    }
    public void addToImage(UUID userProfileId, String fileName, InputStream inputStream){
        imageDataStore.addToImage(userProfileId, fileName, inputStream);
    }
}
