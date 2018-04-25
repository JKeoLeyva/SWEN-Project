package com.webcheckers.ui;

import com.webcheckers.Application;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.LogManager;

import static org.mockito.Mockito.*;

@Tag("UI-tier")
class WebServerTest {
    /**
     * Tests that Application handles an error if needed.
     * Indirectly tests WebServers initialize.
     */
    @Test
    void badReadConfiguration(){
        LogManager logManager = mock(LogManager.class);
        try {
            doThrow(new Exception()).when(logManager).readConfiguration();
        }
        catch(Exception e){}
        Application.main(new String[] {});
    }
}