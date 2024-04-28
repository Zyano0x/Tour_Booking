document.querySelector("#form-login").addEventListener('submit', async function (event) {
    event.preventDefault();

    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;

    await login(email, password);
});

async function login(email, password) {
    try {
        const res = await fetch("/api/v1/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email,
                password,
            }),
        });

        if (res.status === 200) {
            window.setTimeout(() => {
                window.location.assign("/");
            }, 1500);
        }
    } catch (err) {
        console.error(err);
    }
}