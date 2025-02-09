document.addEventListener("DOMContentLoaded", function() {
    var successMessage = document.getElementById("successMessage");
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.display = "none";
        }, 3000); // 3초 후에 메시지 숨기기
    }

    var errorMessage = document.getElementById("errorMessage");
    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.display = "none";
        }, 3000); // 3초 후에 메시지 숨기기
    }
});