var order = [];

function updateCartBadge(value) {
  var cartBadge = document.getElementById("cart-badge");
  cartBadge.innerHTML = value;
}

function removeProduct(productName, productFormat, productType) {
  $.ajax({
    url: "removeProduct",
    method: "POST",
    data: {
      productName: productName,
      productFormat: productFormat,
      productType: productType,
    },
    success: function (response) {
      if (response != null) {
        getOrderJSON();
      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

function addQuantity(productName, productFormat, productType) {
  $.ajax({
    url: "addQuantity",
    method: "POST",
    data: {
      productName: productName,
      productFormat: productFormat,
      productType: productType,
    },
    success: function (response) {
      if (response != null) {
        getOrderJSON();
      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

function removeQuantity(productName, productFormat, productType) {
  $.ajax({
    url: "removeQuantity",
    method: "POST",
    data: {
      productName: productName,
      productFormat: productFormat,
      productType: productType,
    },
    success: function (response) {
      if (response != null) {
        getOrderJSON();
      }
    },
    fail: function (error) {
      console.log(error);
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

        var productsBox = document.getElementById("productsBox");
        var root = "";

        var productsCount =
          order.menu.length + order.panini.length + order.bevande.length;

        if (productsCount == 0) {
          root += "<p>Nessun prodotto nel carrello :)</p>";
          document.getElementById("total-price").innerHTML = "";
        } else {
          order.menu.map((menu) => {
            ingredients =
              '<p class="product-ingredients">' +
              menu.nome_panino +
              "(" +
              menu.formato_panino +
              ")" +
              ", " +
              menu.nome_bevanda +
              "(" +
              menu.formato_bevanda +
              ")" +
              "</p>";

            root +=
              '<div class="product-box"> \
            <img src="../assets/menu/' +
              menu.immagine_menu +
              '.png" class="product-image" /> \
            <div class="product-details"> \
              <h3 class="product-title">' +
              menu.nome_menu +
              " - " +
              menu.formato_menu +
              "</h3>" +
              ingredients +
              '<div class="quantity-option">\
                <p class="product-quantity">Quantità: ' +
              menu.quantita +
              '</p>\
                <div class="option-btns">\
                  <button class="btn add-btn" onclick="addQuantity(\'' +
              menu.nome_menu +
              "', '" +
              menu.formato_menu +
              '\', \'menu\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-plus-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3a.5.5 0 0 1 1 0z" />\
                    </svg>\
                  </button>\
                  <button class="btn minus-btn" onclick="removeQuantity(\'' +
              menu.nome_menu +
              "', '" +
              menu.formato_menu +
              '\', \'menu\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-dash-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2.5 7.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1z" />\
                    </svg>\
                  </button>\
                </div>\
              </div>\
            </div>\
            <div class="price-delete">\
              <h2 class="product-price">' +
              menu.prezzo +
              '€</h2>\
              <a class="btn delete-btn" onclick="removeProduct(\'' +
              menu.nome_menu +
              "', '" +
              menu.formato_menu +
              '\', \'menu\')">\
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                  class="bi bi-trash-fill" viewBox="0 0 16 16">\
                  <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />\
                </svg>\
              </a>\
              </form>\
            </div>\
          </div>\
          ';
          });

          order.panini.map((panino) => {
            ingredients =
              '<p class="product-ingredients">Ingredienti non disponibili.</p>';

            if (panino.ingredienti.length != 0) {
              ingredientsStr = "";
              countIngredienti = 0;
              panino.ingredienti.map((ingrediente) => {
                if (countIngredienti + 1 == panino.ingredienti.length) {
                  ingredientsStr += ingrediente;
                } else {
                  ingredientsStr += ingrediente + ", ";
                }

                countIngredienti++;
              });
            }

            ingredients =
              '<p class="product-ingredients">' + ingredientsStr + "</p>";
            root +=
              '<div class="product-box"> \
            <img src="../assets/panini/' +
              panino.immagine_panino +
              '.png" class="product-image" /> \
            <div class="product-details"> \
              <h3 class="product-title">' +
              panino.nome_panino +
              " - " +
              panino.formato_panino +
              "</h3>" +
              ingredients +
              '<div class="quantity-option">\
                <p class="product-quantity">Quantità: ' +
              panino.quantita +
              '</p>\
                <div class="option-btns">\
                  <button class="btn add-btn" onclick="addQuantity(\'' +
              panino.nome_panino +
              "', '" +
              panino.formato_panino +
              '\', \'panino\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-plus-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3a.5.5 0 0 1 1 0z" />\
                    </svg>\
                  </button>\
                  <button class="btn minus-btn" onclick="removeQuantity(\'' +
              panino.nome_panino +
              "', '" +
              panino.formato_panino +
              '\', \'panino\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-dash-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2.5 7.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1z" />\
                    </svg>\
                  </button>\
                </div>\
              </div>\
            </div>\
            <div class="price-delete">\
              <h2 class="product-price">' +
              panino.prezzo +
              '€</h2>\
              <a class="btn delete-btn" onclick="removeProduct(\'' +
              panino.nome_panino +
              "', '" +
              panino.formato_panino +
              '\', \'panino\')">\
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                  class="bi bi-trash-fill" viewBox="0 0 16 16">\
                  <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />\
                </svg>\
              </a>\
              </form>\
            </div>\
          </div>\
          ';
          });

          order.bevande.map((bevanda) => {
            ingredients =
              '<p class="product-ingredients">Ingredienti non disponibili.</p>';
            root +=
              '<div class="product-box"> \
            <img src="../assets/bevande/' +
              bevanda.immagine_bevanda +
              '.png" class="product-image" /> \
            <div class="product-details"> \
              <h3 class="product-title">' +
              bevanda.nome_bevanda +
              " - " +
              bevanda.formato_bevanda +
              "</h3>" +
              ingredients +
              '<div class="quantity-option">\
                <p class="product-quantity">Quantità: ' +
              bevanda.quantita +
              '</p>\
                <div class="option-btns">\
                  <button class="btn add-btn" onclick="addQuantity(\'' +
              bevanda.nome_bevanda +
              "', '" +
              bevanda.formato_bevanda +
              '\', \'bevanda\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-plus-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3a.5.5 0 0 1 1 0z" />\
                    </svg>\
                  </button>\
                  <button class="btn minus-btn" onclick="removeQuantity(\'' +
              bevanda.nome_bevanda +
              "', '" +
              bevanda.formato_bevanda +
              '\', \'bevanda\')">\
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                      class="bi bi-dash-square-fill" viewBox="0 0 16 16">\
                      <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2.5 7.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1z" />\
                    </svg>\
                  </button>\
                </div>\
              </div>\
            </div>\
            <div class="price-delete">\
              <h2 class="product-price">' +
              bevanda.prezzo +
              '€</h2>\
              <a class="btn delete-btn" onclick="removeProduct(\'' +
              bevanda.nome_bevanda +
              "', '" +
              bevanda.formato_bevanda +
              '\', \'bevanda\')">\
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\
                  class="bi bi-trash-fill" viewBox="0 0 16 16">\
                  <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />\
                </svg>\
              </a>\
              </form>\
            </div>\
          </div>\
          ';
          });

          calculateTotal();
        }

        productsBox.innerHTML = root;
        updateCartBadge(order.quantitaProdotti.toString());
      } else {
        document.getElementById("total-price").innerHTML = "";
        document.getElementById("productsBox").innerHTML =
          "<p>Nessun prodotto nel carrello :)</p>";
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
      " - €" +
      menu.prezzo +
      "</p>";
  });

  order.panini.map((panino) => {
    root +=
      "<p>" +
      panino.quantita +
      "x " +
      panino.nome_panino +
      " - €" +
      panino.prezzo +
      "</p>";
  });

  order.bevande.map((bevanda) => {
    root +=
      "<p>" +
      bevanda.quantita +
      "x " +
      bevanda.nome_bevanda +
      " - €" +
      bevanda.prezzo +
      "</p>";
  });

  root +=
    "<p>Consegna - €2.50</p>\
  </div> \
  <h5>Totale: €" +
    totalPrice.toFixed(2) +
    '</h5>\
    <form method="POST" action="confirm_order">\
    	<button class="btn continue-order-btn">Prosegui</button>\
    </form>';

  document.getElementById("total-price").innerHTML = root;
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

function clearAll() {
  document.getElementById("productsBox").innerHTML =
    "<p>Nessun prodotto nel carrello :)</p>";
}

window.onload = function () {
  getOrderJSON();
  document.getElementById("logout-btn").onclick = logout;
  document.getElementById("clear-btn").onclick = clearAll;
};
