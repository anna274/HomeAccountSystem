package com.rusakovich.bsuir.client.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Client extends Application {

    private static final Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        openScene("../views/Login.fxml");
    }

    public static Map<String, String> doRequest(String query) {
        String response = "";
        try {
            Socket clientSocket = new Socket("127.0.0.1", 8888);
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())
            );
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            bufferedWriter.write(query + "\n");
            bufferedWriter.flush();
            response = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getResponseParams(response);
    }

    private static Map<String, String> getResponseParams(String response) {
        String[] responseParams = response.split("&");
        Map<String, String> params = new HashMap<>();
        for (String sr : responseParams) {
            System.out.println(sr);
            String[] s = sr.split("=");
            params.put(s[0], s[1]);
        }
        return params;
    }

    public static Map<String, String> getResponseObject(String data) {
        String[] responseParams = data.split(",");
        Map<String, String> params = new HashMap<>();
        for (String sr : responseParams) {
            System.out.println(sr);
            String[] s = sr.split(":");
            params.put(s[0], s[1]);
        }
        return params;
    }

    public static ArrayList<Map<String, String>> getResponseArray(String data) {
        ArrayList<Map<String, String>> objects = new ArrayList<>();
        String[] responseObjectsParams = data.split(";");
        for (String s : responseObjectsParams) {
            objects.add(getResponseObject(s));
        }
        return objects;
    }


    public static void openScene(String scenePath){
        stage.setResizable(false);
        try {
            Parent root = FXMLLoader.load(Client.class.getResource(scenePath));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
