package com.github.phoswald.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SimpleJUnit5Test {

    private final FileListItem testee = new FileListItem();

    @Test
    void fileNameProperty_get_success() {
        testee.setName("test1");
        assertEquals("test1", testee.fileNameProperty().get());
    }

    @Test
    void fileNameProperty_set_success() {
        testee.fileNameProperty().set("test2");
        assertEquals("test2", testee.getName());
    }
}
