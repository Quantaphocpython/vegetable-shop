

function showAlertLeaveMessage(event) {
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/contact/sendMessage",
        data: $("#contact-form").serialize(),
        success: function (data) {
            $(".contact-message-input").val("")
            let alert = $('.contact-alert');
            alert.show();
            setTimeout(function() {
                alert.hide();
            }, 5000);
        }
    });
}