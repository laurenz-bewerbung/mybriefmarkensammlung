document.addEventListener('DOMContentLoaded', function() {
    const container = document.getElementById('image-sortable');
    if (!container) return;

    new Sortable(container, {
        animation: 150, // Sanfte Animation beim Verschieben (in ms)
        ghostClass: 'sortable-ghost',  // Klasse für das Element, das gerade "gezogen" wird
        chosenClass: 'sortable-chosen', // Klasse für das ausgewählte Element
        dragClass: 'sortable-drag',     // Klasse für das Element, während es bewegt wird
        filter: '.remove-img',
        preventOnFilter: false
    });

    container.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-img')) {
            const imageItem = e.target.closest('.image-item');
            if (imageItem) {
                imageItem.style.transition = 'opacity 0.3s, transform 0.3s';
                imageItem.style.opacity = '0';
                imageItem.style.transform = 'scale(0.8)';

                setTimeout(() => {
                    imageItem.remove();
                }, 300);
            }
        }
    });
});