import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class OMinus {

    static void playSound(String filePath) {
    try {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    } catch (Exception e) {
        System.out.println("🔇 Failed to play sound: " + e.getMessage());
    }
}

    static void printString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        System.out.println(input);
        // Note: Don’t close shared scanner to avoid closing System.in
    }

    static void showHelp() {
        System.out.println("Available commands:");
        System.out.println(" - printString   : Prompt for and print a string");
        System.out.println(" - color         : Change terminal text color");
        System.out.println(" - /help         : Show this help message");
        System.out.println(" - exit          : Exit O-Minus");
        System.out.println(" - noShell       : Use regular command line iside the shell");
        System.out.println(" - executeProgram : Execute an exe file");
        System.out.println(" - runVirus      : run one of the prank viruses");
        System.out.println(" - compile       : compile a .omo file");
        System.out.println(" - runomo        : run a .omo file AFTER it has been compiled");
        System.out.println(" - createomo     : create a .omo file");
    }

    static void handleColorChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("Choose a color:");
            System.out.println("1: Blue\n2: Green\n3: Cyan\n4: Red");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                break;
            } else {
                System.out.println("Invalid input! Please enter a number like 1, 2, or 3.");
                scanner.nextLine(); // discard invalid input
            }
        }

        String colorCode = switch (choice) {
            case 1 -> "1";
            case 2 -> "2";
            case 3 -> "3";
            case 4 -> "4";
            default -> null;
        };

        if (colorCode != null) {
            try {
                new ProcessBuilder("cmd.exe", "/c", "color", colorCode)
                    .inheritIO()
                    .start()
                    .waitFor();

                    playSound("sound3.wav");
                System.out.println("Color changed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unexpected error: invalid color code.");
        }
    }

    static void noShell() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command: ");
        String input = scanner.nextLine();

        try {
            new ProcessBuilder("cmd.exe", "/c", input)
                .inheritIO()
                .start()
                .waitFor();

                playSound("sound2.wav");
        } catch (Exception e) {
            System.out.println("Failed to run command: " + e.getMessage());
        }
    }

    static void executeProgram() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the program name to execute: ");
        String program = scanner.nextLine();

        try {
            new ProcessBuilder("cmd.exe", "/c", "start", program)
                .inheritIO()
                .start()
                .waitFor();

                playSound("sound4.wav");
            System.out.println("Program Executed!");
        } catch (Exception e) {
            System.out.println("Failed to execute program: " + e.getMessage());
        }
    }

    static void runVirus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a virus to run:");
        System.out.println("1: Fake Delete System32");
        System.out.println("2: Albanian");
        System.out.println("3: python virus");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.println("deleting System32...");
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Deleting C:\\Windows\\System32\\file" + i + ".dll ...");
                        Thread.sleep(1000);
                    }

                    playSound("sound2.wav");
                } catch (InterruptedException e) {
                    System.out.println("Prank interrupted.");
                }
            }
            case "2" -> {
                try {
                    new ProcessBuilder("cmd.exe", "/c", "cscript Albanian_Virus.vbs")
                        .inheritIO()
                        .start()
                        .waitFor();

                        playSound("sound4.wav");
                    System.out.println("Program Executed!");
                } catch (Exception e) {
                    System.out.println("Failed to execute program: " + e.getMessage());
                }
            }

case "3" -> {
    try {
        new ProcessBuilder("cmd.exe", "/c", "python virus.py")
            .inheritIO()
            .start()
            .waitFor();

            playSound("sound1.wav");
        System.out.println("Program Executed!");
    } catch (Exception e) {
        System.out.println("Failed to execute program: " + e.getMessage());
    }
}
        }
    }

    static void compile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the .omo file to compile: ");
        String fileName = scanner.nextLine();        

try {
            new ProcessBuilder("cmd.exe", "/c", "java O", fileName)
                .inheritIO()
                .start()
                .waitFor();

                playSound("sound3.wav");
        } catch (Exception e) {
            System.out.println("Failed to run command: " + e.getMessage());
        }
    }

    static void runomo() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the .omo file to run: ");
    String fileName = scanner.nextLine();

    try {
        new ProcessBuilder("cmd.exe", "/c", "java", fileName)
            .inheritIO()
            .start()
            .waitFor();

        playSound("sound1.wav");

    } catch (Exception e) {
        System.out.println("Failed to run .omo file: " + e.getMessage());
    }
}

    static void createomo() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter .omo file name: ");
    String input = scanner.nextLine();

    try {
        File newOmoFile = new File(input + ".omo");

        if (newOmoFile.createNewFile()) {
            System.out.println("File created: " + newOmoFile.getName());
            playSound("sound2.wav");
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        System.out.println("Welcome to O-Minus! Type '/help' for commands.");

        while (true) {
            System.out.print(">>> ");
            try {
                input = reader.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) break;

                switch (input.toLowerCase()) {
                    case "printstring" -> printString();
                    case "/help" -> showHelp();
                    case "color" -> handleColorChoice();
                    case "noshell" -> noShell();
                    case "executeprogram" -> executeProgram();
                    case "runvirus" -> runVirus();
                    case "compile" -> compile();
                    case "runomo" -> runomo();
                    case "createomo" -> createomo();
                    default -> {
                        try {
                            new ProcessBuilder("cmd.exe", "/c", input).inheritIO().start().waitFor();
                        } catch (IOException e) {
                            System.out.println("Unknown command or error occurred. Type '/help' for options.");
                        }
                    }
                }

            } catch (IOException | InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        playSound("sound2.wav");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
e.printStackTrace();
        }
        System.out.println("See ya");
    }
}