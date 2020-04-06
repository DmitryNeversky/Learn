jQuery(document).ready(function($) {
    scrollToBottom();

    $("#send-form").submit(function(event) {
        event.preventDefault();

        let images = document.getElementById('partImages').files;
        let files = document.getElementById('partFiles').files;

        const formData = new FormData();
        formData.append("letter", $('#msg').val());

        for(let i = 0; i < images.length; i++)
            formData.append('multipartImages', images[i]);
        for(let i = 0; i < files.length; i++)
            formData.append('multipartFiles', files[i]);

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
                $('#partImages').val("");
                $('#partFiles').val("");
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