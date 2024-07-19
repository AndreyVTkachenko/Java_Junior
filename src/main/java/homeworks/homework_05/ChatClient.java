package homeworks.homework_05;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String clientLogin = console.nextLine();

        try (Socket server = new Socket("localhost", 8889)) {
            System.out.println("Успешно подключились к серверу");

            try (PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                 Scanner in = new Scanner(server.getInputStream())) {

                String loginRequest = createLoginRequest(clientLogin);
                out.println(loginRequest);

                String loginResponseString = in.nextLine();
                if (!checkLoginResponse(loginResponseString)) {
                    System.out.println("Не удалось подключиться к серверу");
                    return;
                }

                new Thread(() -> {
                    while (true) {
                        try {
                            if (in.hasNextLine()) {
                                String msgFromServer = in.nextLine();
                                System.out.println("Сообщение от сервера: " + msgFromServer);
                            }
                        } catch (Exception e) {
                            System.err.println("Ошибка получения сообщения от сервера: " + e.getMessage());
                            break;
                        }
                    }
                }).start();

                while (true) {
                    System.out.println("Что хочу сделать?");
                    System.out.println("1. Послать сообщение другу");
                    System.out.println("2. Послать сообщение всем");
                    System.out.println("3. Получить список логинов");
                    System.out.println("4. Отключиться");

                    String type = console.nextLine();
                    if (type.equals("1")) {
                        System.out.println("Введите логин получателя:");
                        String recipient = console.nextLine();

                        SendMessageRequest request = new SendMessageRequest();
                        request.setRecipient(recipient);
                        request.setMessage(console.nextLine());

                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("2")) {
                        System.out.println("Введите сообщение для всех:");
                        BroadcastMessageRequest request = new BroadcastMessageRequest();
                        request.setMessage(console.nextLine());

                        String broadcastMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(broadcastMsgRequest);
                    } else if (type.equals("3")) {
                        UsersRequest request = new UsersRequest();
                        String usersRequest = objectMapper.writeValueAsString(request);
                        out.println(usersRequest);

                        String usersResponseString = in.nextLine();
                        UsersResponse usersResponse = objectMapper.readValue(usersResponseString, UsersResponse.class);
                        System.out.println("Список логинов: " + usersResponse.getUsers());
                    } else if (type.equals("4")) {
                        DisconnectRequest request = new DisconnectRequest();
                        String disconnectRequest = objectMapper.writeValueAsString(request);
                        out.println(disconnectRequest);
                        break;
                    } else {
                        System.out.println("Неизвестная команда");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
        }
    }

    private static String createLoginRequest(String login) throws JsonProcessingException {
        LoginRequest request = new LoginRequest();
        request.setLogin(login);
        return objectMapper.writeValueAsString(request);
    }

    private static boolean checkLoginResponse(String response) throws JsonProcessingException {
        LoginResponse loginResponse = objectMapper.readValue(response, LoginResponse.class);
        return loginResponse.isConnected();
    }
}
