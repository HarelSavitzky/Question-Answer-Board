package com.example.ex2;
import com.google.gson.JsonObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Get answers.
 */
@WebServlet(name = "getAnswers", value = "/getAnswers")
public class getAnswers extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * the get handler is receiving the question pressed and building a json to send to the index.html page
     * in order to display all the answers to the question
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject myJson = new JsonObject();
        int numOfQuestion = Integer.parseInt((request.getParameter("id")));

        ServletContext context = getServletContext();
        if(context.getAttribute("data") instanceof runTimeData) {

            runTimeData myDB = (runTimeData) context.getAttribute("data");
            String currentQuestion = myDB.getQuestion(numOfQuestion).replace("\r","");;
            HashMap<String, ArrayList<String>> myMap = myDB.getMap();
            ArrayList<String> answersList = myMap.get(currentQuestion);

            for(int i = 0; i < answersList.size(); i++){
                String [] currentAnswer = answersList.get(i).split(":\n");
                myJson.addProperty(i+"_name", currentAnswer[0]);
                myJson.addProperty(i+"_answer", currentAnswer[1]);
            }
            myJson.addProperty("_questionNum", numOfQuestion);
        }
        response.setContentType("application/json");
        PrintWriter output = response.getWriter();
        output.print(myJson);
        output.flush();
    }
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
