package com.woopaca.univclub.resource;

public class EmptyImageResource extends AbstractImageResource {

    protected EmptyImageResource() {
        super(null, null);
    }

    @Override
    protected String extractExtension(String filename) {
        return null;
    }

    @Override
    protected void validateExtension(String extension) {
    }
}
