document.querySelector("#register").addEventListener('submit', async function (event) {
   event.preventDefault();

   await signUp()
});

async function signUp() {
   try {
      const fullName = document.getElementById('fullname').value;
      const username = document.getElementById('username').value;
      const email = document.getElementById('email').value;
      const birthday = document.getElementById('birthday').value;
      const gender = document.getElementById('gender').value;
      const address = document.getElementById('address').value;
      const cid = document.getElementById('cid').value;
      const phone = document.getElementById('phone').value;
      const password = document.getElementById('password1').value;

      const res = await fetch("/api/v1/auth/register", {
         method: "POST",
         headers: {
            "Content-Type": "application/json"
         },
         body: JSON.stringify({
            name: fullName,
            username,
            email,
            password,
            birthday,
            gender,
            address,
            cid,
            phone
         })
      });

      if (res.status === 201) {
         window.setTimeout(() => {
            window.location.assign('/');
         }, 1000);
      }
   } catch (e) {
      console.error(e);
   }
}