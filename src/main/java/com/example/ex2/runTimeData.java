package com.example.ex2;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Run time data.
 * a class that saves and handles all the data on runtime
 */
public class runTimeData {

   private HashMap<String, ArrayList<String>> myDB = new HashMap<>();
   private ArrayList<String> _questions = new ArrayList<>();
    /**
     * Get map hash map.
     *
     * @return the hash map
     */
    public HashMap<String, ArrayList<String>> getMap(){
       return this.myDB;
   }
    /**
     * Add answer.
     *
     * @param question      the question
     * @param currentAnswer the current answer
     * function to add a new answer to a question - a new element to the map
     */
    public void addAnswer(String question, String currentAnswer){

       ArrayList<String> answersArr = new ArrayList<>();

       if(myDB.get(question) != null){
           answersArr = myDB.get(question);
       }
       answersArr.add(currentAnswer);
       myDB.put(question, answersArr);
   }

    /**
     * Get question string.
     *
     * @param id the id
     * @return the string
     */
    public String getQuestion(int id){
       return _questions.get(id);
   }

    /**
     * Init questions.
     *
     * @param questions the questions
     *function to initialize the members of this class
     *the map and the list,
     */
    public void initQuestions(String[] questions){
   for(String currentQue : questions){
            myDB.put(currentQue, new ArrayList<>());
            _questions.add(currentQue);
        }
   }
}