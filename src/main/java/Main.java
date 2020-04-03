import java.io.*;
import java.net.Socket;

public class Main {
    public final static String IP = "localhost";
    public final static int PORT = 8290;

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в ЧАТ!\n1.Регистрация\n2.Аутентификация\n3.Выход");
        try (BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in))) {
            Socket socket = new Socket(IP, PORT);
            switch (console.readLine()) {
                case "1":
//                    while (true) {
                    System.out.print("Введите логин и пароль! (!логин:пароль!) ");
                    String reg = console.readLine();
                    while (!reg.contains(":") || reg.startsWith(" ") || reg.startsWith(":")) {
                        System.out.println("Не верная запись ввода!\nПопробуйте еще раз! (!логин:пароль!)");
                        reg = console.readLine();
                    }
                    String boom = reg.replaceAll("\\s", "");
                    reg = boom;

                    PrintWriter writer0 = new PrintWriter(socket.getOutputStream());
                    writer0.println(reg + "REGISTRATION");
                    writer0.flush();
                    BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage = clientReader.readLine();
                    while (!serverMessage.equals("Регистация прошла успешно!")) {
                        System.out.println("Данный пользователь уже зарегистрирован! Повторите ввод:");
                        serverMessage = clientReader.readLine();
                        String out = console.readLine();
                        PrintWriter writer1 = new PrintWriter(socket.getOutputStream());
                        writer1.println(out);
                        writer1.flush();
                    }

//                    serverMessage.contains("Регистация прошла успешно!");
                    System.out.println("Регистация прошла успешно!");
                    break;

                case "2":
                    System.out.print("Введите логин и пароль! (логин:пароль) ");
                    String aut = console.readLine();
                    while (!aut.contains(":") || aut.startsWith(" ") || aut.startsWith(":")) {
                        System.out.println("Не верная запись ввода!\nПопробуйте еще раз! (!логин:пароль!)");
                        aut = console.readLine();
                    }
                    String boom2 = aut.replaceAll("\\s", "");
                    aut = boom2;
                    PrintWriter writer2 = new PrintWriter(socket.getOutputStream());
                    writer2.println(aut + "AUT");
                    writer2.flush();
//                    BufferedReader clientReader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String serverMessage2 = clientReader2.readLine();
//                    System.out.println(serverMessage2);
                    break;

                case "3":
                   System.exit(0);
            }
            Thread w = new Thread(() -> {
                try {
                    BufferedReader serverReader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        System.out.println(serverReader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            w.start();

            while (true) {
                String message = console.readLine();
                Thread t = new Thread(() -> {
                    try {
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        writer.println(message);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
                t.join();
                if (message.startsWith("exit")) {
                    System.exit(0);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

