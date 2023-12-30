document.getElementById('exit_link').addEventListener('click', function (event) {
    event.preventDefault();

    fetch("/api/auth/logout", { method: "POST" })
        .then((response) => {
            if (response.ok) return response.text();
            else throw new Error("Error: " + response.statusText);
        })
        .then((result) => {
            window.location.href = "/";
        })
        .catch((error) => console.log(error));
})