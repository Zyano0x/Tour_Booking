document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/admin/users-manage")
            .then(response => response.json())
            .then(data => {
                fetchTableUser(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error(error));
    }

    function fetchUserDetails(email) {
        const url = `/api/v1/admin/users/${encodeURIComponent(email)}`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                fetchDataModal(data);
                $('#exampleModal').modal('show'); // Show the modal
            })
            .catch(error => console.error(error));
    }

    function fetchTableUser(data) {
        let tableBody = document.querySelector('#user-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(user => {
            const row = document.createElement('tr');
            const badgeClass = user.accountNonLocked ? 'bg-primary' : 'bg-warning';
            const accountStatus = user.accountNonLocked ? 'Non Locked' : 'Locked';
            row.innerHTML = `
                <th scope="row">${user.id}</th>
                <td>${user.name}</td>
                <td>${user.username}</td>
                <td>${user.birthday}</td>
                <td>${user.phone}</td>
                <td><span class="badge ${badgeClass}">${accountStatus}</span></td>
                <td>
                    <a href="#" class="eye-icon"><i data-feather="eye"></i></a>
                    <a href="#" class="lock-icon"><i data-feather="lock"></i></a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    }

    function eyeAction() {
        document.querySelectorAll('.eye-icon').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                let email = item.parentElement.parentElement.querySelector('td:nth-child(3)').innerText;
                fetchUserDetails(email);
            });
        });
    }

    function lockAction() {
        document.querySelectorAll('.lock-icon').forEach(item => {
            item.addEventListener('click', async event => {
                event.preventDefault();
                let id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                await updateStatus(id);
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

async function updateStatus(id) {
    try {
        await fetch(`/api/v1/admin/update-user-status/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email,
            }),
        });
    } catch (e) {
        console.error( e);
    }
}

function fetchDataModal(data) {
    document.getElementById('name').value = data.name;
    document.getElementById('username').value = data.email;
    document.getElementById('email').value = data.username;
    document.getElementById('gender').value = data.gender;
    document.getElementById('birthday').value = data.birthday;
    document.getElementById('address').value = data.address;
    document.getElementById('cid').value = data.cid;
}
