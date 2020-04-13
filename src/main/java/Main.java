import java.io.*;
import java.net.Socket;

public class Main {
    public final static String IP = "localhost";
    public final static int PORT = 8290;
    public static Socket socket;

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в ЧАТ!\n1.Регистрация\n2.Аутентификация\n3.Выход");
        try (BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in))) {
            socket = new Socket(IP, PORT);

            switch (console.readLine()) {
                case "1":
                    registration();
                    break;
                case "2":
                    autentification();
                    break;
                case "3":
                    System.exit(0);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void autentification() {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Введите логин и пароль! (логин:пароль) ");
            String aut = console.readLine();
            while (!aut.contains(":") || aut.startsWith(" ") || aut.startsWith(":")) {
                System.out.println("Не верная запись ввода!\nПопробуйте еще раз! (логин:пароль)");
                aut = console.readLine();
            }
            String boom2 = aut.replaceAll("\\s", "");
            aut = boom2;
            PrintWriter writer2 = new PrintWriter(socket.getOutputStream());
            writer2.println(aut + "AUT");
            writer2.flush();
            BufferedReader clientReader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String checkAut = clientReader2.readLine();
            System.out.println(checkAut);
            if (checkAut.contains("Неверный логин или пароль!")) {
                autentification();
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

    public static void registration() {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Введите логин и пароль! (Логин:Пароль) ");
            String reg = console.readLine();
            while (!reg.contains(":") || reg.startsWith(" ") || reg.startsWith(":")) {
                System.out.println("Не верная запись ввода!\nПопробуйте еще раз! (логин:пароль)");
                reg = console.readLine();
            }
            String trueReg = reg.replaceAll("\\s", "");
            reg = trueReg;
            PrintWriter writer0 = new PrintWriter(socket.getOutputStream());
            writer0.println(reg + "REGISTRATION");
            writer0.flush();
            BufferedReader clientReader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String checkReg = clientReader2.readLine();
            System.out.println(checkReg);
            if (checkReg.contains("Данный пользователь уже зарегистрирован!")) {
                registration();
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

