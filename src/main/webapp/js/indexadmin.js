var almeno_un_ingrediente = false;
var ingredienti = [];

window.onload = function () {
  var modal_add = document.querySelector("#mySelect_add");
  modal_add.addEventListener("change", print_add);
  print_add();

  var modal_remove = document.querySelector("#mySelect_remove");
  modal_remove.addEventListener("change", print_remove);
  print_remove();

  var button_remove = document.querySelector("#btn_remove");
  button_remove.addEventListener("click", rimuovi);

  var button_add = document.querySelector("#btn_add");
  button_add.addEventListener("click", aggiungi);

  var button_clear_add = document.querySelector("#btn_clear_add");
  button_clear_add.addEventListener("click", clear_add);

  var button_clear_remove = document.querySelector("#button_clear_remove");
  button_clear_remove.addEventListener("click", clear_remove);
  almeno_un_ingrediente = false;

  document.getElementById("logout-btn").onclick = logout;
};

function rimuovi() {
  if (validateForm_rimossi()) {
    if (document.getElementById("mySelect_remove").value == "Panini") {
      rimuoviPanino();
    } else if (document.getElementById("mySelect_remove").value == "Bevande") {
      rimuoviBevanda();
    } else if (document.getElementById("mySelect_remove").value == "Menu") {
      rimuoviMenu();
    }
  }
}

function rimuoviPanino() {
  var nomePanino = $("#nome_panino_rimosso").val();
  var formatoPanino = $("#formato_panino_rimosso").val();

  jsonPanino = {
    tipo: "panino",
    nome: nomePanino,
    formato: formatoPanino,
  };

  sendJSONToRemove(JSON.stringify(jsonPanino));
}

function rimuoviBevanda() {
  var nomeBevanda = $("#nome_bevanda_rimossa").val();
  var formatoBevanda = $("#formato_bevanda_rimossa").val();

  jsonBevanda = {
    tipo: "bevanda",
    nome: nomeBevanda,
    formato: formatoBevanda,
  };

  sendJSONToRemove(JSON.stringify(jsonBevanda));
}

function rimuoviMenu() {
  var nomeMenu = $("#nome_menu_rimosso").val();
  var formatoMenu = $("#formato_menu_rimosso").val();

  jsonMenu = {
    tipo: "menu",
    nome: nomeMenu,
    formato: formatoMenu,
  };

  sendJSONToRemove(JSON.stringify(jsonMenu));
}

function aggiungi() {
  if (validateForm_aggiunti()) {
    if (document.getElementById("mySelect_add").value == "Panini") {
      aggiungiPanino();
    } else if (document.getElementById("mySelect_add").value == "Bevande") {
      aggiungiBevanda();
    } else if (document.getElementById("mySelect_add").value == "Menu") {
      aggiungiMenu();
    }
  }
}

function aggiungiPanino() {
  var nomePanino = $("#nome_panino").val();
  var formatoPanino = $("#formato_panino").val();
  var prezzoPanino = $("#prezzo_panino").val();
  var immaginePanino = $("#nome_immagine_panino").val();
  var descrizione = $("#descrizione_panino").val();

  jsonPanino = {
    tipo: "panino",
    nome: nomePanino,
    formato: formatoPanino,
    prezzo: prezzoPanino,
    immagine: immaginePanino,
    ingredienti: ingredienti,
    descrizione: descrizione,
  };

  sendJSONToAdd(JSON.stringify(jsonPanino));
}

function aggiungiBevanda() {
  var nomeBevanda = $("#nome_bevanda").val();
  var formatoBevanda = $("#formato_bevanda").val();
  var prezzoBevanda = $("#prezzo_bevanda").val();
  var immagineBevanda = $("#nome_immagine_bevanda").val();

  jsonBevanda = {
    tipo: "bevanda",
    nome: nomeBevanda,
    formato: formatoBevanda,
    prezzo: prezzoBevanda,
    immagine: immagineBevanda,
  };

  sendJSONToAdd(JSON.stringify(jsonBevanda));
}

function aggiungiMenu() {
  var nomeMenu = $("#nome_menu").val();
  var formatoMenu = $("#formato_menu").val();
  var prezzoMenu = $("#prezzo_menu").val();
  var immagineMenu = $("#nome_immagine_menu").val();
  var nome_panino = $("#nome_panino_menu").val();
  var formato_panino = $("#formato_panino_menu").val();
  var nome_bevanda = $("#nome_bevanda_menu").val();
  var formato_bevanda = $("#formato_bevanda_menu").val();
  var descrizione = $("#descrizione_menu").val();

  jsonMenu = {
    tipo: "menu",
    nome: nomeMenu,
    formato: formatoMenu,
    prezzo: prezzoMenu,
    immagine: immagineMenu,
    nome_panino: nome_panino,
    formato_panino: formato_panino,
    nome_bevanda: nome_bevanda,
    formato_bevanda: formato_bevanda,
    descrizione: descrizione,
  };

  sendJSONToAdd(JSON.stringify(jsonMenu));
}

function sendJSONToAdd(json) {
  $.ajax({
    url: "addProductToDB",
    method: "POST",
    data: { json: json },
    success: function (response) {
      if (response != null) {
        alert("Prodotto aggiunto con successo!");
        clear_add();
      } else {
        alert("Errore con l'aggiunta");
      }
    },
    fail: function (error) {
      alert("Errore con l'aggiunta");
    },
  });
}

function sendJSONToRemove(json) {
  $.ajax({
    url: "removeProductFromDB",
    method: "POST",
    data: { json: json },
    success: function (response) {
      if (response != null) {
        alert("Prodotto rimosso con successo!");
        clear_add();
      } else {
        alert("Errore con la rimozione");
      }
    },
    fail: function (error) {
      alert("Errore con la rimozione");
    },
  });
}

function validateForm_aggiunti() {
  if (document.getElementById("mySelect_add").value === "Panini") {
    var nome = $("#nome_panino").val();
    var formato = $("#formato_panino").val();
    var prezzo = $("#prezzo_panino").val();
    var immagine = $("#nome_immagine_panino").val();

    console.log(nome);
    console.log(formato);
    console.log(prezzo);
    console.log(immagine);
    console.log(almeno_un_ingrediente);

    if (
      nome == "" ||
      nome == null ||
      formato == "" ||
      formato == null ||
      prezzo == "" ||
      prezzo == null ||
      immagine == "" ||
      immagine == null ||
      almeno_un_ingrediente == false
    ) {
      alert("Riempi tutti i campi!");
      return false;
    }

    var list = document.querySelector(".results");
    list.innerHTML = "";

    return true;
  }

  if (document.getElementById("mySelect_add").value === "Bevande") {
    var nome = $("#nome_bevanda").val();
    var formato = $("#formato_bevanda").val();
    var prezzo = $("#prezzo_bevanda").val();
    var immagine = $("#nome_immagine_bevanda").val();

    if (
      nome == "" ||
      nome == null ||
      formato == "" ||
      formato == null ||
      immagine == "" ||
      immagine == null ||
      prezzo == "" ||
      prezzo == null
    ) {
      alert("Riempi tutti i campi!");
      return false;
    }
  }
  if (document.getElementById("mySelect_add").value === "Menu") {
    var nome = $("#nome_menu").val();
    var formato = $("#formato_menu").val();
    var nome_panino = $("#nome_panino_menu").val();
    var formato_panino = $("#formato_panino_menu").val();
    var nome_bevanda = $("#nome_bevanda_menu").val();
    var formato_bevanda = $("#formato_bevanda_menu").val();
    var prezzo_menu = $("#prezzo_menu").val();
    var immagine = $("#nome_immagine_menu").val();

    if (
      nome == "" ||
      nome == null ||
      formato == "" ||
      formato == null ||
      nome_panino == "" ||
      nome_panino == null ||
      formato_panino == "" ||
      formato_panino == null ||
      nome_bevanda == "" ||
      nome_bevanda == null ||
      formato_bevanda == "" ||
      formato_bevanda == null ||
      immagine == "" ||
      immagine == null ||
      prezzo_menu == "" ||
      prezzo_menu == null
    ) {
      alert("Riempi tutti i campi!");
      return false;
    }
  }

  return true;
}

function print_add() {
  var x = document.querySelector(".addform");

  if (document.getElementById("mySelect_add").value === "Panini") {
    x.innerHTML =
      ' <form id="form_add_panini">\
						<label for="panino"> Nome panino:</label><br>\
						  <input type="text" id="nome_panino" name="fname"><br>\
						  <label for="lname">Formato:</label><br>\
              <input type="text" id="formato_panino" name="fname"><br>\
              <label for="lname">Descrizione:</label><br>\
							<input type="text" id="descrizione_panino" name="fname"><br>\
								<label for="lname">Prezzo:</label><br>\
								<input type="text" id="prezzo_panino" name="fname"><br>\
								<label for="lname">Nome immagine:</label><br>\
								<input type="text" id="nome_immagine_panino" name="fname"><br>\
								<form id="add_ingredients">\
								<label for="lname">Aggiungi ingredienti al panino:</label><br>\
							    <input id="in" type="text" />\
							    <input id="aggiungi_ingr" value="Aggiungi" type="submit" />\
							</form>\
							<div class="results">\
						    <ul id="ingredients" class="list">\
							</ul>\
							</form>';

    var aggiungi_ingr = document.querySelector("#aggiungi_ingr");
    aggiungi_ingr.addEventListener("click", printIngredients);
  }

  if (document.getElementById("mySelect_add").value === "Bevande") {
    x.innerHTML =
      '<form id="form_add_bevande">\
						<label for="panino"> Nome bevanda:</label><br>\
						  <input type="text" id="nome_bevanda" name="fname"><br>\
						  <label for="lname">Formato:</label><br>\
              <input type="text" id="formato_bevanda" name="fname"><br>\
								<label for="lname">Nome immagine:</label><br>\
								<input type="text" id="nome_immagine_bevanda" name="fname"><br>\
								<form id="add_ingredients">\
								<label for="lname">Prezzo:</label><br>\
								<input type="text" id="prezzo_bevanda" name="fname"><br>\
						</form>';
  }
  if (document.getElementById("mySelect_add").value === "Menu") {
    x.innerHTML =
      ' <form id="form_add_menu">\
			<label for="panino"> Nome menu:</label><br>\
						  <input type="text" id="nome_menu" name="fname"><br>\
						<label for="lname">Formato:</label><br>\
              <input type="text" id="formato_menu" name="fname"><br>\
              <label for="lname">Descrizione:</label><br>\
							<input type="text" id="descrizione_menu" name="fname"><br>\
						<label for="panino"> Panino:</label><br>\
							<input type="text" id="nome_panino_menu" name="fname"><br>\
						<label for="panino"> Formato panino:</label><br>\
							<input type="text" id="formato_panino_menu" name="fname"><br>\
						<label for="panino"> Bevanda:</label><br>\
							<input type="text" id="nome_bevanda_menu" name="fname"><br>\
						<label for="panino"> Formato bevanda:</label><br>\
							<input type="text" id="formato_bevanda_menu" name="fname"><br>\
						<label for="panino"> Prezzo menu:</label><br>\
							<input type="text" id="prezzo_menu" name="fname"><br>\
						<label for="lname">Nome immagine:</label><br>\
						<input type="text" id="nome_immagine_menu" name="fname"><br>\
						<form id="add_ingredients">\
						</form>';
  }
}

function print_remove() {
  var y = document.querySelector(".form_remove");

  if (document.getElementById("mySelect_remove").value === "Panini") {
    y.innerHTML =
      '<form id="form_remove_panini">\
							<label for="panino"> Nome panino:</label><br>\
								<input type="text" id="nome_panino_rimosso" name="fname"><br>\
							<label for="lname">Formato:</label><br>\
								<input type="text" id="formato_panino_rimosso" name="fname"><br>\
						</form>';
  }

  if (document.getElementById("mySelect_remove").value === "Bevande") {
    y.innerHTML =
      '<form id="form_remove_bevande">\
							<label for="panino"> Nome bevanda:</label><br>\
						  		<input type="text" id="nome_bevanda_rimossa" name="fname"><br>\
						  	<label for="lname">Formato:</label><br>\
								<input type="text" id="formato_bevanda_rimossa" name="fname"><br>\
						</form>';
  }
  if (document.getElementById("mySelect_remove").value === "Menu") {
    y.innerHTML =
      '<form id="form_remove_menu">\
							<label for="panino"> Nome menu:</label><br>\
								<input type="text" id="nome_menu_rimosso" name="fname"><br>\
							<label for="lname">Formato:</label><br>\
								<input type="text" id="formato_menu_rimosso" name="fname"><br>\
						</form>';
  }
}
function printIngredients(event) {
  var val = $("#in").val();
  if (val == null || val == "") return;
  almeno_un_ingrediente = true;
  $("ul.list").append("<li>" + val + "</li>");
  ingredienti.push(val);
  event.preventDefault();
}

function validateForm_rimossi() {
  if (document.getElementById("mySelect_remove").value === "Panini") {
    var nome = $("#nome_panino_rimosso").val();
    var formato = $("#formato_panino_rimosso").val();

    if (nome == "" || nome == null || formato == "" || formato == null) {
      alert("Riempi tutti i campi!");
      return false;
    }

    return true;
  }

  if (document.getElementById("mySelect_remove").value === "Bevande") {
    var nome = $("#nome_bevanda_rimossa").val();
    var formato = $("#formato_bevanda_rimossa").val();

    if (nome == "" || nome == null || formato == "" || formato == null) {
      alert("Riempi tutti i campi!");
      return false;
    }
    return true;
  }
  if (document.getElementById("mySelect_remove").value === "Menu") {
    var nome = $("#nome_menu_rimosso").val();
    var formato = $("#formato_menu_rimosso").val();

    if (nome == "" || nome == null || formato == "" || formato == null) {
      alert("Riempi tutti i campi!");
      return false;
    }
    return true;
  }

  return false;
}

function clear_add() {
  if (document.getElementById("mySelect_add").value === "Panini") {
    var list = document.querySelector(".results");
    list.innerHTML = "";
    document.getElementById("form_add_panini").reset();
  }

  if (document.getElementById("mySelect_add").value === "Bevande")
    document.getElementById("form_add_bevande").reset();

  if (document.getElementById("mySelect_add").value === "Menu")
    document.getElementById("form_add_menu").reset();
}

function clear_remove() {
  if (document.getElementById("mySelect_remove").value === "Panini")
    document.getElementById("form_remove_panini").reset();

  if (document.getElementById("mySelect_remove").value === "Bevande")
    document.getElementById("form_remove_bevande").reset();

  if (document.getElementById("mySelect_remove").value === "Menu")
    document.getElementById("form_remove_menu").reset();
}

function logout() {
  $.ajax({
    url: "logout",
    method: "POST",
    data: {},
    success: function () {
      location.replace("/");
    },
  });
}
