export function fetchData(url, callback, dropdownId) {
    fetch(url)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error('Failed to fetch data. Status: ' + response.status);
        })
        .then(data => callback(data, dropdownId))
        .catch(error => console.error('Error:', error));
}