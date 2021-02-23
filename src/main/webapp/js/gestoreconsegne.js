window.onload = function () {
  carica();
  var el = document.getElementById("confirm_button");
  el.addEventListener("click", rimuovi);
  document.getElementById("logout-btn").onclick = logout;
};

function rimuovi() {
  var check = $("input[type='radio']").filter(":checked").val();

  $.ajax({
    url: "confermaConsegna",
    method: "POST",
    data: { productId: check },
    success: function (response) {
      if (response != null) {
        getAllOrders();
      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}
function carica() {
  var ordini = [];

  $.ajax({
    url: "getOrdiniDaConsegnareJSON",
    method: "POST",
    data: {},
    success: function (responseData) {
      ordini = JSON.parse(responseData);
      console.log(ordini);
      var ind = document.getElementById("divOrdini");
      if (ordini.ordini.length == 0) {
        ind.innerHTML +=
          '<p id="noOrders">Non ci sono ordini da consegnare!</p>';
        $("#confirm_button").hide();
      } else {
        ind.innerHTML = "";

        $.each(ordini.ordini, function (key, value) {
          $("#divOrdini").append(
            '<div class="cardInfo">\
			       				<td><input id="radiobtn" value=' +
              value.id +
              ' type="radio" name="Contact0_AmericanExpress" class="check" checked="checked"> </td> \
			       				<h5 id="nominativi_indirizzi"> #' +
              value.id +
              " " +
              value.indirizzo +
              ",  " +
              value.totale +
              " " +
              "&euro;" +
              "</h5></div>"
          );
        });
      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

function getAllOrders() {
  var ordini = [];

  $.ajax({
    url: "getOrdiniDaConsegnareJSON",
    method: "POST",
    data: {},
    success: function (responseData) {
      ordini = JSON.parse(responseData);
      console.log(ordini);
      var ind = document.getElementById("divOrdini");

      if (ordini.ordini.length == 0) {
        ind.innerHTML =
          '<p id="noOrders">Non ci sono ordini da consegnare!</p>';
        $("#confirm_button").hide();
      } else {
        ind.innerHTML = "";

        $.each(ordini.ordini, function (key, value) {
          ind.innerHTML +=
            '<div class="cardInfo">\
 				<td><input id="radiobtn" value=' +
            value.id +
            ' type="radio" name="Contact0_AmericanExpress" class="check" checked="checked"> </td> \
				 				<h5 id="nominativi_indirizzi"> #' +
            value.id +
            "   " +
            value.indirizzo +
            ",  " +
            value.totale +
            " " +
            "&euro;" +
            "</h5></div>";
        });
      }
      if (ordini.ordini.length == 0) {
        ind.innerHTML =
          '<p id="noOrders">Non ci sono ordini da consegnare!</p>';
        $("#confirm_button").hide();
      }
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
