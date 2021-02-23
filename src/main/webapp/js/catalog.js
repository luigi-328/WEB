var order = {};

window.onload = function () {
  getOrderJSON();
  document.getElementById("logout-btn").onclick = logout;
};

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
      console.log("ERROR JSON Order");
    },
  });
}

function addToCart(nome_prodotto, formato_prodotto, tipo_prodotto, modal) {
  var quantita = undefined;
  if (tipo_prodotto != "panino") {
    var productId = nome_prodotto.replace(/\s/g, "");
    if (modal == "true") {
      quantita = $(
        "#quantita-count-" + productId + formato_prodotto + "2"
      ).val();
    } else {
      quantita = $("#quantita-count-" + productId + formato_prodotto).val();
    }
  } else {
    if (modal == "true") {
      quantita = $(
        "#quantita-count-" + nome_prodotto + formato_prodotto + "2"
      ).val();
    } else {
      quantita = $("#quantita-count-" + nome_prodotto + formato_prodotto).val();
    }
  }
  prodottoTrovato = false;

  if (tipo_prodotto == "panino") {
    order.panini.map((panino) => {
      if (
        panino.nome_panino == nome_prodotto &&
        panino.formato_panino == formato_prodotto
      ) {
        prodottoTrovato = true;
      }
    });

    if (!prodottoTrovato) {
      order.panini.push({
        nome_panino: nome_prodotto,
        formato_panino: formato_prodotto,
        quantita: quantita,
      });
    } else {
      var index = 0;
      for (var i = 0; i < order.panini.length; i++) {
        if (
          order.panini[i].nome_panino == nome_prodotto &&
          order.panini[i].formato_panino == formato_prodotto
        ) {
          break;
        }

        index++;
      }

      order.panini[index] = {
        ...order.panini[index],
        quantita: (
          parseInt(order.panini[index].quantita) + parseInt(quantita)
        ).toString(),
      };
    }
  } else if (tipo_prodotto == "bevanda") {
    order.bevande.map((bevanda) => {
      if (
        bevanda.nome_bevanda == nome_prodotto &&
        bevanda.formato_bevanda == formato_prodotto
      ) {
        prodottoTrovato = true;
      }
    });

    if (!prodottoTrovato) {
      order.bevande.push({
        nome_bevanda: nome_prodotto,
        formato_bevanda: formato_prodotto,
        quantita: quantita,
      });
    } else {
      var index = 0;
      for (var i = 0; i < order.bevande.length; i++) {
        if (
          order.bevande[i].nome_bevanda == nome_prodotto &&
          order.bevande[i].formato_bevanda == formato_prodotto
        ) {
          break;
        }

        index++;
      }

      order.bevande[index] = {
        ...order.bevande[index],
        quantita: (
          parseInt(order.bevande[index].quantita) + parseInt(quantita)
        ).toString(),
      };
    }
  } else if (tipo_prodotto == "menu") {
    order.menu.map((m) => {
      if (m.nome_menu == nome_prodotto && m.formato_menu == formato_prodotto) {
        prodottoTrovato = true;
      }
    });

    if (!prodottoTrovato) {
      order.menu.push({
        nome_menu: nome_prodotto,
        formato_menu: formato_prodotto,
        quantita: quantita,
      });
    } else {
      var index = 0;
      for (var i = 0; i < order.menu.length; i++) {
        if (
          order.menu[i].nome_menu == nome_prodotto &&
          order.menu[i].formato_menu == formato_prodotto
        ) {
          break;
        }

        index++;
      }

      order.menu[index] = {
        ...order.menu[index],
        quantita: (
          parseInt(order.menu[index].quantita) + parseInt(quantita)
        ).toString(),
      };
    }
  }

  $.ajax({
    url: "updateOrder",
    method: "POST",
    data: { json: JSON.stringify(order) },
    success: function () {
      console.log("SUCCESS");
      getOrderJSON();
    },
    fail: function () {
      console.log("ERROR WITH THE UPDATE");
    },
  });

  console.log(order);
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
