jQuery(document).ready(function($) {
    scrollToBottom();
    connect();

    $("#send-form").submit(function(event) {
        event.preventDefault();

        let images = document.getElementById('partImages').files;
        let files = document.getElementById('partFiles').files;
        let letter = $('#msg').val();

        const formData = new FormData();
        formData.append("letter", letter);

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
                sendMessage();
                $(".chat-history").load("main #add");
                $('#msg').val("");
                $('#partImages').val("");
                $('#partFiles').val("");
                scrollToBottom();
            }
        });

    });
});

function showMessage() {
    $(".chat-history").load("main #add");
}

function scrollToBottom() {
    $('#chat').animate({ scrollTop: $('#chat')[0].scrollHeight }, 400);
}

// WebSocket

let stompClient = null;

function connect() {
    const socket = new SockJS('/message-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function () {
            showMessage();
            scrollToBottom();
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/chat", {}, {});
}