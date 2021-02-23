var order = [];

window.onload = function () {
  document.getElementById("logout-btn").onclick = logout;
  document.getElementById("aggiungi").onclick = validateForm;

  getAllAddress();
  getOrderJSON();
  calculateTotal();
};
function getOrderJSON() {
  $.ajax({
    url: "getCurrentOrder",
    method: "POST",
    data: {},
    success: function (responseData) {
      if (responseData != "notlogged") {
        order = JSON.parse(responseData);
        updateCartBadge(order.quantitaProdotti.toString());
        calculateTotal();
      }
    },
    error: function () {
      document.getElementById("total-price").innerHTML = "";
      document.getElementById("productsBox").innerHTML =
        "<p>Nessun prodotto nel carrello :)</p>";
    },
  });
}
function calculateTotal() {
  var root = '<h5>Totale ordine</h5>\
	  <div class="ordered-products">';

  var totalPrice = order.totale;

  order.menu.map((menu) => {
    root +=
      "<p>" +
      menu.quantita +
      "x " +
      menu.nome_menu +
      " - &euro;" +
      menu.prezzo +
      "</p>";
  });

  order.panini.map((panino) => {
    root +=
      "<p>" +
      panino.quantita +
      "x " +
      panino.nome_panino +
      " - &euro;" +
      panino.prezzo +
      "</p>";
  });

  order.bevande.map((bevanda) => {
    root +=
      "<p>" +
      bevanda.quantita +
      "x " +
      bevanda.nome_bevanda +
      " - &euro;" +
      bevanda.prezzo +
      "</p>";
  });
  console.log(root);
  root +=
    "<p>Consegna - &euro;2.50</p>\
  </div> \
		  <h5>Totale: &euro;" +
    totalPrice.toFixed(2) +
    '</h5>\
		  <form class="confirm" method="POST" action="success">\
				  <div class="confirm">\
								  <button id="confirm_button" class="btn btn-outline-success">Conferma Ordine</button>\
				  </div>\
		  </form>';

  document.getElementById("total-price").innerHTML = root;

  document.querySelector("#confirm_button").onclick = addOrder;
}

function Indirizzo(username, nome_indirizzo, cap) {
  this.username = username;
  this.nome_indirizzo = nome_indirizzo;
  this.cap = cap;
}

function aggiungi_indirizzo() {
  var nome_indirizzo =
    $("#nome").val() + "  " + $("#indirizzo").val() + " " + $("#citta").val();
  var cap = $("#cap").val();
  var username = document.getElementById("aggiungi").getAttribute("name");
  var indirizzo = new Indirizzo(username, nome_indirizzo, cap);

  $.ajax({
    url: "aggiungiIndirizzo",
    method: "POST",
    data: JSON.stringify(indirizzo),
    contentType: "application/json",
    success: function (response) {
      if (response == true) {
        document.getElementById("divindirizzi").innerHTML = "";
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
  var nome = $("#nome").val();
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
      addresses = JSON.parse(responseData);

      $.each(addresses.indirizzi, function (key, value) {
        var ind = JSON.stringify(value.indirizzo + " " + value.cap);
        $("#divindirizzi").append(
          '<div class="cardInfo">\
			<td><input id="radiobtn" type="radio" name="Contact0_AmericanExpress" class="check" checked="checked" indirizzo=' +
            ind +
            '> </td> \
			<h5 id="nominativi_indirizzi">' +
            value.indirizzo +
            " " +
            value.cap +
            "</h5></div>"
        );
      });
    },
    fail: function (error) {
      console.log(error);
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

function updateCartBadge(value) {
  var cartBadge = document.getElementById("cart-badge");
  cartBadge.innerHTML = value;
}
function addOrder() {
  var address = {};
  address.indirizzo = $("input[type='radio']")
    .filter(":checked")
    .attr("indirizzo");
  address.price = order.totale;

  $.ajax({
    url: "saveOrder",
    method: "POST",
    data: JSON.stringify(address),
    contentType: "application/json",
    success: function (responseData) {
      // alert('Ordine confermato!');
    },
    error: function () {
      alert("Ordine annullato!");
    },
  });
}

function updateCartBadge(value) {
  var cartBadge = document.getElementById("cart-badge");
  cartBadge.innerHTML = value;
}
