document.addEventListener('DOMContentLoaded',function(){            //Listeners
    loadedAction();
    document.getElementById("resetFormButton").addEventListener('click', function () {
        resetForm();});
    document.getElementById("submitAnswerButton").addEventListener('click', function () {
        submitForm();});
}, false);
function loadedAction() {
    $('answerForm').submit(function(e){e.preventDefault();});
}
function resetForm(){
    document.getElementById("answerForm").reset();
}
function submitForm(){
    document.getElementById("answerForm").submit();
}

