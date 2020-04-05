jQuery(document).ready(function($) {
    scrollToBottom();

    $("#send-form").submit(function(event) {
        event.preventDefault();

        let files = document.getElementById('part').files;

        const formData = new FormData();
        formData.append("letter", $('#msg').val());

        for(let i = 0; i < files.length; i++) {
            formData.append('multipartFiles', files[i]);
        }

        $.ajax ({
            url: "/main",
            type: "POST",
            data: formData,
            dataType: "html",
            processData: false,
            contentType: false,
            success: function() {
                $(".chat-history").load("main #add");
                $('#msg').val("");
                $('#part').val("");
                scrollToBottom();
            }
        });

    });

    function showImage(fileName) {
        console.log(fileName);
    }
});

function scrollToBottom() {
    $('#chat').animate({ scrollTop: $('#chat')[0].scrollHeight }, 400);
}