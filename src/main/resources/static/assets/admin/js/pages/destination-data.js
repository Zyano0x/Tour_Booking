document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/destinations")
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
        const url = `/api/v1/destinations/${encodeURIComponent(id)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                fetchDestinationDetailsModal(data);
                $('#destinationDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error(error));
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
                const url = `/api/v1/admin/update-destination-status/${encodeURIComponent(id)}`;

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

async function updateDestination() {
    try {
        const id = document.getElementById('id').value;
        const name = document.getElementById('name').value;
        const isHot = document.getElementById('isHot').value;
        const status = document.getElementById('status').value;
        const thumbnail = document.getElementById('thumbnail').value;

        const res = await fetch(`/api/v1/admin/update-destinations/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                isHot,
                status,
                thumbnail,
            })
        });

        if (res.status === 200) {
            showToast('Succeed Update Destination', 'Success', 'green');
            $('#destinationDetailsModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
    }
}
document.getElementById('update-destination').addEventListener('click', async function(event) {
    event.preventDefault();

    await updateDestination();
})

async function addDestination() {
    try {
        const name = document.getElementById('_name').value;
        const isHot = document.getElementById('_isHot').value;
        const thumbnail = document.getElementById('_thumbnail').value;

        const res = await fetch("/api/v1/admin/destinations", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                isHot,
                thumbnail,
            })
        });

        if (res.status === 201) {
            showToast('Succeed Add Destination', 'Success', 'green');
            $('#newDestinationModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
    }
}
document.getElementById('add-destination').addEventListener('click', async function(event) {
    event.preventDefault();

    await addDestination();
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
