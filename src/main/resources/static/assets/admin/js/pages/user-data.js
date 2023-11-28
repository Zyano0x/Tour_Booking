document.addEventListener('DOMContentLoaded', function () {
    // Function to fetch data and populate the table
    function fetchData() {
        fetch("/api/admin/list-users")
            .then(response => response.json())
            .then(data => {
                fetchTable(data);
                feather.replace();
                lockAction();
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
                    <a href="#" data-toggle="modal" data-target="#exampleModal" onclick="fetchUserDetails('${user.email}')"><i data-feather="eye"></i></a>
                    <a href="#" class="lock-icon"><i data-feather="lock"></i></a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    }

    function lockAction() {
        document.querySelectorAll('.lock-icon').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                let email = item.parentElement.parentElement.querySelector('td:nth-child(3)').innerText;
                updateStatus(email);
            });
        });
    }

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

    fetchData();
    setInterval(fetchData, 1000);
});

function fetchUserDetails(email) {
    const url = `/api/admin/user?email=${encodeURIComponent(email)}`;
    console.log('Requesting user details:', url);

    fetch(url)
        .then(response => {
            console.log('Response status:', response.status);
            return response.json();
        })
        .then(data => {
            console.log('Received data:', data);
            getDataToModal(data);
            $('#exampleModal').modal('show'); // Show the modal
        })
        .catch(error => console.error('Error fetching user details:', error));
}

function getDataToModal(data) {
    document.getElementById('name').value = data.name;
    document.getElementById('username').value = data.username;
    document.getElementById('email').value = data.email;
    document.getElementById('gender').value = data.gender;
    document.getElementById('birthday').value = data.birthday;
    document.getElementById('address').value = data.address;
    document.getElementById('cid').value = data.cid;
}

function closeModal() {
    $('#exampleModal').modal('hide');
}