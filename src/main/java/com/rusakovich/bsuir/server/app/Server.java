package com.rusakovich.bsuir.server.app;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                processRequest(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processRequest(Socket clientSocket) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String request = bufferedReader.readLine();
        String[] splitRequest = request.split("\\?");
        String controllerName = splitRequest[0];
        Map<String, String> params = splitRequest.length > 1 ? getRequestParams(splitRequest[1]) : new HashMap<>();
        Controller controller = ControllerType.getControllerByName(controllerName);

        String response = controller.request(params);

        bufferedWriter.write(response + "\n");
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
        clientSocket.close();
    }

    private static Map<String, String> getRequestParams(String query) {
        String[] queryParams = query.split("&");
        Map<String, String> params = new HashMap<>();
        for (String sr : queryParams) {
            String[] s = sr.split("=");
            params.put(s[0], s[1]);
        }
        return params;
    }
}
