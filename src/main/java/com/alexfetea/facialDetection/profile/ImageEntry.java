package com.alexfetea.facialDetection.profile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public class ImageEntry {

    private final UUID imageId;
    private InputStream inputStream;

    public ImageEntry(UUID imageId, InputStream image) {
        this.imageId = imageId;
        this.inputStream = image;
    }

    public UUID getImageId() {
        return imageId;
    }

    public InputStream getImage() {
        return inputStream;
    }

    public void setImage(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntry that = (ImageEntry) o;
        return Objects.equals(imageId, that.imageId) &&
                Objects.equals(inputStream, that.inputStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, inputStream);
    }
}
