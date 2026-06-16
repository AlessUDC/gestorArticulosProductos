// ─────────────────────────────────────────────────────────────
//  productos.js  –  Consumo de API REST: /api/productos
//
//  Campos esperados del backend (Producto):
//    idProducto | nombre | categoria | descripcion | precio | imagenUrl
//
//  IDs del HTML (productos.html):
//    #idProducto   → input hidden
//    #nombre       → input text
//    #categoria    → input text
//    #descripcion  → textarea
//    #precio       → input number
//    #imagenUrl    → input url
//    #imgPreview / #previewWrap → preview de imagen
//    #formTitulo   → h2 del formulario
//    #btnGuardar   → button (onclick)
//    #btnCancelar  → button cancelar edición
//    #productosGrid → div contenedor de tarjetas
//    #mensajeVacio  → p sin resultados
//    #buscador      → input de búsqueda
// ─────────────────────────────────────────────────────────────

const API_URL = `${CONFIG.API_URL}/api/productos`;

// ── Cache local de productos (para filtrado sin re-fetch) ──────
let _productos = [];

// ── Elementos del DOM ──────────────────────────────────────────
const inputId       = document.getElementById('idProducto');
const inputNombre   = document.getElementById('nombre');
const inputCat      = document.getElementById('categoria');
const inputDesc     = document.getElementById('descripcion');
const inputPrecio   = document.getElementById('precio');
const inputImgUrl   = document.getElementById('imagenUrl');
const imgPreview    = document.getElementById('imgPreview');
const previewWrap   = document.getElementById('previewWrap');
const formTitulo    = document.getElementById('formTitulo');
const btnGuardar    = document.getElementById('btnGuardar');
const btnCancelar   = document.getElementById('btnCancelar');
const productosGrid = document.getElementById('productosGrid');
const mensajeVacio  = document.getElementById('mensajeVacio');
const buscador      = document.getElementById('buscador');

// ── Preview de imagen en tiempo real ──────────────────────────
if (inputImgUrl) {
    inputImgUrl.addEventListener('input', () => {
        const url = inputImgUrl.value.trim();
        if (url && previewWrap && imgPreview) {
            imgPreview.src = url;
            previewWrap.style.display = 'block';
            imgPreview.onerror = () => { previewWrap.style.display = 'none'; };
        } else if (previewWrap) {
            previewWrap.style.display = 'none';
        }
    });
}

// ── Utilidades ─────────────────────────────────────────────────
function limpiarFormulario() {
    if (inputId)     inputId.value     = '';
    if (inputNombre) inputNombre.value = '';
    if (inputCat)    inputCat.value    = '';
    if (inputDesc)   inputDesc.value   = '';
    if (inputPrecio) inputPrecio.value = '';
    if (inputImgUrl) inputImgUrl.value = '';
    if (previewWrap) previewWrap.style.display = 'none';
    if (formTitulo)  formTitulo.textContent     = 'Nuevo Producto';
    if (btnGuardar)  btnGuardar.textContent      = '💾 Guardar Producto';
    if (btnCancelar) btnCancelar.style.display   = 'none';
}

function cancelarEdicion() {
    limpiarFormulario();
}

// ── Listar productos  GET /api/productos ───────────────────────
async function listarProductos() {
    try {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error(`Error ${res.status}`);
        _productos = await res.json();
        renderizarGrid(_productos);
    } catch (err) {
        alert('No se pudo cargar la lista de productos: ' + err.message);
    }
}

// ── Filtro de búsqueda (local) ─────────────────────────────────
function filtrarProductos() {
    const q = buscador ? buscador.value.trim().toLowerCase() : '';
    if (!q) {
        renderizarGrid(_productos);
        return;
    }
    const filtrados = _productos.filter(p =>
        p.nombre.toLowerCase().includes(q) ||
        p.categoria.toLowerCase().includes(q)
    );
    renderizarGrid(filtrados);
}

// ── Renderizar grid de tarjetas ────────────────────────────────
function renderizarGrid(productos) {
    if (!productosGrid) return;
    productosGrid.innerHTML = '';

    if (productos.length === 0) {
        if (mensajeVacio) mensajeVacio.style.display = 'block';
        return;
    }
    if (mensajeVacio) mensajeVacio.style.display = 'none';

    productos.forEach(p => {
        const card = document.createElement('div');
        card.className = 'prod-card';

        // Imagen o placeholder
        const imgHtml = p.imagenUrl
            ? `<img class="prod-card-img" src="${p.imagenUrl}" alt="${p.nombre}"
                    onerror="this.style.display='none';this.nextElementSibling.style.display='flex'">`
            : '';
        const placeholderStyle = p.imagenUrl ? 'display:none' : '';

        card.innerHTML = `
            ${imgHtml}
            <div class="prod-card-img-placeholder" style="${placeholderStyle}">📦</div>
            <div class="prod-card-body">
                <span class="prod-card-category">${p.categoria ?? 'Sin categoría'}</span>
                <p class="prod-card-name">${p.nombre}</p>
                <p class="prod-card-desc">${p.descripcion ?? ''}</p>
                <p class="prod-card-price">S/. ${Number(p.precio).toFixed(2)}</p>
            </div>
            <div class="prod-card-actions">
                <button class="btn-editar"   onclick="cargarProducto(${p.idProducto})">✏️ Editar</button>
                <button class="btn-eliminar" onclick="eliminarProducto(${p.idProducto})">🗑️ Eliminar</button>
            </div>`;
        productosGrid.appendChild(card);
    });
}

// ── Guardar (crear o editar) ───────────────────────────────────
async function guardarProducto() {
    const id        = inputId     ? inputId.value.trim()        : '';
    const nombre    = inputNombre ? inputNombre.value.trim()    : '';
    const categoria = inputCat    ? inputCat.value.trim()       : '';
    const descripcion = inputDesc ? inputDesc.value.trim()      : '';
    const precio    = inputPrecio ? parseFloat(inputPrecio.value) : NaN;
    const imagenUrl = inputImgUrl ? inputImgUrl.value.trim()    : '';

    if (!nombre || !categoria || isNaN(precio)) {
        alert('Los campos Nombre, Categoría y Precio son obligatorios.');
        return;
    }

    // Payload compatible con el backend (Producto.java pendiente de creación)
    const payload = { nombre, categoria, descripcion, precio, imagenUrl };

    try {
        let res;
        if (id) {
            // PUT /api/productos/{id}
            res = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        } else {
            // POST /api/productos
            res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        }

        if (!res.ok) throw new Error(`Error ${res.status}`);
        limpiarFormulario();
        listarProductos();
    } catch (err) {
        alert('Error al guardar producto: ' + err.message);
    }
}

// ── Cargar producto en el formulario para editar ───────────────
async function cargarProducto(id) {
    try {
        const res = await fetch(`${API_URL}/${id}`);
        if (!res.ok) throw new Error(`Error ${res.status}`);
        const p = await res.json();

        if (inputId)     inputId.value     = p.idProducto;
        if (inputNombre) inputNombre.value = p.nombre;
        if (inputCat)    inputCat.value    = p.categoria;
        if (inputDesc)   inputDesc.value   = p.descripcion ?? '';
        if (inputPrecio) inputPrecio.value = p.precio;
        if (inputImgUrl) inputImgUrl.value = p.imagenUrl ?? '';

        // Mostrar preview si hay imagen
        if (p.imagenUrl && imgPreview && previewWrap) {
            imgPreview.src = p.imagenUrl;
            previewWrap.style.display = 'block';
        }

        if (formTitulo)  formTitulo.textContent    = `Editar: ${p.nombre}`;
        if (btnGuardar)  btnGuardar.textContent     = '💾 Actualizar Producto';
        if (btnCancelar) btnCancelar.style.display  = 'inline-block';

        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
        alert('Error al cargar producto: ' + err.message);
    }
}

// ── Eliminar producto  DELETE /api/productos/{id} ──────────────
async function eliminarProducto(id) {
    if (!confirm('¿Seguro que deseas eliminar este producto?')) return;
    try {
        const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error(`Error ${res.status}`);
        listarProductos();
    } catch (err) {
        alert('Error al eliminar producto: ' + err.message);
    }
}

// ── Inicio ─────────────────────────────────────────────────────
listarProductos();
