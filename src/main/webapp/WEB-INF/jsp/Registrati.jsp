<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="ISO-8859-1">
      <title>Registrazione</title>
    </head>

    <body>
      <!--Bootstrap-->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" />
      <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

      <!--CSS-->
      <link rel="stylesheet" href="/css/style.css" type="text/css" />
      <link rel="stylesheet" href="/css/profile.css" type="text/css" />
      </head>

      <body>

        <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top NavBar">
          <div class="container-fluid">
            <a class="navbar-brand" href="/">
              <img src="assets/LogoNoBackground.png" width="80" height="80" class="d-inline-block align-top" />
              <p class="NavBar-Title">Food Delivery</p>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
              aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
              <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                  <form method="GET" action="catalog">
                    <a class="nav-link" aria-current="page" href="javascript:;"
                      onclick="parentNode.submit();">Catalogo</a>
                  </form>
                </li>
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" href="#" id="navbarDropdown"
                    role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Categoria
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="#">Panini</a></li>
                    <li><a class="dropdown-item" href="#">Menu</a></li>
                    <li><a class="dropdown-item" href="#">Bibite</a></li>
                  </ul>
                </li>
                <li class="nav-item">
                  <form method="POST" action="cart">
                    <a class="nav-link cart-link" aria-current="page" href="javascript:;"
                      onclick="parentNode.submit();"><svg xmlns="http://www.w3.org/2000/svg" width="23" height="23"
                        fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                        <path
                          d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                      </svg>
                      <span id="cart-badge" class="badge rounded-pill bg-light text-dark">0</span>
                    </a>
                  </form>
                </li>

                <c:if test="${usernameLogged == null}">
                  <form method="POST" action="login">
                    <button class="btn loginBtn">Accedi</button>
                  </form>
                </c:if>

                <c:if test="${usernameLogged != null}">
                  <!--<form method="get" action="logout">
                  <button class="btn loginBtn">Logout</button>
                </form> -->


                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" href="#" id="navbarDropdown"
                      role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${usernameLogged} </a>

                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li><a class="dropdown-item" href="/accountInfo">Account</a></li>
                      <li>
                        <form method="POST" action="logout"><a class="dropdown-item" href="javascript:;"
                            onclick="parentNode.submit();">Logout</a></form>
                      </li>
                    </ul>
                  </li>
                </c:if>
              </ul>
            </div>
          </div>
        </nav>

        <div class="page-content">
          <div class="page-form">
            <form class="container" method="post" action="registrazioneAccount">
              <div class="form-group">
                <h3>Registrati su Food Delivery</h3>
                <input class="form-control" type="email" id="email" name="username"
                  placeholder="Inserisci il tuo indirizzo email" required>
              </div>
              <div class="form-group">
                <input class="form-control" type="password" id="password" name="password"
                  placeholder="Inserisci la tua password" required>
              </div>
              <button class="modifica" value="registraAccount">Crea un account</button>
            </form>
          </div>
        </div>

        <footer>
          <div class="container-fluid footer-container">
            <div class="footer-row">
              <div class="col-sm-3">
                <a href="/" style="text-decoration: none !important">
                  <img src="assets/Logo.png" height="50" width="50" />
                  <p class="footer-title">Food Delivery</p>
                </a>
              </div>
              <div class="col-sm-3">
                <h5 class="footer-link-title">Aiuto</h5>

                <ul class="list-unstyled mb-0">
                  <li>
                    <a class="footer-link-item" href="/contact-us">Contattaci</a>
                  </li>
                </ul>
              </div>
              <div class="col-sm-3">
                <h5 class="footer-link-title">Note Legali</h5>

                <ul class="list-unstyled mb-0">
                  <li>
                    <a class="footer-link-item" href="#!">Termini e Condizioni</a>
                  </li>
                  <li>
                    <a class="footer-link-item" href="#!">Politica sulla Privacy</a>
                  </li>
                </ul>
              </div>
              <div class="col-sm-3">
                <h5 class="footer-link-title">Seguici</h5>

                <ul class="list-unstyled">
                  <li>
                    <a class="footer-link-item" href="#!">Facebook</a>
                  </li>
                  <li>
                    <a class="footer-link-item" href="#!">Instagram</a>
                  </li>
                  <li>
                    <a class="footer-link-item" href="#!">Twitter</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="container-fluid copyright-section">
            <p class="copyright-paragraph">
              © 2021 Copyright -
              <a class="copyright-link" href="/">fooddelivery.com</a>
            </p>
          </div>
        </footer>


      </body>

    </html>