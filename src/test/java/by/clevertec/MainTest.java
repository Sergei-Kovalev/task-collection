package by.clevertec;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    protected ByteArrayOutputStream output;
    private PrintStream old;

    @BeforeEach
    public void setUpStreams() {
        old = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(old);
    }
    @Test
    void task14() {
        Main.task14();
        assertTrue(output.toString().contains("Total cost: 17995,58"));

    }
}