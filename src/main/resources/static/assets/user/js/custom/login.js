document.querySelector("#form-login").addEventListener('submit', function (event) {
    event.preventDefault();

    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;

    login(email, password);
});

function login(email, password) {
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    let raw = JSON.stringify({
        email: email,
        password: password,
    });

    let requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
    };

    fetch("/api/auth/login", requestOptions)
        .then((response) => {
            if (response.ok) return response.json();
            else throw new Error("Error: " + response.statusText);
        })
        .then((result) => {
            window.location.href = "/";
        })
        .catch((error) => console.error("Login Error:", error.message));
}

document.getElementById('exit_link').addEventListener('click', function (event) {
    event.preventDefault();

    fetch("/api/auth/logout", {method: "POST"})
        .then((response) => {
            if (response.ok) return response.text();
            else throw new Error("Error: " + response.statusText);
        })
        .then((result) => {
            window.location.href = "/";
        })
        .catch((error) => console.log(error));
})