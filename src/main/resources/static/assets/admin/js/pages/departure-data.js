document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/departure-day/all")
            .then(response => response.json())
            .then(data => {
                fetchTableDeparture(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchDepartureDetails(id) {
        const url = `/api/departure-day?id=${encodeURIComponent(id)}`;
        console.log('Requesting type details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Received data:', data);
                fetchDepartureDetailsModal(data);
                $('#departureDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching departure details:', error));
    }

    function fetchTableDeparture(data) {
        let tableBody = document.querySelector('#departure-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(departure => {
            const row = document.createElement('tr');
            const badgeClass = departure.status ? 'bg-primary' : 'bg-warning';
            const departureStatus = departure.status ? 'Visible' : 'Hidden';
            row.innerHTML = `
                <th scope="row">${departure.id}</th>
                <td>${departure.departureDay}</td>
                <td>${departure.quantity}</td>
                <td>${departure.tour.name}</td>
                <td><span class="badge ${badgeClass}">${departureStatus}</span></td>
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
                fetchDepartureDetails(id);
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
    const url = `/api/admin/update-departure-day-status?id=${encodeURIComponent(id)}`;

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

async function updateDeparture(id) {
    event.preventDefault();

    const url = `/api/admin/update-departure-day?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const day = document.getElementById('departure').value; //TODO: Fix departure day
    const slots = document.getElementById('slots').value;
    const status = document.getElementById('status').value;
    const tourId = await getTourIdByName(document.getElementById('tourDropdownUpdate').value);

    let raw = JSON.stringify({
        "departureDay": day,
        "quantity": slots,
        "status": status,
        "tourId": tourId
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
            console.log('Departure day updated successfully:', result);
            showToast('Departure day updated successfully');
            $('#departureDetailsModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error updating destination:', error));
}

async function addDeparture() {
    event.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const day = document.getElementById('_departure').value; //TODO: Fix departure day
    const slots = document.getElementById('_slots').value;
    const status = document.getElementById('_status').value;
    const tourId = await getTourIdByName(document.getElementById('tourDropdownNew').value);

    let raw = JSON.stringify({
        "departureDay": day,
        "quantity": slots,
        "status": status,
        "tourId": tourId
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch("/api/admin/departure-day", requestOptions)
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(result => {
            console.log('Departure day added successfully:', result);
            showToast('Departure day added successfully');
            $('#newDepartureModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error adding departure day:', error));
}

function dropdownWithTour(currentTour) {
    const dropdownUpdate = document.getElementById('tourDropdownUpdate');

    fetch('/api/tour/all')
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(tours => {
            dropdownUpdate.innerHTML = "";

            tours.forEach(tour => {
                const option = document.createElement("option");
                option.value = tour.name;
                option.text = tour.name;

                if (currentTour && tour.id === currentTour.id)
                    option.selected = true;

                dropdownUpdate.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching tours:', error);
        });
}

function fetchDepartureDetailsModal(data) {
    document.getElementById('id').value = data.id;
    document.getElementById('departure').value = data.departureDay;
    document.getElementById('slots').value = data.quantity;
    document.getElementById('status').value = data.status;
    dropdownWithTour(data.tour);
}

async function getTourOptions() {
    try {
        const response = await fetch('/api/tour/all');
        if (response.ok) return await response.json();
    } catch (error) {
        console.error('Error fetching tour options:', error);
        return [];
    }
}

async function getTourIdByName(name) {
    const tourOptions = await getTourOptions(); // Wait for the promise to resolve
    const tour = tourOptions.find(option => option.name === name);
    return tour ? tour.id : null;
}

async function fetchTours() {
    try {
        const tours = await fetch('/api/tour/all').then(response => response.json());

        populateDropdown('tourDropdownNew', tours);
    } catch (error) {
        console.error('Error fetching data options:', error);
    }
}
fetchTours();