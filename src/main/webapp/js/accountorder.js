window.onload = function () {
  getAllOrder();
  getOrderJSON();
  document.getElementById("logout-btn").onclick = logout;
  var button_send = document.querySelector("#invio");
  button_send.addEventListener("click",sendRecensione);
  var button_close = document.querySelector("#btn-close");
  button_close.addEventListener("click",clear);
};

function getAllOrder() {
  var ordini = [];
  var cont = 0;
  var daRecensire = 0;
  $.ajax({
    url: "getOrdiniEffettuati",
    method: "POST",
    data: {},
    success: function (responseData) {
      ordini = JSON.parse(responseData);
      console.log(ordini);
      var ind = document.getElementById("divOrdini");
      var c = document.getElementById("divbottone");
      c.innerHTML = "";
      ind.innerHTML = "";
      if (ordini.ordini.length == 0) {
        ind.innerHTML += '<p id="noOrders">Non hai effettuato ordini</p>';
      } else {
        $.each(ordini.ordini, function (key, value) {
          console.log(value.fase);
          if (value.fase == "false") {
            cont = 1;
            console.log(value.fase);
            $("#divOrdini").append(
              '<div class="cardInfo">\
			       				<td><input id="radiobtn" value=' +
                value.id +
                ' type="radio" name="Contact0_AmericanExpress" class="check"> </td> \
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
          } else if (value.fase == "true") {
            daRecensire = 1;
            console.log(value.fase);
            $("#divOrdini").append(
              '<div class="cardInfo" id='+value.id+' > \
			        <div> <h5 id="nominativi_indirizzi"> #' +
                value.id +
                " " +
                value.indirizzo +
                ",  " +
                value.totale +
                " " +
                "&euro;" +
                "</h5></div>" + 
                '<div>' +
                "</div >" 
                + "</div>"
            );
          }
        });

        if (daRecensire != 0){
          c.innerHTML += '<button class="buttonRec" data-toggle="modal" data-target="#myModal">Recensione</button>';
        }

        if (cont != 0) {
          c.innerHTML += '<button id="remove_add">Rimuovi</button>';
          document.getElementById("remove_add").onclick = rimuovi;
        }

      }
    },
    fail: function (error) {
      console.log(error);
    },
  });
}

function rimuovi() {
  var check = $("input[type='radio']").filter(":checked").val();
  $.ajax({
    url: "annullaOrdine",
    method: "POST",
    data: { productId: check },
    success: function (response) {
      console.log(response);
      getAllOrder();
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



function sendRecensione() {
  var t = $("#text_recensione").val();
  var r = $("#idOrdine").val();
  $.ajax({
    url:"inviaRecensione",
    method:"POST",
    data:{ text:t,
            id:r
    },
    success: function(response) {
      if(response === true) {
        alert("Recensione effettuata con successo");
      }
      if(response === false) {
        alert("Non è possibile effetuare una recensione per questo ordine");
      }
      clear();
    },
    fail: function(error){
      clear();
    },
  });
}

function updateCartBadge(value) {
  var cartBadge = document.getElementById("cart-badge");
  cartBadge.innerHTML = value;
}

function clear(){
  document.getElementById("text_recensione").value = "";
  document.getElementById("idOrdine").value = "";
}
