import java.io.*;
import java.net.Socket;

public class Main {
    public final static String IP = "localhost";
    public final static int PORT = 8290;

    public static void main(String[] args) {
        System.out.println("Hi, client!");
        try (BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in))) {
            Socket socket = new Socket(IP, PORT);
            while (true) {
                System.out.print("Enter Login and Password:(example login:password) ");
                String reg = console.readLine();
                    if (reg.contains(":")) {
                        PrintWriter writer0 = new PrintWriter(socket.getOutputStream());
                        writer0.println(reg);
                        writer0.flush();
                        break;
                    } else {
                        System.out.println("Не верная запись ввода!\nПопробуйте еще раз! example login:password");
                    }
                }
          Thread w =  new Thread(() -> {
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
//                System.out.println("I will send " + message);

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
                if(message.startsWith("exit")){
//                    w.interrupt();
                    System.exit(0);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


//        try(BufferedReader log = new BufferedReader(new InputStreamReader(System.in))){
//            User user = new User();
//            System.out.println("Enter your login and password: ");
//            String login = log.readLine();
//            String password = log.readLine();
//            System.out.println("I send " + login + " and " + password);
//
//            new Thread(()->{
//                try {
//                    PrintWriter writer1 = new PrintWriter(user.getLogin());
//                    writer1.write(login);
//                    writer1.flush();
//                    PrintWriter writer2 = new PrintWriter(user.getPassword());
//                    writer2.write(password);
//                    writer2.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();

//            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1.out"))){
//            oos.writeObject(new User ("login","password"));
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//
//            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("1.out"))){
//              User user = (User) ois.readObject();
//                System.out.println("user.getLogin() + user.getPassword() = " + user.getLogin() + user.getPassword());
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
