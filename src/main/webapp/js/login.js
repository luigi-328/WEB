function login() {
  var username = $("#email").val();
  var password = $("#password").val();

  $.ajax({
    url: "loginAccount",
    method: "POST",
    data: { username: username, password: password },
    success: function (responseData) {
      if (responseData) {
        if (username == "gestore@gestore") {
          window.location.replace("gestoreConsegne");
        } else if (username == "admin@admin") {
          window.location.replace("indexadmin");
        } else {
          window.location.replace("/");
        }
      } else {
        alert("Credenziali errate");
      }
    },

    fail: function (error) {
      alert("Errore. Riprova");
      console.log(error);
    },
  });
}

function signup() {
  document.location.replace("signup");
}

window.onload = function () {
  document.getElementById("login-btn").onclick = login;
  document.getElementById("signup-btn").onclick = signup;
};
