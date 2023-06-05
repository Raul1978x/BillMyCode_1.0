function filtrarTablaDev() {
  var filtros = document.getElementById("filtrar-tabla-dev").value.toUpperCase().split(",");
  var filasTd = document.getElementById("tabla-developers").getElementsByTagName("tr");

  for (var i = 0; i < filasTd.length; i++) {
    var developer = filasTd[i];
    var Nombre = developer.getElementsByTagName("td")[3];
    var Apellido = developer.getElementsByTagName("td")[4];
    var Email = developer.getElementsByTagName("td")[5];
    var Especialidad = developer.getElementsByTagName("td")[6];
    var Seniority = developer.getElementsByTagName("td")[7];
    var Salario = developer.getElementsByTagName("td")[8];

    if (Nombre || Apellido || Email || Especialidad || Seniority || Salario) {
      var textoNombre = Nombre.textContent || Nombre.innerText;
      var textoApellido = Apellido.textContent || Apellido.innerText;
      var textoEmail = Email.textContent || Email.innerText;
      var textoEspecialidad = Especialidad.textContent || Especialidad.innerText;
      var textoSeniority = Seniority.textContent || Seniority.innerText;
      var textoSalario = Salario.textContent || Salario.innerText;

      var mostrarTabResult = true; //mostrar tabla resultado

      for (var j = 0; j < filtros.length; j++) {
        var filtroIndAct = filtros[j].trim(); //filtro individual actual

        if (
          textoNombre.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoApellido.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoEmail.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoEspecialidad.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoSeniority.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoSalario.toUpperCase().indexOf(filtroIndAct) === -1
        ) {
          mostrarTabResult = false;
          break;
        }
      }
      if (mostrarTabResult) {
        developer.style.display = "";
      } else {
        developer.style.display = "none";
      }
    }
  }
}

function filtrarTablaAccountant() {
  var filtros = document.getElementById("filtrar-tabla-accountant").value.toUpperCase().split(",");
  var filasTd = document.getElementById("tabla-accountant").getElementsByTagName("tr");

  for (var i = 0; i < filasTd.length; i++) {
    var accountant = filasTd[i];
    var Email = accountant.getElementsByTagName("td")[3];
    var Nombre = accountant.getElementsByTagName("td")[4];
    var Apellido = accountant.getElementsByTagName("td")[5];
    var Especializacion = accountant.getElementsByTagName("td")[6];
    var Honorarios = accountant.getElementsByTagName("td")[7];

    if (Nombre || Apellido || Email || Especializacion || Honorarios) {
      var textoEmail = Email.textContent || Email.innerText;
      var textoNombre = Nombre.textContent || Nombre.innerText;
      var textoApellido = Apellido.textContent || Apellido.innerText;
      var textoEspecializacion = Especializacion.textContent || Especializacion.innerText;
      var textoHonorarios = Honorarios.textContent || Honorarios.innerText;

      var mostrarTabResult = true; //mostrar tabla resultado

      for (var j = 0; j < filtros.length; j++) {
        var filtroIndAct = filtros[j].trim(); //filtro individual actual

        if (
          textoEmail.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoNombre.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoApellido.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoEspecializacion.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoHonorarios.toUpperCase().indexOf(filtroIndAct) === -1
        ) {
          mostrarTabResult = false;
          break;
        }
      }
      if (mostrarTabResult) {
        accountant.style.display = "";
      } else {
        accountant.style.display = "none";
      }
    }
  }
}

function filtrarTablaAdmin() {
  var filtros = document.getElementById("filtrar-tabla-admin").value.toUpperCase().split(",");
  var filasTd = document.getElementById("tabla-admins").getElementsByTagName("tr");

  for (var i = 0; i < filasTd.length; i++) {
    var admin = filasTd[i];
    var Email = admin.getElementsByTagName("td")[3];
    var Nombre = admin.getElementsByTagName("td")[4];
    var Apellido = admin.getElementsByTagName("td")[5];
    var Nacionalidad = admin.getElementsByTagName("td")[6];
    var Telefono = admin.getElementsByTagName("td")[7];

    if (Email || Nombre || Apellido || Nacionalidad || Telefono) {
      var textoEmail = Email.textContent || Email.innerText;
      var textoNombre = Nombre.textContent || Nombre.innerText;
      var textoApellido = Apellido.textContent || Apellido.innerText;
      var textoNacionalidad = Nacionalidad.textContent || Nacionalidad.innerText;
      var textoTelefono = Telefono.textContent || Telefono.innerText;

      var mostrarTabResult = true; //mostrar tabla resultado

      for (var j = 0; j < filtros.length; j++) {
        var filtroIndAct = filtros[j].trim(); //filtro individual actual

        if (
          textoEmail.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoNombre.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoApellido.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoNacionalidad.toUpperCase().indexOf(filtroIndAct) === -1 &&
          textoTelefono.toUpperCase().indexOf(filtroIndAct) === -1
        ) {
          mostrarTabResult = false;
          break;
        }
      }
      if (mostrarTabResult) {
        admin.style.display = "";
      } else {
        admin.style.display = "none";
      }
    }
  }
}


