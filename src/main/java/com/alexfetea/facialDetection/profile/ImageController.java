package com.alexfetea.facialDetection.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/image-entry")
@CrossOrigin("*")
public class ImageController {

    private final ImageService ImageService;

    @Autowired
    public ImageController(ImageService userProfileService) {
        this.ImageService = userProfileService;
    }

    @GetMapping
    public List<ImageEntry> getLatestImage() {
        return Arrays.asList(ImageService.getLatestImage());
    }

    @PostMapping(
            path = "{imageId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadImage(@PathVariable("imageId") UUID imageId,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        ImageService.uploadImage(imageId, file);
    }

    @GetMapping("{imageId}/image/download")
    public byte[] downloadImage(@PathVariable("imageId") UUID imageId){
        return ImageService.downloadImage(imageId);
    }

}
