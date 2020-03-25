jQuery(document).ready(function($) {
    $("#send-form").submit(function(event) {
        event.preventDefault();
        var message = document.getElementById("msg").value;
        $.ajax ({
            url: "/main",
            type: "POST",
            cache: false,
            data: {letter:message},
            dataType: "html",
            success: function(){
                $(".chat-history").load("main #add");
                document.getElementById("msg").value = "";
            }
        });
    });
});