package org.example.pnubookstore.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.s3.S3Uploader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
public class TestController {

    private final S3Uploader s3Uploader;
    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadThumbnail(MultipartFile imageFile) throws IOException {
        String url = s3Uploader.uploadFile(imageFile);
        return ResponseEntity.ok(url);
    }
}

