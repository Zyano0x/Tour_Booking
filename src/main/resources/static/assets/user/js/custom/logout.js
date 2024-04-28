document.getElementById('exit_link').addEventListener('click', async function (event) {
    event.preventDefault();
    await logout();
})

async function logout() {
    try {
        const res = await fetch("/api/v1/auth/logout", {
            method: "POST",
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