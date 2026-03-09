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

function appendCategoryToDom(parentId, category) {
    // Finde den Container der Kinder des aktuellen Knotens
    const parentLi = document.getElementById(`node-${parentId}`);
    let childrenUl = parentLi.querySelector('.children-container');

    // Falls noch kein <ul> für Kinder existiert, erstelle eines
    if (!childrenUl) {
        childrenUl = document.createElement('ul');
        childrenUl.className = 'children-container';
        parentLi.appendChild(childrenUl);
    }

    // Füge das neue Element hinzu (vereinfachtes Template)
    const newLi = document.createElement('li');
    newLi.id = `node-${category.id}`;
    newLi.className = 'category-node';
    newLi.innerHTML = `
        <div class="category-content">
            <span class="category-name">${category.name}</span>
            <button type="button" onclick="showInlineForm(${category.id})">+</button>
            <div id="form-container-${category.id}"></div>
        </div>
        <ul class="children-container"></ul>
    `;
    childrenUl.appendChild(newLi);
}

function cancelForm(parentId) {
    document.getElementById(`form-container-${parentId}`).innerHTML = "";
}