const loginForm = document.querySelector('form'); // Keep only one declaration
const logoutBtn = document.getElementById('logout');

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
                window.location.assign("/panel/dashboard");
            }, 1500);
        }
    } catch (e) {
        console.error(e);
    }
}

if (loginForm) {
    loginForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        let email = document.querySelector('#email').value;
        let password = document.querySelector('#password').value;

        await login(email, password);
    });
}

async function logout() {
    try {
        const res = await fetch("/api/v1/auth/logout", {
            method: "POST"
        });

        if (res.status === 200) {
            window.setTimeout(() => {
                window.location.assign("/panel/login");
            },1500);
        }
    } catch (e) {
        console.error(e);
    }
}

if (logoutBtn) {
    logoutBtn.addEventListener('click', async function () {
        await logout();
    })
}
