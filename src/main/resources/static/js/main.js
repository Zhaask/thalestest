// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {

    // Obtener referencias a elementos
    const searchForm = document.querySelector('form');
    const idInput = document.getElementById('employeeId');

    // Validación de entrada de ID (solo números)
    if (idInput) {
        idInput.addEventListener('input', function() {
            // Reemplazar cualquier carácter que no sea un número
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }

    // Enviar formulario al presionar Enter
    if (idInput) {
        idInput.addEventListener('keydown', function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                searchForm.submit();
            }
        });
    }

    // Añadir clases para destacar filas al pasar el ratón
    const tableRows = document.querySelectorAll('tbody tr');
    tableRows.forEach(row => {
        row.addEventListener('mouseover', function() {
            this.classList.add('table-primary');
        });
        row.addEventListener('mouseout', function() {
            this.classList.remove('table-primary');
        });
    });
});