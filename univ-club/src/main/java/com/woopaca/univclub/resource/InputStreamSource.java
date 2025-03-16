package com.woopaca.univclub.resource;

import java.io.Closeable;
import java.io.InputStream;

public interface InputStreamSource extends Closeable {

    InputStream getInputStream();
}
