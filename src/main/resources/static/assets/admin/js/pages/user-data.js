document.addEventListener('DOMContentLoaded', function () {
    // Fetch data from the server (replace 'users.json' with your actual API endpoint)
    fetch("/api/list-users")
        .then(response => response.json())
        .then(data => {
            // Populate the table with the fetched data
            let tableBody = document.querySelector('#user-table tbody');
            data.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                  <th scope="row">${user.id}</th>
                  <td>${user.name}</td>
                  <td>${user.email}</td>
                  <td>${user.birthday}</td>
                  <td>${user.phone}</td>
                  <td>
                    <a href="#"><i data-feather="edit"></i></a>
                    <a href="#"><i data-feather="lock"></i></a>
                  </td>
                `;
                tableBody.appendChild(row);
            });
            feather.replace();
        })
        .catch(error => console.error('Error fetching data:', error));
});