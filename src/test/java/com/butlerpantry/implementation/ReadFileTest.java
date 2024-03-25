package com.butlerpantry.implementation;

import com.butlerpantry.data.ReadFile;
import com.butlerpantry.logging.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadFileTest {

    @Test
    void readFileThrowsExceptionIfNoFile() {
        Exception whatIsThisException = assertThrows(Exception.class, () -> {
            ReadFile.readFile("doesntexist.csv");
                });
        Logger.logNow(whatIsThisException.getMessage() + whatIsThisException.getClass());
    }
}