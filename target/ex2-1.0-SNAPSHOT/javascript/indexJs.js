document.addEventListener('DOMContentLoaded',function(){            //Listeners
    if(document.getElementById("question_page") != null) {
        loadedPage();
    }
}, false);

/**
 * a function to fetch the questions in order to build the main page
 */
function loadedPage(){                   //a function to take several actions when the dom is loaded
    fetch('./Questions')
        .then(
            function (response) {
                if (response.status !== 200) {          // handle the error
                    console.log('Looks like there was a problem.');
                    response.status;
                    return;
                }
                response.json().then(function (data) {  //handle the response => convert to json
                    if (data.error){
                        console.log('Looks like there was a problem.');
                    }else{
                        buildQuestionsBlock(data);
                    }
                });
            })
        .catch(function (err) {         //handle error
            console.log('Fetch Error :', err);
        });
}

/**
 * a function that gets the json of the questions and build the question blocks of the main page.
 * @param data = json of the questions
 */
function buildQuestionsBlock(data){
    let i;
    var idCounter = 0;
    var questionBlock = '';
    for(i = 0; i < Object.keys(data).length; i++) {
        if (i % 2 == 0) {
            questionBlock += '<div id="' + idCounter + '_questionDiv" class="row container-fluid border border-secondary '
                + 'block" style="background-color: #e9ecef; margin:8px;"><br>'
                + '<div class="row container-fluid my-first-container question-block">'
                + '<h4>'
                + '<b>' + data[idCounter+"_question"]
                + '</b></h4></div>'
                + '<div class="row container-fluid my-first-container">'
                + '<p id="' + idCounter + '_counter" style="text-decoration: underline;">' + data[idCounter+"_answers"] + ' answers</p></div>'
                + '<br>'
                + '<div id="' + idCounter + '_buttonsDiv" class="row container-fluid my-first-container" style="display: block">'
                + '<button class="btn btn-secondary" style="margin:5px;" id="' + idCounter + '" onclick="showPressed(this.id)">'
                + '<b>Show answers</b></button>'
                + '<a href="/Answer?question=' + data[idCounter+"_question"] + '" class="btn btn-secondary"'
                + 'style="margin:5px;">'
                + '<b>Add answer</b></a></div><br>'
                + '<div id="' + idCounter + '_answerDiv"'
                + 'class="row container-fluid my-first-container" style="display: none"></div>'
                + '<button class="btn btn-secondary" id="' + idCounter + '_hideButton' + '" onclick="hidePressed(this.id)" style="display: none; margin:5px;">'
                + '<b>hide</b></button></div>'
            idCounter++;
        }
    }
    document.getElementById("questionsBlock").innerHTML = questionBlock;
}

/**
 * if show pressed, fetch the answers
 * @param id
 */
function showPressed(id){
    fetchAnswers(id);
}

/**
 * if hide pressed, hide needed
 * @param id
 */
function hidePressed(id){
    let tempId = id.split("_");
    document.getElementById(tempId[0]+'_answerDiv').style.display = "none";
    document.getElementById(tempId[0]+'_hideButton').style.display = "none";
}

/**
 * fetch the answers for display
 * @param id
 */
function fetchAnswers(id){
    fetch('./getAnswers?id=' + id)
        .then(
            function (response) {
                if (response.status !== 200) {          // handle the error
                    console.log('Looks like there was a problem.');
                    response.status;
                    return;
                }
                response.json().then(function (data) {  //handle the response => convert to json
                    if (data.error){
                        console.log('Looks like there was a problem.');
                    }else{
                        if(Object.keys(data).length > 1) {
                        buildAnswersBlock(data);
                            displayMode(id);
                        }
                    }
                });
            })
        .catch(function (err) {         //handle error
            console.log('Fetch Error :', err);
        });
}

/**
 * a function to active some display for viewing the answers
 * @param id
 */
function displayMode(id){
    document.getElementById(id+'_answerDiv').style.display = "block";
    document.getElementById(id+'_hideButton').style.display = "block";
}

/**
 * a function to build the answer block after fetching the data (name and answer)
 * @param data
 */
function buildAnswersBlock(data){
    let i;
    var answerBlock = '';
    let numOfAnswer = 0;

    for(i = 0; i < Object.keys(data).length; i++) {
        if (i % 2 == 0 && i != Object.keys(data).length-1) {

            answerBlock +=
                '<div class="border border-secondary" style="margin:6px; padding: 3px 10px 0px 10px; background-color: '
                +'ghostwhite; container-fluid:align; border-radius: 20px">'
                +'<p style="text-shadow: 2px 2px 5px deepskyblue; text-decoration: underline; font-weight: bold">'
                + data[numOfAnswer+"_name"] + '</p> '
                +'<p>' + data[numOfAnswer+"_answer"] + '</p></div>';
            numOfAnswer+=1;
        }
    }
    document.getElementById(data["_questionNum"]+"_answerDiv").innerHTML = answerBlock;
}
