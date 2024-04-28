document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/types-of-tours")
            .then(response => response.json())
            .then(data => {
                fetchTableTourType(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error(error));
    }

    function fetchTypeDetails(id) {
        const url = `/api/v1/type-of-tours/${encodeURIComponent(id)}`;

        fetch(url)
            .then(response =>  response.json())
            .then(data => {
                fetchTourTypeDetailsModal(data);
                $('#typeDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error(error));
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
                const url = `/api/v1/admin/update-type-of-tours-status/${encodeURIComponent(id)}`;

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
                    .catch(error => console.error(error));
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

async function updateTypeTour() {
    try {
        const id = document.getElementById('id').value;
        const name = document.getElementById('name').value;
        const description = document.getElementById('content').value;
        const status = document.getElementById('status').value;

        const res = await fetch(`/api/v1/admin/update-type-of-tours/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                description,
                status,
            })
        });

        if (res.status === 200) {
            showToast('Succeed Update Type Tour', 'Success', 'green');
            $('#typeDetailsModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Update Type Tour', 'Error', 'red')
    }
}
document.getElementById('update-typeTour').addEventListener('click', async function(event) {
    event.preventDefault();

    await updateTypeTour();
})

async function addTypeTour() {
    try {
        const name = document.getElementById('_name').value;
        const description = document.getElementById('_content').value;

        const res = await fetch("/api/v1/admin/type-of-tours", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                description,
            })
        });

        if (res.status === 201) {
            showToast('Succeed Add Type Tour', 'Success', 'green');
            $('#newTypeModalScrollable').modal('hide');
        }
    } catch (e) {
        showToast('Failed Add Type Tour', 'Error', 'red');
    }
}
document.getElementById('add-typeTour').addEventListener('click', async function(event) {
    event.preventDefault();

    await addTypeTour();
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