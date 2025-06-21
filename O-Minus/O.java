import java.io.*;
import java.util.*;

public class O {
    // 🆕 Tracks the most recent input variable
    private static String lastInputVar = null;

    public static void compile(String omoPath) throws IOException {
        File omoFile = new File(omoPath);
        String className = omoFile.getName().replace(".omo", "");
        File javaFile = new File(omoFile.getParentFile(), className + ".java");

        Set<String> declaredVars = new HashSet<>();
        List<String> javaLines = new ArrayList<>();
        boolean needsScanner = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(omoFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty()) continue;

                if (line.startsWith("text ")) {
                    javaLines.addAll(handleText(line, declaredVars));
                } else if (line.startsWith("say ")) {
                    javaLines.addAll(handleSay(line, declaredVars));
                } else if (line.startsWith("con ")) {
                    javaLines.addAll(handleConcaStrings(line, declaredVars));
                } else if (line.startsWith("wait ")) {
                    javaLines.addAll(handleWait(line));
                } else if (line.startsWith("input ")) {
                    needsScanner = true;
                    javaLines.addAll(handleInput(line, declaredVars));
                } else if (line.startsWith("number ")) {
                    javaLines.addAll(handleNum(line, declaredVars));
                } else if (line.startsWith("InputVar ")) {
                    javaLines.addAll(handleInputVar(line, declaredVars));
                } else {
                    System.out.println("Unsupported syntax: " + line);
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.println("import java.util.Scanner;");
            writer.println("public class " + className + " {");
            writer.println("    public static void main(String[] args) {");
            if (needsScanner) {
                writer.println("        Scanner scanner = new Scanner(System.in);");
            }
            for (String javaLine : javaLines) {
                writer.println("        " + javaLine);
            }
            writer.println("    }");
            writer.println("}");
        }

        compileJava(javaFile);
    }

    private static List<String> handleText(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        String[] declarations = line.substring(5).split(",");
        for (String decl : declarations) {
            String[] parts = decl.split("=", 2);
            if (parts.length != 2) {
                System.out.println("Invalid 'text' syntax: " + decl);
                continue;
            }
            String varName = parts[0].trim();
            String value = parts[1].trim();
            if (!declaredVars.add(varName)) {
                System.out.println("Duplicate variable declaration: " + varName);
            }
            result.add("String " + varName + " = " + value + "\";");
        }
        return result;
    }

    private static List<String> handleSay(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        String[] expressions = line.substring(4).split(",");
        for (String expr : expressions) {
            String content = expr.trim();
            if (declaredVars.contains(content)) {
                result.add("System.out.println(" + content + ");");
            } else {
                result.add("System.out.println(\"" + content + "\");");
            }
        }
        return result;
    }

    private static List<String> handleWait(String line) {
        List<String> result = new ArrayList<>();
        String value = line.substring(5).trim();
        result.add("try { Thread.sleep(" + value + "); } catch (InterruptedException e) { e.printStackTrace(); }");
        return result;
    }

    private static List<String> handleInput(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        String varName = line.substring(6).trim();

        if (!declaredVars.add(varName)) {
            System.out.println("Duplicate variable declaration: " + varName);
        }

        lastInputVar = varName; // 🔗 Track latest input

        result.add("System.out.print(\"" + varName + ": \");");
        result.add("String " + varName + " = scanner.nextLine();");

        return result;
    }

    private static List<String> handleNum(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        String[] declarations = line.substring(6).split(",");
        for (String decl : declarations) {
            String[] parts = decl.split("=", 2);
            if (parts.length != 2) {
                System.out.println("Invalid 'number' syntax: " + decl);
                continue;
            }
            String varName = parts[0].trim();
            String value = parts[1].trim();
            if (!declaredVars.add(varName)) {
                System.out.println("Duplicate variable declaration: " + varName);
            }
            result.add("int " + varName + " = " + value + ";");
        }
        return result;
    }

    private static List<String> handleConcaStrings(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        String expression = line.substring(4).trim();
        result.add("System.out.println(" + expression + ");");
        return result;
    }

    private static List<String> handleInputVar(String line, Set<String> declaredVars) {
        List<String> result = new ArrayList<>();
        if (lastInputVar != null) {
            result.add("System.out.println(" + lastInputVar + ");");
        } else {
            System.out.println("No previous input variable found.");
        }
        return result;
    }

    private static void compileJava(File javaFile) {
        try {
            Process compile = new ProcessBuilder("javac", javaFile.getAbsolutePath())
                .inheritIO()
                .start();
            compile.waitFor();
            System.out.println("Compiled: " + javaFile.getName().replace(".java", ".class"));
        } catch (Exception e) {
            System.err.println("Compilation error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java O <file.omo>");
            return;
        }

        try {
            compile(args[0]);
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }
}