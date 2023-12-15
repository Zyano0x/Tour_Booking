document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/articles/all")
            .then(response => response.json())
            .then(data => {
                fetchTableArticles(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function fetchArticlesDetails(id) {
        const url = `/api/articles?id=${encodeURIComponent(id)}`;
        console.log('Requesting articles details:', url);

        fetch(url)
            .then(response => {
                console.log('Response status:', response.status);
                return response.json();
            })
            .then(data => {
                console.log('Received data:', data);
                fetchArticlesDetailsModal(data);
                $('#articlesDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error('Error fetching type details:', error));
    }

    function fetchTableArticles(data) {
        let tableBody = document.querySelector('#articles-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach(articles => {
            const row = document.createElement('tr');
            const badgeClass = articles.status ? 'bg-primary' : 'bg-warning';
            const articlesStatus = articles.status ? 'Visible' : 'Hidden';
            row.innerHTML = `
                <th scope="row">${articles.id}</th>
                <td>${articles.title}</td>
                <td>${articles.dateOfPosting}</td>
                <td>${articles.editDate}</td>
                <td><span class="badge ${badgeClass}">${articlesStatus}</span></td>
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
                fetchArticlesDetails(id);
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
    const url = `/api/admin/update-articles-status?id=${encodeURIComponent(id)}`;

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
        })
        .then(result => console.log(result))
        .catch(error => console.log('Error updating status:', error));
}

function updateArticles(id) {
    event.preventDefault();

    const url = `/api/admin/update-articles?id=${encodeURIComponent(id)}`;

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const status = document.getElementById('status').value;

    let raw = JSON.stringify({
        "title": title,
        "content": content,
        "status": status,
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
            if (response.ok)
            {
                showToast('Articles updated successfully');
                $('#articlesDetailsModalScrollable').modal('hide');
                return response.json();
            }
        })
        .then(result => console.log(result))
        .catch(error => console.log('Error updating articles:', error));
}

function fetchArticlesDetailsModal(data) {
    document.getElementById('id').value = data.id;
    document.getElementById('title').value = data.title;
    document.getElementById('posting').value = data.dateOfPosting;
    document.getElementById('editing').value = data.editDate;
    document.getElementById('status').value = data.status;
    document.getElementById('content').value = data.content;
}