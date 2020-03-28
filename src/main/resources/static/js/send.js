jQuery(document).ready(function($) {
    scrollToBottom();
    $("#send-form").submit(function(event) {
        event.preventDefault();
        var message = document.getElementById("msg").value;
        $.ajax ({
            url: "/main",
            type: "POST",
            cache: false,
            data: {letter:message},
            dataType: "html",
            success: function() {
                $(".chat-history").load("main #add");
                document.getElementById("msg").value = "";
                scrollToBottom();
            }
        });
    });
});

function scrollToBottom() {
    // $("#chat").scrollTop($("#chat")[0].scrollHeight);
    $('#chat').animate({ scrollTop: $('#chat')[0].scrollHeight }, 400);
}