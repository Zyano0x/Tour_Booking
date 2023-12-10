document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/type-of-tour/all")
            .then(response => response.json())
            .then(data => {
                fetchTableTourType(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchTypeDetails(id) {
        const url = `/api/type-of-tour?id=${encodeURIComponent(id)}`;
        console.log('Requesting type details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Received data:', data);
                fetchTourTypeDetailsModal(data);
                $('#typeDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching type details:', error));
    }

    function fetchTableTourType(data) {
        let tableBody = document.querySelector('#tour-type-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(type => {
            const row = document.createElement('tr');
            const badgeClass = type.status ? 'bg-primary' : 'bg-warning';
            const typeStatus = type.status ? 'Visible' : 'Hidden';
            row.innerHTML = `
                <th scope="row">${type.id}</th>
                <td>${type.name}</td>
                <td>${type.description}</td>
                <td><span class="badge ${badgeClass}">${typeStatus}</span></td>
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
                let id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                fetchTypeDetails(id);
            });
        });
    }

    function lockAction() {
        document.querySelectorAll('.lock-icon').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                let id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                updateStatus(id);
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

function updateStatus(id) {
    const url = `/api/admin/update-type-of-tour-status?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch(url, requestOptions)
        .then(response => response.json())
        .then(result => console.log(result))
        .catch(error => console.log('Error updating status:', error));
}

function updateTypeTour(id) {
    event.preventDefault();

    const url = `/api/admin/update-type-of-tour?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    // Example: Get values from your form fields
    const name = document.getElementById('name').value;
    const description = document.getElementById('content').value;
    const status = document.getElementById('status').value;

    // Construct the request body based on the TourDTO structure
    let raw = JSON.stringify({
        "name": name,
        "description": description,
        "status": status,
    });

    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch(url, requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log('Type tour updated successfully:', result);
            showToast('Type tour updated successfully');
            $('#typeDetailsModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error updating type tour:', error));
}

function addTypeTour() {
    event.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const name = document.getElementById('_name').value;
    const description = document.getElementById('_content').value;

    let raw = JSON.stringify({
        "name": name,
        "description": description,
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch("/api/admin/type-of-tour", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log('Type Tour added successfully:', result);
            showToast('Type Tour added successfully');
            $('#newTypeModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error adding tour type:', error));
}

function fetchTourTypeDetailsModal(data) {
    document.getElementById('id').value = data.id;
    document.getElementById('name').value = data.name;
    document.getElementById('content').value = data.description;
    document.getElementById('status').value = data.status;
}
