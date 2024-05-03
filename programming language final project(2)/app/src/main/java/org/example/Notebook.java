package org.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.File;
public class Notebook {
    private static String notebookFileName = "notebook.txt";

    public static void checkOrCreateNotebook() {
        if (!Files.exists(Paths.get(notebookFileName))) {
            try {
                Files.createFile(Paths.get(notebookFileName));
                System.out.println("No default notebook was found, created one.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readNotebook() {
        StringBuilder notes = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(notebookFileName))) {
            String line;
            int lineNumber = 1;
            boolean isEmpty = true;
            while ((line = br.readLine()) != null) {
                isEmpty = false;
                notes.append(lineNumber++).append(". ").append(line).append("\n");
            }
            if (isEmpty) {
                System.out.println("Note empty");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes.toString();
    }

    public static void addNote() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(notebookFileName, true))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Write a new note: ");
            String newNote = scanner.nextLine();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yy");
            String timestamp = now.format(formatter);
            bw.write(newNote + ":::" + timestamp + "\n");
            System.out.println("Note added.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void emptyNotebook() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(notebookFileName))) {
            bw.write("");
            System.out.println("Notes deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeNotebook() {
        try (BufferedReader reader = new BufferedReader(new FileReader(notebookFileName))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Give the name of the new file: ");
            String newFileName = scanner.nextLine();
            if (!Files.exists(Paths.get(newFileName))) {
                try {
                    Files.createFile(Paths.get(newFileName));
                    System.out.println("No notebook with that name detected, created one.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            notebookFileName = newFileName;
            System.out.println("Now using file " + notebookFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteNote() {
        try (BufferedReader br = new BufferedReader(new FileReader(notebookFileName))) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(readNotebook());
            System.out.print("Enter the line number of the note you want to delete: ");
            int lineNumberToDelete = Integer.parseInt(scanner.nextLine());
            StringBuilder updatedNotes = new StringBuilder();
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (lineNumber != lineNumberToDelete) {
                    updatedNotes.append(line).append("\n");
                }
                lineNumber++;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(notebookFileName))) {
                bw.write(updatedNotes.toString());
                System.out.println("Note deleted.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file you want to delete: ");
        String fileNameToDelete = scanner.nextLine();
        File file = new File(fileNameToDelete);
        if(file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public static void main(String[] args) {
        checkOrCreateNotebook();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("(1) Read the notebook");
                System.out.println("(2) Add note");
                System.out.println("(3) Empty the notebook");
                System.out.println("(4) Change the notebook");
                System.out.println("(5) Delete a note");
                System.out.println("(6) Delete a file");
                System.out.println("(7) Quit");

                System.out.print("\nPlease select one: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        String notes = readNotebook();
                        System.out.println(notes);
                        break;
                    case "2":
                        addNote();
                        break;
                    case "3":
                        emptyNotebook();
                        break;
                    case "4":
                        changeNotebook();
                        break;
                    case "5":
                        deleteNote();
                        break;
                    case "6":
                        deleteFile();
                        break;
                    case "7":
                        System.out.println("Notebook shutting down, thank you.");
                        return;
                    default:
                        System.out.println("Incorrect selection.");
                        break;
                }
            }
        }
    }
}
