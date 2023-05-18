package main.java.com.butlerspantry.connection;

import main.java.com.butlerspantry.logging.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

        @Test
        void readFileThrowsExceptionIfNoFile() {
            Exception whatIsThisException = assertThrows(Exception.class, () -> {
                ReadFile.readFile("doesntexist.csv");
            });
            Logger.logNow(whatIsThisException.getMessage() + " " + whatIsThisException.getClass());
        }
    }