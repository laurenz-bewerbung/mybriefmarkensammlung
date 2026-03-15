function showInlineForm(parentId) {
    const container = document.getElementById(`form-container-${parentId}`);

    // Verhindern, dass mehrere Felder gleichzeitig offen sind
    if (container.innerHTML !== "") return;

    container.innerHTML = `
        <input type="text" id="input-${parentId}" placeholder="Name..." />
        <button onclick="submitCategory(${parentId})">OK</button>
        <button onclick="cancelForm(${parentId})">X</button>
    `;
    document.getElementById(`input-${parentId}`).focus();
}

async function submitCategory(parentId) {
    const input = document.getElementById(`input-${parentId}`);
    const name = input.value;

    if (!name) return;

    // Wir erstellen ein "Formular-Format", da dein Controller @RequestParam nutzt
    const formData = new URLSearchParams();
    formData.append('category', name);
    if (parentId) {
        formData.append('parentId', parentId);
    }

    try {
        const response = await fetch('/categories', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: formData
        });

        if (response.ok) {
            const newCategory = await response.json();
            appendCategoryToDom(parentId, newCategory);
            cancelForm(parentId);
        } else {
            alert("Fehler beim Speichern!");
        }
    } catch (error) {
        console.error("Netzwerkfehler:", error);
    }
}

function appendCategoryToDom(parentId, responseData) {
    const categoryName = responseData.category;
    const categoryId = responseData.id;

    let targetContainer;

    if (parentId) {
        // 1. Finde das li-Element des Elternknotens
        const parentLi = document.getElementById(`node-${parentId}`);
        if (!parentLi) return;

        // 2. Suche den children-container (der jetzt dank HTML-Fix immer da ist)
        targetContainer = parentLi.querySelector(':scope > .children-container');
    } else {
        // Hauptkategorie: Die oberste Liste im Dokument
        targetContainer = document.querySelector('.category-tree-container');
    }

    // 3. Jede Ebene braucht eine <ul> mit der Klasse 'tree-group'
    // Wenn noch keine Unterkategorien da waren, existiert diese <ul> noch nicht
    let targetUl = targetContainer.querySelector(':scope > .tree-group');

    if (!targetUl) {
        targetUl = document.createElement('ul');
        targetUl.className = 'tree-group';
        targetContainer.appendChild(targetUl);
    }

    // 4. Das neue li-Element bauen (Exakt wie dein Thymeleaf-Fragment)
    const newLi = document.createElement('li');
    newLi.id = `node-${categoryId}`;
    newLi.className = 'category-node';
    newLi.innerHTML = `
        <div class="category-content border shadow-sm">
            <a href="/sammlungen?category=${categoryId}"
               class="category-link d-flex align-items-center flex-grow-1 text-decoration-none text-dark">
                <i class="bi bi-folder2-open me-2 text-primary"></i>
                <span class="category-name">${categoryName}</span>
            </a>
            <button type="button" class="btn-add ms-2"
                    onclick="event.stopPropagation(); showInlineForm(${categoryId})">
                +
            </button>
        </div>
        <div id="form-container-${categoryId}" class="inline-form-container"></div>
        <div class="children-container"></div>
    `;

    // 5. In die Liste einfügen
    targetUl.appendChild(newLi);

    // Cleanup für leere Zustände
    if (!parentId) {
        const emptyMsg = document.querySelector('.text-center.text-muted');
        if (emptyMsg) emptyMsg.remove();
    }
}

function cancelForm(parentId) {
    document.getElementById(`form-container-${parentId}`).innerHTML = "";
}