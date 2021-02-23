var order = [];

window.onload = function () {
  getAllAddress();
  getOrderJSON();

  document.getElementById("aggiungi_ind").onclick = validateForm;
  document.getElementById("logout-btn").onclick = logout;
};

function Indirizzo(username, nome_indirizzo, cap) {
  this.username = username;
  this.nome_indirizzo = nome_indirizzo;
  this.cap = cap;
}

function aggiungi_indirizzo() {
  var nome_indirizzo =
    $("#nominativo").val() +
    "  " +
    $("#indirizzo").val() +
    " " +
    $("#citta").val();
  var cap = $("#cap").val();
  var username = document.getElementById("aggiungi_ind").getAttribute("name");

  var indirizzo = new Indirizzo(username, nome_indirizzo, cap);

  $.ajax({
    url: "aggiungiIndirizzo",
    method: "POST",
    data: JSON.stringify(indirizzo),
    contentType: "application/json",
    success: function (response) {
      if (response == true) {
        getAllAddress();
      } else alert("Attenzione, indirizzo gia' presente!");
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

// controlli sul form
function validateForm() {
  var nome = $("#nominativo").val();
  var indirizzo = $("#indirizzo").val();
  var citta = $("#citta").val();
  var cap = $("#cap").val();

  if (
    nome == "" ||
    nome == null ||
    indirizzo == "" ||
    indirizzo == null ||
    citta == "" ||
    citta == null ||
    cap == "" ||
    cap == null
  ) {
    alert("Riempi tutti i campi!");
    return false;
  } else if (cap != 87036 && cap != 87100) {
    alert(
      "Mi dispiace, forniamo questo servizio solo per i comuni di Rende e Cosenza"
    );
  } else {
    aggiungi_indirizzo();
  }
}

function getAllAddress() {
  var addresses = [];

  $.ajax({
    url: "getIndirizziJSON",
    method: "POST",
    data: {},
    success: function (responseData) {
      if (responseData != "null") {
        addresses = JSON.parse(responseData);
        var indx = document.getElementById("divindirizzi");
        var c = document.getElementById("divbottone");

        indx.innerHTML = "";
        c.innerHTML = "";
        $.each(addresses.indirizzi, function (key, value) {
          var ind = JSON.stringify(value.indirizzo);

          indx.innerHTML +=
            '<div class="cardInfo">\
			<td><input id="radiobtn" type="radio" name="Contact0_AmericanExpress" class="check" checked="checked" value =' +
            ind +
            " indirizzo=" +
            ind +
            '> </td> \
			<h5 id="nominativi_indirizzi">' +
            value.indirizzo +
            " " +
            value.cap +
            "</h5></div>";
        });
        c.innerHTML += '<button id="remove_add" > Rimuovi</button>';
        document.getElementById("remove_add").onclick = rimuovi;
      } else {
        var indx = document.getElementById("divindirizzi");
        var c = document.getElementById("divbottone");

        indx.innerHTML = "";
        c.innerHTML = "";
      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

function rimuovi() {
  var p = $('input[type="radio"]').filter(":checked").attr("indirizzo");
  console.log(p);

  $.ajax({
    url: "rimuoviIndirizzo",
    method: "POST",
    data: { indirizzo: p },
    success: function (responseData) {
      getAllAddress();
    },
    error: function () {
      console.log("sono qui");
    },
  });
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

function getOrderJSON() {
  $.ajax({
    url: "getCurrentOrder",
    method: "POST",
    data: {},
    success: function (responseData) {
      if (responseData != "notlogged") {
        order = JSON.parse(responseData);
        console.log(order);
        updateCartBadge(order.quantitaProdotti.toString());
      }
    },
    fail: function () {
      console.log("ERROR JSON");
    },
  });
}

function updateCartBadge(value) {
  var cartBadge = document.getElementById("cart-badge");
  cartBadge.innerHTML = value;
}
