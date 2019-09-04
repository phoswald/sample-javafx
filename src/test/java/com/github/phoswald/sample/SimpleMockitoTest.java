package com.github.phoswald.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class SimpleMockitoTest {

    @Test
    void mock_localClass_success() {
        FileListItem mock = mock(FileListItem.class);
        when(mock.getName()).thenReturn("name-1", "name-2");
        assertEquals("name-1", mock.getName());
        assertEquals("name-2", mock.getName());
        verify(mock, times(2)).getName();
        verifyNoMoreInteractions(mock);
    }

    @Test
    void mock_jreClass_success() throws IOException {
        InputStream mock = mock(InputStream.class);
        when(mock.read()).thenReturn(Integer.valueOf(42), Integer.valueOf(43));
        assertEquals(42, mock.read());
        assertEquals(43, mock.read());
        verify(mock, times(2)).read();
        verifyNoMoreInteractions(mock);
    }
}
