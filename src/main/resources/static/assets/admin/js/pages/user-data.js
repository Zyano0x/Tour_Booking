document.addEventListener('DOMContentLoaded', function () {
    // Function to fetch data and populate the table
    function fetchData() {
        fetch("/api/admin/list-users")
            .then(response => response.json())
            .then(data => {
                fetchTable(data);
                feather.replace();
                lockListeners();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchTable(data) {
        let tableBody = document.querySelector('#user-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(user => {
            const row = document.createElement('tr');
            const badgeClass = user.accountNonLocked ? 'bg-primary' : 'bg-warning';
            const accountStatus = user.accountNonLocked ? 'Non Locked' : 'Locked';
            row.innerHTML = `
                <th scope="row">${user.id}</th>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.birthday}</td>
                <td>${user.phone}</td>
                <td><span class="badge ${badgeClass}">${accountStatus}</span></td>
                <td>
                    <a href="#" data-toggle="modal" data-target="#exampleModal"><i data-feather="edit"></i></a>
                    <a href="#" class="lock-icon"><i data-feather="lock"></i></a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    }

    function fetchUserDetails(email) {
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "text/plain");

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            body: email,
            redirect: 'follow'
        };

        fetch("/api/admin/user")
            .then(response => response.json())
            .then(data => {
                populateModal(data);
                $('#editModal').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching user details:', error));
    }

    function populateModal(userDetails) {
        document.getElementById('name').value = userDetails[0].name;
    }

    function viewListeners() {
        document.querySelectorAll('[data-toggle="modal"]').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                let email = item.parentElement.parentElement.querySelector('td:nth-child(3)').innerText;
                fetchUserDetails(email);
            });
        });
    }

    function lockListeners() {
        document.querySelectorAll('.lock-icon').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                let email = item.parentElement.parentElement.querySelector('td:nth-child(3)').innerText;
                updateStatus(email);
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);

    viewListeners();
});

function updateStatus(email) {
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "text/plain");

    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: email,
        redirect: 'follow'
    };

    fetch("/api/admin/update-user-status", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('Error updating user status:', error));
}
