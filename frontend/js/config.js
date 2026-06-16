// Configuración global del frontend
const CONFIG = {
    // Si estamos en localhost, apunta al backend local.
    // De lo contrario, utiliza la URL del backend en Render.
    API_URL: window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
        ? 'http://localhost:8080'
        : 'https://gestor-articulos-productos.onrender.com' // Reemplazar con la URL real de Render tras crear el Web Service
};
