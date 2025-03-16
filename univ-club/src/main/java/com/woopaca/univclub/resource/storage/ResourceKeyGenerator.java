package com.woopaca.univclub.resource.storage;

import com.woopaca.univclub.resource.ImageResource;

/**
 * @see ResourceKeyGenerators
 */
@FunctionalInterface
public interface ResourceKeyGenerator {

    String generateKey(ImageResource resource);
}
