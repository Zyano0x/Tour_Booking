function showToast(message, type = 'success') {
    const toastContainer = document.getElementById('toast');
    const toast = new bootstrap.Toast(toastContainer);

    toastContainer.querySelector('.toast-body').innerText = message;
    toastContainer.classList.remove('bg-success', 'bg-error'); // Remove previous classes
    toastContainer.classList.add(`bg-${type}`);

    toast.show();
}