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

function handleLogout() {
    fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json()) // Parse the response to JSON
        .then(data => {
            if (data.success) {
                window.location.href = "/";
            } else {
                console.error("Logout failed", data.message);
            }
        })
        .catch(error => {
            console.error("Error logging out", error);
        });
}

const exitLink = document.getElementById('exit_link');
exitLink.addEventListener('click', handleLogout);