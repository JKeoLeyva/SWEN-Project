package com.webcheckers;

import org.junit.jupiter.api.Test;

class ApplicationTest {
    @Test
    void testServerStarts() {
        // Make sure startup doesn't crash
        Application.main(new String[] {});
    }
}
