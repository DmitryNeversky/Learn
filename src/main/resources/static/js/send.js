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
                send("/app/chat");
                $('#msg').val("");
                $('#partImages').val("");
                $('#partFiles').val("");
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

let socket = new SockJS('/message-socket');
let stompClient = Stomp.over(socket);

function connect() {
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        send("/app/status", true);

        stompClient.subscribe('/receive/chat', function () {
            showMessage();
            scrollToBottom();
        });

        stompClient.subscribe('/receive/status', function () {
            $(".user-list").load("main #userTable");
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        send("/app/status", false);
        stompClient.disconnect();
    }
}

function send(path, data) {
    stompClient.send(path, {}, data);
}