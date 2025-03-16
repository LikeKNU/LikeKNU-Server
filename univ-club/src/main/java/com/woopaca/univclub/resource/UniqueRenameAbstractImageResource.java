package com.woopaca.univclub.resource;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class UniqueRenameAbstractImageResource extends AbstractImageResource {

    private final String filename;

    protected UniqueRenameAbstractImageResource(String originalFilename, InputStream inputStream) {
        super(originalFilename, inputStream);

        String uuid = UUID.randomUUID()
                .toString();
        this.filename = String.join(".", uuid, getExtension());
    }

    public static ImageResource from(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new EmptyImageResource();
        }

        try {
            return new UniqueRenameAbstractImageResource(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("아 이미지 파일 뭔가 이상함;;");
        }
    }

    @Override
    public String getFilename() {
        return filename;
    }
}
