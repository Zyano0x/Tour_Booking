document.querySelector("#register").addEventListener('submit', function (event) {
   const fullName = document.getElementById('fullname').value;
   const username = document.getElementById('username').value;
   const email = document.getElementById('email').value;
   const birthday = document.getElementById('birthday').value;
   const gender = document.getElementById('gender').value;
   const address = document.getElementById('address').value;
   const cid = document.getElementById('cid').value;
   const phone = document.getElementById('phone').value;
   const password = document.getElementById('password1').value;

   const myHeaders = new Headers();
   myHeaders.append("Content-Type", "application/json");

   const raw = JSON.stringify({
      "name": fullName,
      "username": username,
      "email": email,
      "password": password,
      "birthday": birthday,
      "gender": gender,
      "address": address,
      "cid": cid,
      "phone": phone
   });

   const requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
      redirect: 'follow'
   };

   fetch("/api/auth/register", requestOptions)
       .then(response => {
          if (response.ok) return response.json();
          else throw new Error("Error Status: " + response.status);
       })
       .then(result => {
          window.location.href = "/";
       })
       .catch(error => console.log('Register Error', error));
});