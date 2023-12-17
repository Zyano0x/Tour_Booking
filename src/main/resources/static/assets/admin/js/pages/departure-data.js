document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/departure-days")
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
                const id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                const url = `/api/admin/update-departure-day-status?id=${encodeURIComponent(id)}`;

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

document.getElementById('update-day').addEventListener('click', function(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
    const url = `/api/admin/update-departure-day?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const day = document.getElementById('departure').value;
    const slots = document.getElementById('slots').value;
    const status = document.getElementById('status').value;
    const tourId = document.getElementById('tours').value;

    let raw = JSON.stringify({
        "departureDay": day,
        "quantity": slots,
        "status": status,
        "tourId": tourId
    });

    console.log('Parsed Luxon Date:', day);

    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error('Failed to update departure day. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Update Departure Day', 'Success', 'green');
            $('#departureDetailsModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Update Departure Day', 'Error', 'red'));
})

document.getElementById('add-day').addEventListener('click', function(event) {
    event.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const day = document.getElementById('_departure').value;
    const slots = document.getElementById('_slots').value;
    const status = document.getElementById('_status').value;
    const tourId = document.getElementById('_tours').value;

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

    fetch("/api/admin/departure-day", requestOptions)
        .then(response => {
            if (response.ok) return response.text();
            else throw new Error('Failed to update type tour. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Add Departure Day', 'Success', 'green');
            $('#newDepartureModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Add Departure Day', 'Error', 'red'));
})

function fetchDepartureDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    console.log(data.departureDay);
    const formattedDate = formatDate(data.departureDay, 'dd-mm-yyyy');

    setElementValue('id', data.id);
    setElementValue('departure', formattedDate);
    setElementValue('slots', data.quantity);
    setElementValue('status', data.status);
    setElementValue('tours', data.tour.id);
}

function formatDate(isoDate, inputFormat) {
    const parseDateFromString = (dateString) => {
        const parts = dateString.split('-');
        if (parts.length === 3) {
            const day = parseInt(parts[0], 10);
            const month = parseInt(parts[1], 10) - 1;
            const year = parseInt(parts[2], 10);

            if (!isNaN(day) && !isNaN(month) && !isNaN(year)) {
                return new Date(year, month, day);
            }
        }
        return null;
    };

    const date = inputFormat === 'dd-mm-yyyy' ? parseDateFromString(isoDate) : new Date(isoDate);

    if (date instanceof Date && !isNaN(date.getTime())) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');

        return `${year}-${month}-${day}`;
    } else {
        return 'Invalid date format';
    }
}

import {fetchData} from './utils.js';

fetchData('/api/tours', function(data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function(typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, 'tours');

fetchData('/api/tours', function(data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function(typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, '_tours');