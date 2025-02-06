import java.io.*;
import java.net.*;

public class RawSocketServer {
    @FunctionalInterface 
    public interface Operations {
        String apply(float a, float b);    
    }
    private static String green = "\u001B[32m";
    private static String reset = "\u001B[0m";
    private static String red = "\u001B[31m";






    private static String[] operations = {"Plus","Minus","Divide","Multiply"}; 
    private static Operations[] functions = new Operations[]{
        (x, y) -> x + " + " + y + " = " + green + (x + y) + reset,  
        (x, y) -> x + " - " + y + " = " + green + (x - y) + reset,            
        (x, y) -> x + " / " + y + " = " + green + (x / y) + reset,                  
        (x, y) -> x + " * " + y + " = " + green + (x * y) + reset           
    };
    public static void main(String[] args) {
        int port = 5090; // Port to listen on
        int option = 0;
        float number1 = 0;
        float number2 = 0;
        String answer = "";
        String receivedMessage = "";
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                try {

                    out.println(green + "CONNECTED" + reset);
                    out.println("Enter " + red + "'exit'" + reset + " to exit" + reset); 
                    out.println(operations.length);
                    for (int i = 0; i < operations.length; i++) {
                       out.println(i + ". " + operations[i]);
                    } 
                    while ((receivedMessage != null || receivedMessage != "exit")) {
                           receivedMessage = in.readLine();
                           //option = Integer.parseInt(validateValue(option, receivedMessage, "option", in, out));
                           option = Integer.parseInt(receivedMessage);
                           out.println("Enter a number:");
                           receivedMessage = in.readLine();
                           //number1 = Float.parseFloat(validateValue(number1, receivedMessage, "number", in, out));
                           number1 = Float.parseFloat(receivedMessage);
                           out.println("Enter a number you want to apply " + operations[option] + " with:");
                           receivedMessage = in.readLine();
                           //number2 = Float.parseFloat(validateValue(number2, receivedMessage, "number", in, out));
                           number2 = Float.parseFloat(receivedMessage);
                           answer = functions[option].apply(number1, number2);
                           out.println(answer);
                           
                    }
                } catch (Exception e) {
                    clientSocket.close();
                } finally {
                    clientSocket.close();
                }
            } 

        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
}

