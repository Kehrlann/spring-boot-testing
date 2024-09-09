document.addEventListener("DOMContentLoaded", () => {

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

})