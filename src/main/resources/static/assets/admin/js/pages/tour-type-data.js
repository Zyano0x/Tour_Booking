document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/types-of-tours")
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
        const url = `/api/type-of-tours?id=${encodeURIComponent(id)}`;
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
                const id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                const url = `/api/admin/update-type-of-tours-status?id=${encodeURIComponent(id)}`;

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

document.getElementById('update-typeTour').addEventListener('click', function(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
    const url = `/api/admin/update-type-of-tours?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const name = document.getElementById('name').value;
    const description = document.getElementById('content').value;
    const status = document.getElementById('status').value;

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

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error('Failed to update type tour. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Update Type Tour', 'Success', 'green');
            $('#typeDetailsModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Update Type Tour', 'Error', 'red'));
})

document.getElementById('add-typeTour').addEventListener('click', function(event) {
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

    fetch("/api/admin/type-of-tours", requestOptions)
        .then(response => {
            if (response.ok) return response.text();
            else throw new Error('Failed to update type tour. Status: ' + response.status);
        })
        .then(result => {
            showToast('Succeed Add Type Tour', 'Success', 'green');
            $('#newTypeModalScrollable').modal('hide');
        })
        .catch(error => showToast('Failed Add Type Tour', 'Error', 'red'));
})

function fetchTourTypeDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('name', data.name);
    setElementValue('content', data.description);
    setElementValue('status', data.status);
}