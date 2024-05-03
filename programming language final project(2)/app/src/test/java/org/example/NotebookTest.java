package org.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;


public class NotebookTest {
    @Test
    void addNoteTest() {
        // Set up input and output streams
        String input = "Test note\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the addNote method
        Notebook.addNote();

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Check if the note was added
        String output = outputStream.toString();
        assertTrue(output.contains("Note added."));
    }

    // Similarly, write tests for other methods like readNotebook, emptyNotebook, changeNotebook, deleteNote, deleteFile, and main.
}
