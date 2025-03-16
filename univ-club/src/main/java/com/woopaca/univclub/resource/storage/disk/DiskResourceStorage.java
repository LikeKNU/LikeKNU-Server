package com.woopaca.univclub.resource.storage.disk;

import com.woopaca.univclub.resource.ImageResource;
import com.woopaca.univclub.resource.storage.ResourceIdentifier;
import com.woopaca.univclub.resource.storage.ResourceKeyGenerator;
import com.woopaca.univclub.resource.storage.ResourceStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Profile("local")
@Component
public class DiskResourceStorage implements ResourceStorage {

    @Override
    public String store(ImageResource resource, ResourceKeyGenerator resourceKeyGenerator) {
        String homeDirectory = System.getProperty("user.home");
        Path path = Paths.get(homeDirectory, "univ-club", resourceKeyGenerator.generateKey(resource));
        try (resource) {
            Files.createDirectories(path.getParent());
            Files.copy(resource.getInputStream(), path);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 생성 실패함 ㅅㄱ");
        }
        return path.toString();
    }

    @Override
    public void delete(ResourceIdentifier identifier) {
        String path = identifier.getSubject();
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 삭제 실패함 ㅅㄱ");
        }
    }
}