package com.woopaca.univclub.resource.storage;

/**
 * @see ResourceKeyGenerator
 */
public class ResourceKeyGenerators {

    private static final String KEY_SEPARATOR = "/";

    public static final ResourceKeyGenerator LOGO_IMAGE_KEY_GENERATOR = resource -> {
        String filename = resource.getFilename();
        if (filename.startsWith(KEY_SEPARATOR)) {
            return "logo".concat(filename);
        }
        return String.join(KEY_SEPARATOR, "logos", resource.getFilename());
    };
}
