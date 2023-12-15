document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/destinations")
            .then(response => response.json())
            .then(data => {
                fetchTableDestination(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchDestinationDetails(id) {
        const url = `/api/destination?id=${encodeURIComponent(id)}`;
        console.log('Requesting type details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Received data:', data);
                fetchDestinationDetailsModal(data);
                $('#destinationDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching type details:', error));
    }

    function fetchTableDestination(data) {
        let tableBody = document.querySelector('#destination-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(destination => {
            const row = document.createElement('tr');
            const badgeClass = destination.status ? 'bg-primary' : 'bg-warning';
            const destinationStatus = destination.status ? 'Visible' : 'Hidden';
            row.innerHTML = `
                <th scope="row">${destination.id}</th>
                <td>${destination.name}</td>
                <td><a href="${destination.thumbnail}" target="_blank">${destination.thumbnail}</a></td>
                <td>${destination.isHot}</td>
                <td><span class="badge ${badgeClass}">${destinationStatus}</span></td>
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
                fetchDestinationDetails(id);
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
    const url = `/api/admin/update-destination-status?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(result => console.log(result))
        .catch(error => console.log('Error updating status:', error));
}

function updateDestination(id) {
    event.preventDefault();

    const url = `/api/admin/update-destination?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const name = document.getElementById('name').value;
    const isHot = document.getElementById('isHot').value;
    const status = document.getElementById('status').value;
    const thumbnail = document.getElementById('thumbnail').value;

    let raw = JSON.stringify({
        "name": name,
        "isHot": isHot,
        "status": status,
        "thumbnail": thumbnail
    });

    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(result => {
            console.log('Destination updated successfully:', result);
            showToast('Destination updated successfully');
            $('#destinationDetailsModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error updating destination:', error));
}

function addDestination() {
    event.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const name = document.getElementById('_name').value;
    const isHot = document.getElementById('_isHot').value;
    const thumbnail = document.getElementById('_thumbnail').value;

    let raw = JSON.stringify({
        "name": name,
        "isHot": isHot,
        "thumbnail": thumbnail,
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch("/api/admin/destination", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log('Destination added successfully:', result);
            showToast('Destination added successfully');
            $('#newDestinationModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error adding destination:', error));
}

function fetchDestinationDetailsModal(data) {
    document.getElementById('id').value = data.id;
    document.getElementById('name').value = data.name;
    document.getElementById('isHot').value = data.isHot;
    document.getElementById('status').value = data.status;
    document.getElementById('thumbnail').value = data.thumbnail;
}
