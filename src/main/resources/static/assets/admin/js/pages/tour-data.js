document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/tour/all")
            .then(response => response.json())
            .then(data => {
                fetchTableTour(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchTourDetails(id) {
        const url = `/api/tour?id=${encodeURIComponent(id)}`;
        console.log('Requesting tour details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Received data:', data);
                fetchTourDetailsModal(data);
                $('#tourDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching user details:', error));
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
                let id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                updateStatus(id);
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

function fetchImagesByTourId(id) {
    const url = `/api/tour-image/tour?id=${encodeURIComponent(id)}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const textarea = document.getElementById('images');
            textarea.value = '';
            data.forEach(image => {
                textarea.value += image.path + '\n';
            });
        })
        .catch(error => console.error('Error fetching type of tour options:', error));
}

function updateStatus(id) {
    const url = `/api/admin/update-tour-status?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    let requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch(url, requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('Error updating tour status:', error));
}

async function updateTour(id) {
    event.preventDefault();

    const url = `/api/admin/update-tour?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const name = document.getElementById('name').value;
    const description = document.getElementById('content').value;
    const service = document.getElementById('service').value;
    const time = document.getElementById('time').value;
    const schedule = document.getElementById('schedule').value;
    const priceForAdult = document.getElementById('priceForAdult').value;
    const priceForChildren = document.getElementById('priceForChildren').value;
    const departurePoint = document.getElementById('departurePoint').value;
    const dateOfPosting = document.getElementById('dateOfPosting').value;
    const editDate = document.getElementById('editDate').value;
    const status = document.getElementById('status').value;
    const isHot = document.getElementById('isHot').value;
    const typeOfTourId = await getTypeOfTourIdByName(document.getElementById('typeOfTourDropdownUpdate').value);
    const destinationId = await getDestinationIdByName(document.getElementById('destinationDropdownUpdate').value);
    const thumbnail = document.getElementById('thumbnail').value;
    const images = document.getElementById('images').value.split('\n').filter(Boolean); // Split images into an array

    let raw = JSON.stringify({
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
            console.log('Tour updated successfully:', result);
            showToast('Tour updated successfully');
            $('#tourDetailsModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error updating tour:', error));
}

async function addTour() {
    event.preventDefault();

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    // Example: Get values from your form fields
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
    const typeOfTourId = await getTypeOfTourIdByName(document.getElementById('typeOfTourDropdownNew').value);
    const destinationId = await getDestinationIdByName(document.getElementById('destinationDropdownNew').value);
    const thumbnail = document.getElementById('_thumbnail').value;
    const images = document.getElementById('_images').value.split('\n').filter(Boolean); // Split images into an array

    // Construct the request body based on the TourDTO structure
    let raw = JSON.stringify({
        "name": name,
        "description": description,
        "service": service,
        "time": time,
        "schedule": schedule,
        "priceForAdult": parseFloat(priceForAdult), // Parse to ensure it's a number
        "priceForChildren": parseFloat(priceForChildren), // Parse to ensure it's a number
        "departurePoint": departurePoint,
        "isHot": isHot,
        "status": status,
        "images": images,
        "totId": typeOfTourId,
        "destinationId": destinationId,
        "thumbnail": thumbnail,
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // Make the API request
    fetch("/api/admin/tour", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log('Tour added successfully:', result);
            showToast('Tour added successfully');
            $('#newTourModalScrollable').modal('hide');
        })
        .catch(error => console.log('Error updating tour:', error));
}

function dropdownWithDestination(currentDestination) {
    const dropdownUpdate = document.getElementById('destinationDropdownUpdate');

    fetch('/api/destination/all')
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(destinations => {
            dropdownUpdate.innerHTML = "";

            destinations.forEach(destination => {
                const option = document.createElement("option");
                option.value = destination.name;
                option.text = destination.name;

                if (currentDestination && destination.id === currentDestination.id)
                    option.selected = true;

                dropdownUpdate.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching destinations:', error);
        });
}

function dropdownWithTypeOfTour(currentType) {
    const dropdownUpdate = document.getElementById('typeOfTourDropdownUpdate');

    fetch('/api/type-of-tour/all')
        .then(response => {
            if (response.ok) return response.json();
        })
        .then(types => {
            dropdownUpdate.innerHTML = "";

            types.forEach(type => {
                const option = document.createElement("option");
                option.value = type.name;
                option.text = type.name;

                if (currentType && type.id === currentType.id)
                    option.selected = true;

                dropdownUpdate.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching types:', error);
        });
}

function fetchTourDetailsModal(data) {
    document.getElementById('id').value = data.id;
    document.getElementById('name').value = data.name;
    document.getElementById('content').value = data.description;
    document.getElementById('service').value = data.service;
    document.getElementById('time').value = data.time;
    document.getElementById('schedule').value = data.schedule;
    document.getElementById('priceForAdult').value = data.priceForAdult;
    document.getElementById('priceForChildren').value = data.priceForChildren;
    document.getElementById('departurePoint').value = data.departurePoint;
    document.getElementById('dateOfPosting').value = data.dateOfPosting;
    document.getElementById('editDate').value = data.editDate;
    document.getElementById('status').value = data.status;
    document.getElementById('isHot').value = data.isHot;
    document.getElementById('thumbnail').value = data.thumbnail;
    dropdownWithDestination(data.destination);
    dropdownWithTypeOfTour(data.typeOfTour);
}

async function getTypeOfTourOptions() {
    try {
        const response = await fetch('/api/type-of-tour/all');
        if (response.ok) return await response.json();
    } catch (error) {
        console.error('Error fetching Type of Tour options:', error);
        return [];
    }
}

async function getDestinationOptions() {
    try {
        const response = await fetch('/api/destination/all');
        if (response.ok) return await response.json();
    } catch (error) {
        console.error('Error fetching Destination options:', error);
        return [];
    }
}

async function getTypeOfTourIdByName(name) {
    const typeOfTourOptions = await getTypeOfTourOptions(); // Wait for the promise to resolve
    const typeOfTour = typeOfTourOptions.find(option => option.name === name);
    return typeOfTour ? typeOfTour.id : null;
}

async function getDestinationIdByName(name) {
    const destinationOptions = await getDestinationOptions(); // Wait for the promise to resolve
    const destination = destinationOptions.find(option => option.name === name);
    return destination ? destination.id : null;
}

async function fetchTypeOfTourAndDestinations() {
    try {
        const typeOfTourOptions = await fetch('/api/type-of-tour/all').then(response => response.json());
        const destinationOptions = await fetch('/api/destination/all').then(response => response.json());

        populateDropdown('typeOfTourDropdownNew', typeOfTourOptions);
        populateDropdown('destinationDropdownNew', destinationOptions);
    } catch (error) {
        console.error('Error fetching data options:', error);
    }
}
fetchTypeOfTourAndDestinations();