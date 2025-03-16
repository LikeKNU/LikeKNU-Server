package com.woopaca.univclub.resource;

import org.springframework.http.MediaType;

import java.io.InputStream;
import java.util.Set;

public abstract class AbstractImageResource implements ImageResource {

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "JPG", "JPEG", "PNG");

    private final String originalFilename;
    private final InputStream inputStream;
    private final String extension;

    protected AbstractImageResource(String originalFilename, InputStream inputStream) {
        this.originalFilename = originalFilename;
        this.inputStream = inputStream;
        this.extension = extractExtension(originalFilename);
        validateExtension(extension);
    }

    protected String extractExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    protected void validateExtension(String extension) {
        if (!SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("이미지만 업로드 하세요;;");
        }
    }

    @Override
    public String getFilename() {
        return originalFilename;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parseMediaType("image/" + extension.toLowerCase());
    }

    @Override
    public boolean isEmpty() {
        return originalFilename == null || originalFilename.isBlank() || inputStream == null;
    }

    @Override
    public void close() {
        try {
            inputStream.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("이미지 파일 뭔가 이상함;;");
        }
    }
}
