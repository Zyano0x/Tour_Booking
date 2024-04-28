document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/tours")
            .then(response => response.json())
            .then(data => {
                fetchTableTour(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error(error));
    }

    function fetchTourDetails(id) {
        const url = `/api/v1/tours/${encodeURIComponent(id)}`;
        console.log('Requesting tour details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                fetchTourDetailsModal(data);
                $('#tourDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching tour details:', error));
    }

    function fetchTableTour(data) {
        let tableBody = document.querySelector('#tour-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(tour => {
            const row = document.createElement('tr');
            const badgeClass = tour.status ? 'bg-primary' : 'bg-warning';
            const tourStatus = tour.status ? 'Visible' : 'Hidden';
            row.innerHTML = `
                <th scope="row">${tour.id}</th>
                <td>${tour.name}</td>
                <td>${tour.description}</td>
                <td>${tour.typeOfTour.name}</td>
                <td>${tour.dateOfPosting}</td>
                <td><span class="badge ${badgeClass}">${tourStatus}</span></td>
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
                fetchImagesByTourId(id);
                fetchTourDetails(id);
            });
        });
    }

    function lockAction() {
        document.querySelectorAll('.lock-icon').forEach(item => {
            item.addEventListener('click', event => {
                event.preventDefault();
                const id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                const url = `/api/v1/admin/update-tour-status/${encodeURIComponent(id)}`;

                let myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/json");
                let requestOptions = {
                    method: 'PUT', headers: myHeaders, redirect: 'follow'
                };

                fetch(url, requestOptions)
                    .then(response => response.json())
                    .then(result => console.log(result))
                    .catch(error => console.log(error));
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

const ckEditorInstances = new Map();

function fetchImagesByTourId(id) {
    const url = `/api/v1/tour-images/tours/${encodeURIComponent(id)}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const textarea = document.getElementById('images');
            textarea.value = '';
            data.forEach(image => {
                textarea.value += image.path + '\n';
            });
        })
        .catch(error => console.error(error));
}

function fetchTourDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('name', data.name);
    setElementValue('content', data.description);
    setElementValue('time', data.time);
    setElementValue('priceForAdult', data.priceForAdult);
    setElementValue('priceForChildren', data.priceForChildren);
    setElementValue('departurePoint', data.departurePoint);
    setElementValue('dateOfPosting', data.dateOfPosting);
    setElementValue('editDate', data.editDate);
    setElementValue('status', data.status);
    setElementValue('isHot', data.isHot);
    setElementValue('thumbnail', data.thumbnail);
    setElementValue('typesOfTour', data.typeOfTour.id);
    setElementValue('destinations', data.destination.id);

    const content = document.getElementById('schedule');
    const service = document.getElementById('service');

    // Check if CKEditor is already initialized for the 'content' textarea
    if (!ckEditorInstances.has(content)) {
        // Initialize CKEditor only if it hasn't been initialized yet
        initializeCKEditor(content).then(editor => {
            ckEditorInstances.set(content, editor);
            editor.setData(data.schedule);
        });
    } else {
        // If CKEditor is already initialized, set the content directly
        ckEditorInstances.get(content).setData(data.schedule);
    }

    // Check if CKEditor is already initialized for the 'service' textarea
    if (!ckEditorInstances.has(service)) {
        initializeCKEditor(service).then(editor => {
            ckEditorInstances.set(service, editor);
            editor.setData(data.service);
        });
    } else {
        ckEditorInstances.get(service).setData(data.service);
    }
}

function initializeCKEditor(content) {
    return ClassicEditor
        .create(content)
        .catch(error => {
            console.error(error);
        });
}

import {fetchData} from './utils.js';

fetchData('/api/v1/types-of-tours', function (data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function (typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, 'typesOfTour');

fetchData('/api/v1/destinations', function (data, dropdownId) {
    const destinationDropdown = document.getElementById(dropdownId);
    data.forEach(function (destination) {
        const option = document.createElement('option');
        option.value = destination.id;
        option.text = destination.name;
        destinationDropdown.appendChild(option);
    });
}, 'destinations');

async function updateTour() {
    try {
        const id = document.getElementById('id').value;
        const name = document.getElementById('name').value;
        const description = document.getElementById('content').value;
        const service = ckEditorInstances.get(document.getElementById('service')).getData();
        const time = document.getElementById('time').value;
        const schedule = ckEditorInstances.get(document.getElementById('schedule')).getData();
        const priceForAdult = document.getElementById('priceForAdult').value;
        const priceForChildren = document.getElementById('priceForChildren').value;
        const departurePoint = document.getElementById('departurePoint').value;
        const dateOfPosting = document.getElementById('dateOfPosting').value;
        const editDate = document.getElementById('editDate').value;
        const status = document.getElementById('status').value;
        const isHot = document.getElementById('isHot').value;
        const typeOfTourId = document.getElementById('typesOfTour').value;
        const destinationId = document.getElementById('destinations').value;
        const thumbnail = document.getElementById('thumbnail').value;
        const images = document.getElementById('images').value.split('\n').filter(Boolean); // Split images into an array

        const res = await fetch(`/api/v1/admin/update-tours/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "name": name,
                "description": description,
                "service": service,
                "time": time,
                "schedule": schedule,
                "priceForAdult": parseFloat(priceForAdult), // Parse to ensure it's a number
                "priceForChildren": parseFloat(priceForChildren), // Parse to ensure it's a number
                "departurePoint": departurePoint,
                "dateOfPosting": dateOfPosting,
                "editDate": editDate,
                "isHot": isHot,
                "status": status,
                "images": images,
                "totId": typeOfTourId,
                "destinationId": destinationId,
                "thumbnail": thumbnail,
            })
        });

        if (res.status === 200) {
            showToast('Succeed Update Tour', 'Success', 'green');
            $('#tourDetailsModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Update Tour', 'Error', 'red')
    }
}
document.getElementById('update-tour').addEventListener('click', async function (event) {
    event.preventDefault();

    await updateTour();
})

fetchData('/api/v1/types-of-tours', function (data, dropdownId) {
    const typeOfTourDropdown = document.getElementById(dropdownId);
    data.forEach(function (typeOfTour) {
        const option = document.createElement('option');
        option.value = typeOfTour.id;
        option.text = typeOfTour.name;
        typeOfTourDropdown.appendChild(option);
    });
}, '_typesOfTour');

fetchData('/api/v1/destinations', function (data, dropdownId) {
    const destinationDropdown = document.getElementById(dropdownId);
    data.forEach(function (destination) {
        const option = document.createElement('option');
        option.value = destination.id;
        option.text = destination.name;
        destinationDropdown.appendChild(option);
    });
}, '_destinations');

async function addTour() {
    try {
        const name = document.getElementById('_name').value;
        const description = document.getElementById('_content').value;
        const service = document.getElementById('_service').value;
        const time = document.getElementById('_time').value;
        const schedule = document.getElementById('_schedule').value;
        const priceForAdult = document.getElementById('_priceForAdult').value;
        const priceForChildren = document.getElementById('_priceForChildren').value;
        const departurePoint = document.getElementById('_departurePoint').value;
        const status = document.getElementById('_status').value;
        const isHot = document.getElementById('_isHot').value;
        const typeOfTourId = document.getElementById('_typesOfTour').value;
        const destinationId = document.getElementById('_destinations').value;
        const thumbnail = document.getElementById('_thumbnail').value;
        const images = document.getElementById('_images').value.split('\n').filter(Boolean);

        const res = await fetch('/api/v1/admin/tours', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "name": name,
                "description": description,
                "service": service,
                "time": time,
                "schedule": schedule,
                "priceForAdult": parseFloat(priceForAdult),
                "priceForChildren": parseFloat(priceForChildren),
                "departurePoint": departurePoint,
                "isHot": isHot,
                "status": status,
                "images": images,
                "totId": typeOfTourId,
                "destinationId": destinationId,
                "thumbnail": thumbnail,
            })
        })

        if (res.status === 201) {
            showToast('Succeed Add Tour', 'Success', 'green');
            $('#newTourModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Add Tour', 'Error', 'red');
    }
}
document.getElementById('add-tour').addEventListener('click', async function (event) {
    event.preventDefault();

    await addTour();
})
