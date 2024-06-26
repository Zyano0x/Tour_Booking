document.addEventListener('DOMContentLoaded', function () {
    function fetchData() {
        fetch("/api/v1/articles")
            .then(response => response.json())
            .then(data => {
                fetchTableArticles(data);
                feather.replace();
                eyeAction();
                lockAction();
            })
            .catch(error => console.error(error));
    }

    function fetchArticlesDetails(id) {
        const url = `/api/v1/articles/${encodeURIComponent(id)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                fetchArticlesDetailsModal(data);
                $('#articlesDetailsModalScrollable').modal('show'); // Show the modal
            })
            .catch(error => console.error(error));
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
                const id = item.parentElement.parentElement.querySelector('th[scope="row"]').innerText;
                const url = `/api/v1/admin/update-articles-status/${encodeURIComponent(id)}`;

                let myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/json");
                let requestOptions = {
                    method: 'PUT', headers: myHeaders, redirect: 'follow'
                };

                fetch(url, requestOptions)
                    .then(response => response.json())
                    .then(result => console.log(result))
                    .catch(error => console.log('Error updating status:', error));
            });
        });
    }

    fetchData();
    setInterval(fetchData, 1000);
});

const ckEditorInstances = new Map();

async function updateArticles() {
    try {
        const id = document.getElementById('id').value;
        const title = document.getElementById('title').value;
        const status = document.getElementById('status').value;
        const thumbnail = document.getElementById('thumbnail').value;
        const dateOfPosting = document.getElementById('posting').value
        const description = ckEditorInstances.get(document.getElementById('description')).getData();
        const content = ckEditorInstances.get(document.getElementById('content')).getData();

        const res = await fetch(`/api/v1/admin/update-articles/${encodeURIComponent(id)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title,
                content,
                description,
                thumbnail,
                dateOfPosting,
                status
            })
        });

        if (res.status === 200) {
            showToast('Succeed Update Articles', 'Success', 'green');
            $('#articlesDetailsModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Update Articles', 'Error', 'red')
    }
}
document.getElementById('update-articles').addEventListener('click', async function (event) {
    event.preventDefault();

    await updateArticles();
})

async function addArticles() {
    try {
        const title = document.getElementById('_title').value;
        const content = document.getElementById('_content').value;
        const description = document.getElementById('_description').value;
        const thumbnail = document.getElementById('_thumbnail').value;
        const id = document.head.getAttribute('data-user-id');

        const res = await fetch("/api/v1/articles", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title,
                content,
                thumbnail,
                description,
                userId: id
            })
        });

        if (res.status === 201) {
            showToast('Succeed Add Articles', 'Success', 'green');
            $('#newArticlesModalScrollable').modal('hide');
        }
    } catch (e) {
        console.error(e);
        showToast('Failed Add Articles', 'Error', 'red');
    }
}
document.getElementById('add-articles').addEventListener('click', async function (event) {
    event.preventDefault();

    await addArticles();
});

function fetchArticlesDetailsModal(data) {
    const setElementValue = (elementId, value) => {
        const element = document.getElementById(elementId);
        if (element) {
            element.value = value;
        }
    };

    setElementValue('id', data.id);
    setElementValue('title', data.title);
    setElementValue('posting', data.dateOfPosting);
    setElementValue('editing', data.editDate);
    setElementValue('status', data.status);
    setElementValue('thumbnail', data.thumbnail);

    const content = document.getElementById('content');
    const description = document.getElementById('description');

    // Check if CKEditor is already initialized for the 'content' textarea
    if (!ckEditorInstances.has(content)) {
        // Initialize CKEditor only if it hasn't been initialized yet
        initializeCKEditor(content).then(editor => {
            ckEditorInstances.set(content, editor);
            editor.setData(data.content);
        });
    } else {
        // If CKEditor is already initialized, set the content directly
        ckEditorInstances.get(content).setData(data.content);
    }

    if (!ckEditorInstances.has(description)) {
        // Initialize CKEditor only if it hasn't been initialized yet
        initializeCKEditor(description).then(editor => {
            ckEditorInstances.set(description, editor);
            editor.setData(data.description);
        });
    } else {
        // If CKEditor is already initialized, set the content directly
        ckEditorInstances.get(description).setData(data.description);
    }
}

function initializeCKEditor(content) {
    return ClassicEditor
        .create(content)
        .catch(error => {
            console.error(error);
        });
}