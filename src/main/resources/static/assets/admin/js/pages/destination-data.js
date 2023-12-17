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
                const id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
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
                        else throw new Error("Error Status: " + response.status);
                    })
                    .then(result => console.log(result))
                    .catch(error => console.log('Error updating status:', error));
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

document.getElementById('update-destination').addEventListener('click', function(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
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

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error('Failed to update destination. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Update Destination', 'Success', 'green');
            $('#destinationDetailsModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Update Destination', 'Error', 'red'));
})

document.getElementById('add-destination').addEventListener('click', function(event) {
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
        .then(response => {
            if (response.ok) return response.text();
            else throw new Error('Failed to add destination. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Add Destination', 'Success', 'green');
            $('#newDestinationModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Add Destination', 'Error', 'red'));
})

function fetchDestinationDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('name', data.name);
    setElementValue('isHot', data.isHot);
    setElementValue('status', data.status);
    setElementValue('thumbnail', data.thumbnail);
}
