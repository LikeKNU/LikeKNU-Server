package com.woopaca.univclub.resource.storage.s3;

import com.woopaca.univclub.config.AWSConfiguration;
import com.woopaca.univclub.resource.ImageResource;
import com.woopaca.univclub.resource.storage.AsyncResourceStorage;
import com.woopaca.univclub.resource.storage.ResourceIdentifier;
import com.woopaca.univclub.resource.storage.ResourceKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Primary
@Profile("!local")
@Component
public class S3ResourceStorage extends AsyncResourceStorage {

    private final S3Client s3Client;
    private final AWSConfiguration awsConfiguration;

    public S3ResourceStorage(S3Client s3Client, AWSConfiguration awsConfiguration) {
        this.s3Client = s3Client;
        this.awsConfiguration = awsConfiguration;
    }

    @Override
    public String store(ImageResource resource, ResourceKeyGenerator resourceKeyGenerator) {
        InputStream inputStream = resource.getInputStream();
        String key = resourceKeyGenerator.generateKey(resource);
        try (resource) {
            PutObjectRequest request = generateUploadRequest(key, resource.getMediaType());
            RequestBody requestBody = RequestBody.fromInputStream(inputStream, inputStream.available());
            s3Client.putObject(request, requestBody);
        } catch (IOException e) {
            throw new IllegalArgumentException("지금 S3가 이상함");
        }

        String cloudFrontDomain = awsConfiguration.getCloudFrontDomain();
        return String.join("/", cloudFrontDomain, key);
    }

    private PutObjectRequest generateUploadRequest(String key, MediaType mediaType) {
        return PutObjectRequest.builder()
                .bucket(awsConfiguration.getS3Bucket())
                .key(key)
                .contentType(mediaType.toString())
                .build();
    }

    @Override
    protected void deleteImage(ResourceIdentifier identifier) {
        String imageUrl = identifier.getSubject();
        if (imageUrl != null) {
            String key = imageUrl.replace(awsConfiguration.getCloudFrontDomain() + "/", "");
            s3Client.deleteObject(request -> request.bucket(awsConfiguration.getS3Bucket()).key(key));
        }
    }
}
