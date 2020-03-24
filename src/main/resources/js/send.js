jQuery(document).ready(function($) {
    $("#send-form").submit(function(event) {
        event.preventDefault();
        sendMessage();
    });
});
function sendMessage() {
    $.ajax ({
        url: "/main",
        type: "POST",
        data: {letter:$("#msg")},
        dataType: "html"
    });
}