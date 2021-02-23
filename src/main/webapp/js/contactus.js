window.onload = function () {

  /*var btnInviaEmail = document.getElementById("send-btn");
  btnInviaEmail.addEventListener("click", function() {
  inviaEmail();
  });*/

  document.getElementById("send-btn").onclick = inviaEmail;
  //document.getElementById("send-btn").onclick = sendEmail;
  document.getElementById("logout-btn").onclick = logout;
  getOrderJSON();
};


function sendEmail() {
  document.getElementById("alert").innerHTML =
    '<div class="alert alert-success" role="alert">\
    Email inviata con successo!\
  </div>';

  window.setTimeout(hideAlert, 4000);
}

function hideAlert() {
  document.getElementById("alert").innerHTML = "";
  $(":input").val("");
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


function inviaEmail(){
  //alert("ENTRATO NCULO");
  var oggetto = "Preso in carico segnalazione: " + document.getElementById("oggetto").value;
  //alert(oggetto);
  var testo = "Abbiamo in carico la sua segnalazione, risolveremo il prima possibile.\nCoridali Saluti, lo staff di Food Delivery." ;
  //alert(testo);
  var email = document.getElementById("email").value;
  //alert(email);
  
alert("Attendere...");
  $.ajax({
url: "emailSender",
method: "POST",

data: {oggetto: oggetto, testo: testo, email: email},
success: function(response){		

                  if(response === "SUCCESS"){
                    alert("Email inviata");
                         
                  }else{
                    alert("Email non inviata");
                         
                  }

},
fail: function( jqXHR, textStatus ) {
  alert( "Request failed: " + textStatus );
}

});
  document.getElementById("oggetto").value = "";
  document.getElementById("email").value = "";
  document.getElementById("TextArea").value = "";
}
