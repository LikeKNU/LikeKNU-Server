package com.woopaca.univclub.resource.storage;

import lombok.Getter;

@Getter
public class ResourceIdentifier {

    private final String subject;

    private ResourceIdentifier(String subject) {
        this.subject = subject;
    }

    public static ResourceIdentifier of(String subject) {
        return new ResourceIdentifier(subject);
    }
}
