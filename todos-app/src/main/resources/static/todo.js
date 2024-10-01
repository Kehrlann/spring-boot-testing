document.addEventListener("DOMContentLoaded", () => {

    let displayed = false;

    document.querySelectorAll(".todo").forEach(
        (element) => {
            const description = element.querySelector(`[data-role="description"]`)
            element.querySelector(`[data-role="toggle"]`).addEventListener("click", () => {
                const visibility = description.style.display
                console.log("clicking " + description.style.display)
                description.style.display = visibility !== "inline" ? "inline" : "none";
            })
        }
    )

    document.querySelector("#toggle-all").addEventListener("click",
        (evt) => {
            displayed = !displayed;
            for (const description of document.querySelectorAll(`[data-role="description"]`)) {
                description.style.display = displayed ? "inline" : "none";
            }
        }
    )


    document.getElementById("clear-button").addEventListener("click", (evt) => {
        evt.preventDefault();
        window.location.href = "/";
    })

})