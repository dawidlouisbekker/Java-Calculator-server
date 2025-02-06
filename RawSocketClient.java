import java.io.*;
import java.net.*;
import java.util.Scanner;



public class RawSocketClient {

    private static String reset = "\u001B[0m";
    private static String red = "\u001B[31m";

    private static <T> String validateValue(T var, String input, String category, Scanner in) {
        String name = var.getClass().getSimpleName();
        boolean valid = false;
        String output = input;
        while (!valid) {
            switch (name) {
                case "Integer":
                    try {
                        Integer.parseInt(output); // Just checking validity
                        valid = true;
                    } catch (Exception e) {
                        System.out.println(red + "INVALID" + reset);
                        System.out.println("Please enter a valid " + category + ":");
                        output = in.next(); // Read new input
                    }
                    break;
    
                case "Float":
                    try {
                        Float.parseFloat(output); // Just checking validity
                        valid = true;
                    } catch (Exception e) {
                        System.out.println(red + "INVALID" + reset);
                        System.out.println("Please enter a valid " + category + ":");
                        output = in.next(); // Read new input
                    }
                    break;
    
                default:
                    System.out.println(red + "UNKNOWN TYPE" + reset);
                    return input;  // Ensure a return value instead of falling through
            }
        }
        return output;
    }
    


    private static void ReadAndPrintInput(BufferedReader in) {
        try {
            String response = in.readLine();
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        String serverAddress = "localhost";
        String request = "";
        int num = 0;
        float flNum = 0;
        int port = 5090; // Server port
            try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
           
               try {
                   String userInput = "";
                   String response = "";
       
        
       
                       ReadAndPrintInput(in);
                       ReadAndPrintInput(in);
                       //read options here

                       response = in.readLine();
                       int length = Integer.parseInt(response);
                       String[] options = new String[length  + 1];
                       for (int i = 0; i < length; i++) {
                           response = in.readLine();
                           options[i] = response;
                       }
                    while ((userInput != "exit") && (response != null)) {
                       System.out.println("Enter an option: ");
                       for (int i = 0; i < length; i++) {
                        System.out.println(options[i]);
                       }
                       System.out.println("Option: ");
                       userInput = scanner.next();
                       if (userInput.equals("exit")) {
                        return;
                       }
                       request = validateValue(num, userInput, "option", scanner);

                       out.println(request);
                       ReadAndPrintInput(in);
                       userInput = scanner.next();
                       if (userInput.equals("exit")) {
                        return;
                       }
                       request = validateValue(flNum, userInput, "number", scanner);
                       out.println(request);
                       ReadAndPrintInput(in);
                       userInput = scanner.next();
                       if (userInput.equals("exit")) {
                        return;
                       }
                       request = validateValue(flNum, userInput, "number", scanner);
                       out.println(request);
                       ReadAndPrintInput(in);

                    }
               } catch (Exception e) {
                   System.out.println(e);
               } finally {
                   in.close();
                   out.close();
                   scanner.close();
                   socket.close();
                   System.out.println(reset);
               }

            } catch (IOException e) {
                //e.printStackTrace();
            }
        
    }
}

