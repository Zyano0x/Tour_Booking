document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/bookings")
            .then(response => response.json())
            .then(data => {
                fetchTableBooking(data);
                feather.replace();
                eyeAction();
            })
            .catch(error => console.error(error));
    }

    function fetchBookingDetails(id) {
        const url = `/api/v1/bookings/${encodeURIComponent(id)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                fetchBookingDetailsModal(data);
                $('#bookingDetailsModalScrollable').modal('show');
            })
            .catch(error => console.error(error));
    }

    function fetchTableBooking(data) {
        let tableBody = document.querySelector('#booking-table tbody');
        tableBody.innerHTML = '';

        data.forEach(booking => {
            const row = document.createElement('tr');
            const badgeClass = booking.status ? 'bg-primary' : 'bg-warning';
            const bookingStatus = booking.status ? 'Success' : 'Fail';
            row.innerHTML = `
                <th scope="row">${booking.id}</th>
                <td>${booking.transactionCode}</td>
                <td>${booking.user.email}</td>
                <td>${booking.departureDay.tour.name}</td>
                <td>${booking.bookingDate}</td>
                <td><span class="badge ${badgeClass}">${bookingStatus}</span></td>
                <td>
                    <a href="#" class="eye-icon"><i data-feather="eye"></i></a>
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
                fetchBookingDetails(id);
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

const ckEditorInstances = new Map();

function fetchBookingDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('transaction', data.transactionCode);
    setElementValue('email', data.user.username);
    setElementValue('full-name', data.user.name);
    setElementValue('booking-date', data.bookingDate);
    setElementValue('tour', data.departureDay.tour.name);
    setElementValue('price-adult', data.departureDay.tour.priceForAdult);
    setElementValue('price-child', data.departureDay.tour.priceForChildren);
    setElementValue('time', data.departureDay.tour.time);
    setElementValue('destination', data.departureDay.tour.destination.name);
    setElementValue('departure-day', data.departureDay.departureDay);

    const content = document.getElementById('schedule');

    // Check if CKEditor is already initialized for the 'content' textarea
    if (!ckEditorInstances.has(content)) {
        // Initialize CKEditor only if it hasn't been initialized yet
        initializeCKEditor(content).then(editor => {
            ckEditorInstances.set(content, editor);
            editor.setData(data.departureDay.tour.schedule);
        });
    } else {
        // If CKEditor is already initialized, set the content directly
        ckEditorInstances.get(content).setData(data.departureDay.tour.schedule);
    }
}

function initializeCKEditor(content) {
    return ClassicEditor
        .create(content)
        .catch(error => {
            console.error(error);
        });
}