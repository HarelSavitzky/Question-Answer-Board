package com.example.ex2;

import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static java.nio.file.Path.*;

/**
 * The type Questions servlet.
 */
@WebServlet(name = "questionsServlet", value = "/Questions", initParams={@WebInitParam(name="nameOfFile", value="questions.txt")})
public class questionsServlet extends HttpServlet {

    private String nameOfFile;
    private String fileStream;
    private runTimeData dataBase = new runTimeData();

    /**
     *initializer to the main servlet - initialize members and define context
     */
    public void init() {
        try {
            nameOfFile = this.getServletConfig().getInitParameter("nameOfFile");
            fileStream = Files.readString(of(getServletContext().getRealPath(nameOfFile))).toLowerCase();
            dataBase.initQuestions(getQuestionsFromFile());

            ServletContext context = getServletContext();
            context.setAttribute("data",dataBase);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get questions from file string [ ].
     *a functio to extract all the questions from the file
     * @return the string [ ] = the questions from the file
     */
    public String[] getQuestionsFromFile(){
        String[] questions = fileStream.split("\n");
        return questions;
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     * this get is building a json for the data required to build the questions page (index.html)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonObject myJson = new JsonObject();
        String[] questions = getQuestionsFromFile();
        ServletContext context = getServletContext();

        if(context.getAttribute("data") instanceof runTimeData){

            runTimeData myDB = (runTimeData) context.getAttribute("data");
            HashMap<String, ArrayList<String>> myMap = myDB.getMap();
            int numOfQuestions = 0;
            int numOfAnswers = 0;

            for(String currQuestion : questions){
                currQuestion = currQuestion.replace("\r","");
                numOfAnswers = 0;

                if(myMap.get(currQuestion) != null){
                    numOfAnswers = myMap.get(currQuestion).size();
                }

                myJson.addProperty(numOfQuestions+"_question",currQuestion);
                myJson.addProperty(numOfQuestions+"_answers",numOfAnswers);
                numOfQuestions+=1;
            }
        }
        response.setContentType("application/json");
        PrintWriter output = response.getWriter();
        output.print(myJson);
        output.flush();
    }

    /**
     * default destructor
     */
    public void destroy() {
    }
}
