function showToast(message, type = 'success') {
    const toastContainer = document.getElementById('toast');
    const toast = new bootstrap.Toast(toastContainer);

    toastContainer.querySelector('.toast-body').innerText = message;
    toastContainer.querySelector('.toast-body').classList.remove('bg-success', 'bg-error');
    toastContainer.querySelector('.toast-body').classList.add(`bg-${type}`);

    toast.show();
}
