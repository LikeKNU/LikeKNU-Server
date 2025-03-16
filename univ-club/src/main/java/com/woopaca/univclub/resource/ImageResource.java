package com.woopaca.univclub.resource;

import org.springframework.http.MediaType;

public interface ImageResource extends InputStreamSource {

    String getFilename();

    String getExtension();

    MediaType getMediaType();

    boolean isEmpty();
}
