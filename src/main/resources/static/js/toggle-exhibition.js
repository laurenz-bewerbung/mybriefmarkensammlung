function toggleExhibition() {
    const checkbox = document.getElementById('isExhibition');
    const container = document.getElementById('exhibitionContainer');
    const select = document.querySelector('#exhibitionContainer select');

    container.hidden = !checkbox.checked;
    if (!checkbox.checked) select.value = "";
}
// Initialer Check beim Laden
document.addEventListener("DOMContentLoaded", toggleExhibition);