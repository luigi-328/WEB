window.onload = function () {
  setId();
  document.getElementById("logout-btn").onclick = logout;

  getOrderJSON();
};

function setId() {
  var id;

  $.ajax({
    url: "getId",
    method: "POST",
    data: {},
    success: function (responseData) {
      id = responseData;
      console.log(id);
      $(".id_ordine").append("<h1> Id ordine: # " + id + "</h1>");
    },
    fail: function (error) {
      console.log("error");
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
