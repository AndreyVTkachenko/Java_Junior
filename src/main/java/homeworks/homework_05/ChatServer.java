package homeworks.homework_05;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ChatServer {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
        ServerSocket server = null;

        try {
            server = new ServerSocket(8889);
            System.out.println("Сервер запущен");

            while (true) {
                System.out.println("Ждем клиентского подключения");
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client, clients);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время работы сервера: " + e.getMessage());
        } finally {
            if (server != null && !server.isClosed()) {
                try {
                    server.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сервера: " + e.getMessage());
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket client;
        private final Scanner in;
        private final PrintWriter out;
        private final Map<String, ClientHandler> clients;
        private String clientLogin;

        public ClientHandler(Socket client, Map<String, ClientHandler> clients) throws IOException {
            this.client = client;
            this.clients = clients;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
        }

        @Override
        public void run() {
            System.out.println("Подключен новый клиент");

            try {
                String loginRequest = in.nextLine();
                LoginRequest request = objectMapper.reader().readValue(loginRequest, LoginRequest.class);
                this.clientLogin = request.getLogin();
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }

            System.out.println("Запрос от клиента: " + clientLogin);
            // Проверка, что логин не занят
            if (clients.containsKey(clientLogin)) {
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }

            clients.put(clientLogin, this);
            String successfulLoginResponse = createLoginResponse(true);
            out.println(successfulLoginResponse);

            while (true) {
                String msgFromClient = in.nextLine();

                final String type;
                try {
                    AbstractRequest request = objectMapper.reader().readValue(msgFromClient, AbstractRequest.class);
                    type = request.getType();
                } catch (IOException e) {
                    System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                    sendMessage("Не удалось прочитать сообщение: " + e.getMessage());
                    continue;
                }

                if (SendMessageRequest.TYPE.equals(type)) {
                    handleSendMessageRequest(msgFromClient);
                } else if (BroadcastMessageRequest.TYPE.equals(type)) {
                    handleBroadcastMessageRequest(msgFromClient);
                } else if (UsersRequest.TYPE.equals(type)) {
                    handleUsersRequest();
                } else if (DisconnectRequest.TYPE.equals(type)) {
                    handleDisconnectRequest();
                    break;
                } else {
                    System.err.println("Неизвестный тип сообщения: " + type);
                    sendMessage("Неизвестный тип сообщения: " + type);
                    continue;
                }
            }
            doClose();
        }

        private void handleSendMessageRequest(String msgFromClient) {
            try {
                SendMessageRequest request = objectMapper.reader().readValue(msgFromClient, SendMessageRequest.class);

                ClientHandler clientTo = clients.get(request.getRecipient());
                if (clientTo == null) {
                    sendMessage("Клиент с логином [" + request.getRecipient() + "] не найден");
                    return;
                }
                String message = "[" + clientLogin + "]: " + request.getMessage();
                clientTo.sendMessage(message);
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение SendMessageRequest от клиента [" + clientLogin + "]: " + e.getMessage());
                sendMessage("Не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
            }
        }

        private void handleBroadcastMessageRequest(String msgFromClient) {
            try {
                BroadcastMessageRequest request = objectMapper.reader().readValue(msgFromClient, BroadcastMessageRequest.class);
                String message = "[" + clientLogin + "]: " + request.getMessage();

                clients.forEach((login, clientHandler) -> {
                    if (!login.equals(clientLogin)) {
                        clientHandler.sendMessage(message);
                    }
                });
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение BroadcastMessageRequest от клиента [" + clientLogin + "]: " + e.getMessage());
                sendMessage("Не удалось прочитать сообщение BroadcastMessageRequest: " + e.getMessage());
            }
        }

        private void handleUsersRequest() {
            List<String> logins = clients.keySet().stream().collect(Collectors.toList());
            UsersResponse response = new UsersResponse();
            response.setUsers(logins);

            try {
                String usersResponse = objectMapper.writer().writeValueAsString(response);
                out.println(usersResponse);
            } catch (JsonProcessingException e) {
                System.err.println("Не удалось создать UsersResponse: " + e.getMessage());
                sendMessage("Не удалось создать UsersResponse: " + e.getMessage());
            }
        }

        private void handleDisconnectRequest() {
            System.out.println("Клиент " + clientLogin + " отключился");
            clients.remove(clientLogin);
            sendMessage("Вы успешно отключились");
        }

        private void doClose() {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println("Ошибка во время отключения клиента: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        private String createLoginResponse(boolean success) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setConnected(success);
            try {
                return objectMapper.writer().writeValueAsString(loginResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Не удалось создать loginResponse: " + e.getMessage());
            }
        }
    }
}
