document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("new-todo");

    input.addEventListener("input", () => {
        if (input.value && !input.value.match(/^[a-z0-9\s_-]+$/)) {
            input.setCustomValidity("Only lower case characters are allowed")
        } else {
            input.setCustomValidity("")
        }
    });

})