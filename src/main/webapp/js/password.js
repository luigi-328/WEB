window.onload = function () {
  document.getElementById("cambioPass").onclick = cambiaPassword;
  document.getElementById("logout-btn").onclick = logout;

  getOrderJSON();
};

function cambiaPassword() {
  var p = document.getElementById("password").value;

  $.ajax({
    url: "cambiaPassword",
    method: "POST",
    data: { password: p },
    success: function (response) {
      if (response == true) {
        console.log("QUI");
        alert("Cambio password effettuato con successo");
      } else {
        alert("Non è stato possibile modificare la password");
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
