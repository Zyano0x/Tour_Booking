document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/departure-days")
            .then(response => response.json())
            .then(data => {
                fetchTableDeparture(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error(error));
    }

    function fetchDepartureDetails(id) {
        const url = `/api/v1/departure-days/${encodeURIComponent(id)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                fetchDepartureDetailsModal(data);
                $('#departureDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error(error));
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
                const url = `/api/v1/admin/update-departure-day-status/${encodeURIComponent(id)}`;

                let myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/json");
                let requestOptions = {
                    method: 'PUT', headers: myHeaders, redirect: 'follow'
                };

                fetch(url, requestOptions)
                    .then(response => response.json())
                    .then(result => console.log(result))
                    .catch(error => console.error(error));
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

async function updateDepartureDays() {
    try {
        const id = document.getElementById('id').value;
        const day = document.getElementById('departure').value;
        const time = document.getElementById('departureTime').value;
        const slots = document.getElementById('slots').value;
        const status = document.getElementById('status').value;
        const tourId = document.getElementById('tours').value;

        const res = await fetch(`/api/v1/admin/update-departure-days/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                departureDay: day,
                departureTime: time,
                quantity: slots,
                status,
                tourId
            })
        });

        if (res.status === 200) {
            showToast('Succeed Update Departure Day', 'Success', 'green');
            $('#departureDetailsModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Update Departure Day', 'Error', 'red')
    }
}
document.getElementById('update-day').addEventListener('click', async function (event) {
    event.preventDefault();

    await updateDepartureDays();
})

async function addDepartureDays() {
    try {
        const day = document.getElementById('_departure').value;
        const time = document.getElementById('_departureTime').value;
        const slots = document.getElementById('_slots').value;
        const status = document.getElementById('_status').value;
        const tourId = document.getElementById('_tours').value;

        const res = await fetch("/api/v1/admin/departure-days", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                departureDay: day,
                departureTime: time,
                quantity: slots,
                status,
                tourId
            })
        });

        if (res.status === 201) {
            showToast('Succeed Add Departure Day', 'Success', 'green');
            $('#newDepartureModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Add Departure Day', 'Error', 'red');
    }
}
document.getElementById('add-day').addEventListener('click', async function (event) {
    event.preventDefault();

    await addDepartureDays();
})

function fetchDepartureDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('departure', data.departureDay);
    setElementValue('departureTime', data.departureTime);
    setElementValue('slots', data.quantity);
    setElementValue('status', data.status);
    setElementValue('tours', data.tour.id);
}

import {fetchData} from './utils.js';

fetchData('/api/v1/tours', function (data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function (typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, 'tours');

fetchData('/api/v1/tours', function (data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function (typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, '_tours');