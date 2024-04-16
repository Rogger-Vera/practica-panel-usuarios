$(document).ready(function() {

});


async function registrarUsuario() {
  let datos = {};
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.email = document.getElementById('txtEmail').value;
  datos.telefono = document.getElementById('txtTelefono').value;
  datos.contrasena = document.getElementById('txtPassword').value;

  let repetirPassword = document.getElementById('txtRepetirPassword').value;

  if (!datos.nombre || !datos.apellido || !datos.telefono || !datos.email || !datos.contrasena || !repetirPassword) {
    alert('Por favor, complete todos los campos.');
    return;
  }

  if (datos.telefono.length !== 10) {
    alert('El teléfono debe tener 10 dígitos.');
    return;
  }

  if (repetirPassword != datos.contrasena) {
    alert('La contraseña que escribiste es diferente.');
    return;
  }

  const request = await fetch('api/usuarios', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  alert("La cuenta fue creada con exito!");
  window.location.href = 'login.html'

}
