
function filtrarTablaAccountant() {
  var filtros = document.getElementById("filtrar-tabla-accountant").value.toUpperCase().split(",");
  var filasli = document.getElementById("tabla-accountant").getElementsByTagName("section");

  for (var i = 0; i < filasli.length; i++) {
      var accountant = filasli[i];
      var Matricula = accountant.getElementsByTagName("li")[1];
      var Nombre = accountant.getElementsByTagName("li")[0];
      var Apellido = accountant.getElementsByTagName("li")[0];
      var Especializacion = accountant.getElementsByTagName("li")[2];
      var Honorarios = accountant.getElementsByTagName("li")[3];

      if (Nombre || Apellido || Matricula || Especializacion || Honorarios) {
        var textoMatricula = Matricula.textContent || Matricula.innerText;
        var textoNombre = Nombre.textContent || Nombre.innerText;
        var textoApellido = Apellido.textContent || Apellido.innerText;
        var textoEspecializacion = Especializacion.textContent || Especializacion.innerText;
        var textoHonorarios = Honorarios.textContent || Honorarios.innerText;


      var mostrarTabResult = true; //mostrar tabla resultado

      for (var j = 0; j < filtros.length; j++) {
        var filtroIndAct = filtros[j].trim(); //filtro individual actual

        if (
          textoMatricula.toUpperCase().indexOf(filtroIndAct) === -1 &&
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


/*function filtrarTablaAccountant() {
  var filtros = document.getElementById("filtrar-tabla-accountant").value.toUpperCase().split(",");
  var filasli = document.getElementById("cards-accountant").getElementsByTagName("li");

  for (var i = 0; i < filasli.length; i++) {
    var accountant = filasli[i];
    var Matricula = accountant.getElementsByTagName("li")[1];
    var Nombre = accountant.getElementsByTagName("li")[0];
    var Apellido = accountant.getElementsByTagName("li")[0];
    var Especializacion = accountant.getElementsByTagName("li")[2];
    var Honorarios = accountant.getElementsByTagName("li")[3];

    if (Nombre || Apellido || Matricula || Especializacion || Honorarios) {
      var textoMatricula = Matricula.textContent || Matricula.innerText;
      var textoNombre = Nombre.textContent || Nombre.innerText;
      var textoApellido = Apellido.textContent || Apellido.innerText;
      var textoEspecializacion = Especializacion.textContent || Especializacion.innerText;
      var textoHonorarios = Honorarios.textContent || Honorarios.innerText;

      var mostrarTabResult = true; //mostrar tabla resultado

      for (var j = 0; j < filtros.length; j++) {
        var filtroIndAct = filtros[j].trim(); //filtro individual actual

        if (
          textoMatricula.toUpperCase().indexOf(filtroIndAct) === -1 &&
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
}*/

