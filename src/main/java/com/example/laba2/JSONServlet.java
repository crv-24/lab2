package com.example.laba2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/JSONServlet")
public class JSONServlet extends HttpServlet {

    // JSON filename to store Car objects
    private static final String JSON_FILENAME = "cars.json";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONArray jsonArray = readJSONFile();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonArray.toString());
            System.out.println("doGet finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject jsonCar = new JSONObject();
            try {
                BufferedReader reader = request.getReader();
                String json = "";
                String line = "";

                while ((line = reader.readLine()) != null) {
                    json += line;
                }

                JSONObject jsonObject = new JSONObject(json);
                String name = jsonObject.getString("name");
                String author = jsonObject.getString("author");
                int year = jsonObject.getInt("year");
                String album = jsonObject.getString("album");
                String country = jsonObject.getString("country");

                jsonCar.put("name", name);
                jsonCar.put("author", author);
                jsonCar.put("year", year);
                jsonCar.put("album", album);
                jsonCar.put("country", country);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = readJSONFile();
            jsonArray.put(jsonCar);
            writeJSONFile(jsonArray);

            response.sendRedirect(request.getContextPath() + "/JSONServlet");
            System.out.println("doPost finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONArray readJSONFile() throws Exception {
        System.out.println("readJSONF start");
        JSONArray jsonArray = new JSONArray();
        File file = new File(getServletContext().getRealPath("/") + "/" + JSON_FILENAME);
        if (file.exists()) {
            String jsonString = readFileAsString(file);
            jsonArray = new JSONArray(jsonString);
        }
        System.out.println("readJSONF finish");
        return jsonArray;
    }

    private void writeJSONFile(JSONArray jsonArray) throws Exception {
        System.out.println("writeJSONF start");
        String jsonString = jsonArray.toString();
        File file = new File(getServletContext().getRealPath("/") + "/" + JSON_FILENAME);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonString);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("writeJSONF finish");
    }

    private static String readFileAsString(File file) throws Exception {
        System.out.println("readFileAsString start");
        StringBuilder stringBuilder = new StringBuilder((int) file.length());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        char[] buffer = new char[1024];
        int length;
        while ((length = bufferedReader.read(buffer)) != -1) {
            stringBuilder.append(buffer, 0, length);
        }
        bufferedReader.close();
        System.out.println("readFileAsString finish");
        return stringBuilder.toString();
    }
}
