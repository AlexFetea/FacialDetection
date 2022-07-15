package com.alexfetea.facialDetection.datastore;

import com.alexfetea.facialDetection.profile.ImageEntry;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ImageDataStore {

    private static final List<ImageEntry> IMAGE_ENTRIES = new ArrayList<>();

    static {
        IMAGE_ENTRIES.add(new ImageEntry(UUID.fromString("2aeb885d-8630-4880-b4a3-e83aaa8f6a80"), null));
    }

    public ImageEntry getLatestImage() {
        return IMAGE_ENTRIES.get(IMAGE_ENTRIES.size()-1);
    }

    public void addToImage( UUID imageId, String FileName, InputStream inputStream){
        IMAGE_ENTRIES.add(new ImageEntry(UUID.randomUUID(), inputStream));

    }
    public ImageEntry getUserProfile(UUID userProfileId){
        for(ImageEntry imageEntry : IMAGE_ENTRIES){
            if(imageEntry.getImageId().equals(userProfileId))
                return imageEntry;
        }
        return null;
    }


}
