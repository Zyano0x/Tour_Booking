function populateDropdown(dropdownId, options) {
    const dropdown = document.getElementById(dropdownId);

    dropdown.innerHTML = "";

    options.forEach(option => {
        const optionElement = document.createElement("option");
        optionElement.value = option.name; // Assuming name is the property you want to use
        optionElement.text = option.name;
        dropdown.appendChild(optionElement);
    });
}