package com.woopaca.univclub.resource.storage;

import com.woopaca.univclub.resource.ImageResource;

public interface ResourceStorage {

    String store(ImageResource resource, ResourceKeyGenerator resourceKeyGenerator);

    void delete(ResourceIdentifier identifier);
}
