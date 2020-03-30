jQuery(document).ready(function($) {
    scrollToBottom();

    $("#send-form").submit(function(event) {
        event.preventDefault();

        const formData = new FormData();
        formData.append("letter", $('#msg'));
        formData.append("multipartFile", $('#part')[0].files[0]);

        $.ajax ({
            url: "/main",
            type: "POST",
            data: formData,
            dataType: "html",
            processData: false,
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            success: function() {
                $(".chat-history").load("main #add");
                $('#msg').val("");
                scrollToBottom();
            }
        });

    });

    // $('.uploadFile').change(function() {
    //     if ($(this).val() !== '') $(this).prev().text('Выбрано файлов: ' + $(this)[0].files.length);
    //     else $(this).prev().text('Выберите файлы');
    // });
});

function scrollToBottom() {
    // $("#chat").scrollTop($("#chat")[0].scrollHeight);
    $('#chat').animate({ scrollTop: $('#chat')[0].scrollHeight }, 400);
}