package com.example.ex2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The type Answer servlet.
 */
@WebServlet(name = "answerServlet", value = "/Answer")
public class answerServlet extends HttpServlet {
    private String question;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * the Get handler is building the answer page
     * building the html dynamic
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter outPut = response.getWriter();
        question = request.getParameter("question");
        if (question != null) {
            outPut.println("<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                    "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">\n" +
                    "    <link rel=\"stylesheet\" href=\"./styles/styles.css\">\n" +
                    "    <script src=\"javascript/formJs.js\"></script>\n" +
                    "    <title>Ex.2</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body id=\"answer_page\">\n" +
                    "<div class=\"form-row\">\n" +
                    "    <div id=\"personal-info-block\" class=\"form-group col-md-7 col-lg-7 col-xl-7 col-sm-12 col-12 block keep-flex\">\n" +
                    "        <form id=\"answerForm\" action=\"/Answer\" method=\"post\" accept-charset=\"UTF-8\" id=\"answer-form\">\n" +
                    "            <h2 style=\"font-weight: bold; text-decoration: underline;\">Answer this question:</h2>\n" +
                    "            <h4 style=\"font-weight: bold\" name=\"_question\">" + question + "</h4>\n" +
                    "            <div class=\"container-fluid\">\n" +
                    "                <div class=\"form-row\">\n" +
                    "                </div>\n" +
                    "                <div class=\"form-row\">\n" +
                    "                    <label for=\"nameId\">Your name</label>\n" +
                    "                    <input type=\"text\" class=\"form-control\" name=\"name_\" id=\"nameId\" required>\n" +
                    "                </div>\n" +
                    "                <div class=\"form-row\">\n" +
                    "                </div>\n" +
                    "                <div class=\"form-row\">\n" +
                    "                    <label for=\"answerId\">Your answer</label>\n" +
                    "                    <input type=\"text\" class=\"form-control\" name=\"answer_\" id=\"answerId\" required>\n" +
                    "                </div>\n" +
                    "                <br>\n" +
                    "                 <div class=\"form-row pb-4\">\n" +
                    "                    <button id=\"submitAnswerButton\" class=\"btn btn-primary\" style=\"margin:5px;\">Submit</button>\n" +
                    "                    <button id=\"resetFormButton\" class=\"btn btn-primary\" style=\"margin:5px;\">Reset</button>\n" +
                    "                </div>\n" +
                    "                <p><a href=\"/index.html\" >Click here to go back</a></p>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<br>\n" +
                    "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" +
                    "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\n" +
                    "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\n" +
                    "</body>\n" +
                    "</html>");
            outPut.close();
        }else{
            response.sendRedirect("index.html");
        }
    }
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * the post handler is receiving the input from the answer page and inserting the data to the dynamic database (runTimeDate class)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        if(context.getAttribute("data") instanceof runTimeData){
            runTimeData myDB = (runTimeData) context.getAttribute("data");
            String currentAnswer = request.getParameter("name_")+":\n"+request.getParameter("answer_");
            myDB.addAnswer(question, currentAnswer);
        }
        response.sendRedirect("index.html");
    }
}

