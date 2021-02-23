<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="it">

    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Food Delivery</title>

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
      <link rel="stylesheet" href="css/style.css" type="text/css" />
      <link rel="stylesheet" href="css/catalog-style.css" type="text/css" />

      <!-- JS -->
      <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
      <script src="../../js/catalog.js"></script>
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
                <form method="GET" action="recensioni">
                  <a class="nav-link" aria-current="page" href="javascript:;"
                    onclick="parentNode.submit();">Recensioni</a>
                </form>
              </li>
              <li class="nav-item">
                <form method="GET" action="catalog">
                  <a class="nav-link" aria-current="page" href="javascript:;"
                    onclick="parentNode.submit();">Catalogo</a>
                </form>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" href="#" id="navbarDropdown"
                  role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Sfoglia
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li>
                    <form method="GET" action="panini">
                      <a class="dropdown-item" href="javascript:;" onclick="parentNode.submit();">Panini</a>
                    </form>
                  </li>
                  <li>
                    <form method="GET" action="menu">
                      <a class="dropdown-item" href="javascript:;" onclick="parentNode.submit();">Menu</a>
                    </form>
                  </li>
                  <li>
                    <form method="GET" action="bevande">
                      <a class="dropdown-item" href="javascript:;" onclick="parentNode.submit();">Bevande</a>
                    </form>
                  </li>
                </ul>

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
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle account-link" data-toggle="dropdown" href="#" href="#"
                    id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i><svg
                        xmlns="http://www.w3.org/2000/svg" width="23" fill="currentColor" class="bi bi-person"
                        viewBox="0 0 16 16">
                        <path
                          d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z">
                        </path>
                      </svg></i> Il tuo account</a>

                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/accountInfo">Area personale</a></li>
                    <li>
                      <a id="logout-btn" class="dropdown-item">Esci</a>
                    </li>
                  </ul>
                </li>
              </c:if>
            </ul>
          </div>
        </div>
      </nav>


      <div id="page-content" class="page-content" ordine="${currentOrder}">
   <c:if test="${listaPanini!=null}">
        <h2 id="page-title">Panini</h2>

        <div class="item">

          <c:forEach var="panini" items="${listaPanini}">
            <div class="card-catalog" productName="${panini.nome}" formato="${panini.formato}">
              <div class="modal fade ${panini.nome}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-body">
                      <div class="container-fluid">
                        <div class="row">
                          <div class="col-md-8 ml-auto">
                            <img class="card-img-modal" src="../../assets/panini/${panini.pathImage}.png"
                              alt="Card image cap" />
                            <h5>${panini.nome}</h5>
                          </div>
                          <p class="modal-paragraph">
                            ${panini.descrizione}
                          </p>
                          <div class="ml-auto">
                            <h8>${panini.prezzo} &euro; </h8>
                            <input class="input-item" type="number" min="1"
                              id="quantita-count-${panini.nome}${panini.formato}2" value="1">
                            <button id="addToCartBtn" class="btn-primary"
                              onclick="addToCart('${panini.nome}', '${panini.formato}', 'panino', 'true')">
                              <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor"
                                class="bi bi-cart" viewBox="0 0 16 16">
                                <path
                                  d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                              </svg>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>
              </div>
              <img data-toggle="modal" data-target=".${panini.nome}" class="card-img-catalog"
                src="../../assets/panini/${panini.pathImage}.png" alt="Card image cap" />
              <div class="card-body-catalog">
                <h5>${panini.nome}</h5>
                <h8>${panini.prezzo} &euro; </h8>
                <input class="input-item" type="number" min="1" id="quantita-count-${panini.nome}${panini.formato}"
                  value="1">
                <button id="addToCartBtn" class="btn-primary"
                  onclick="addToCart('${panini.nome}', '${panini.formato}', 'panino', 'false')">
                  <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-cart"
                    viewBox="0 0 16 16">
                    <path
                      d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                  </svg>
                </button>
              </div>
            </div>
          </c:forEach>
        </div>

  		</c:if>
        <c:if test="${listaMenu!=null}">

        <h2 id="page-title">Menu</h2>
        <div class="item">

          <c:forEach var="menu" items="${listaMenu}">
            <div class="card-catalog" productName="${menu.nome}" formato="${menu.formato}">
              <div class="modal fade menu${menu.panino.nome}" tabindex="-1" role="dialog"
                aria-labelledby="myLargeModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-body">
                      <div class="container-fluid">
                        <div class="row">
                          <div class="col-md-8 ml-auto">
                            <img class="card-img-modal" src="../../assets/menu/${menu.path_image}.png"
                              alt="Card image cap" />
                            <h5>${menu.nome}</h5>
                          </div>
                          <p class="modal-paragraph">
                            ${menu.descrizione}
                          </p>
                          <div class="ml-auto">
                            <h8>${menu.prezzo} &euro; </h8>
                            <input class="input-item" type="number" min="1"
                              id="quantita-count-${menu.getNameWithoutSpace()}${menu.formato}2" value="1">
                            <button id="addToCartBtn" class="btn-primary"
                              onclick="addToCart('${menu.nome}', '${menu.formato}', 'menu', 'true')">
                              <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor"
                                class="bi bi-cart" viewBox="0 0 16 16">
                                <path
                                  d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                              </svg>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>
              </div>
              <img data-toggle="modal" data-target=".menu${menu.panino.nome}" class="card-img-catalog"
                src="../../assets/menu/${menu.path_image}.png" alt="Card image cap" />
              <div class="card-body-catalog">
                <h5>${menu.nome}</h5>
                <h8>${menu.prezzo} &euro; </h8>
                <input class="input-item" type="number" min="1"
                  id="quantita-count-${menu.getNameWithoutSpace()}${menu.formato}" value="1">
                <button id="addToCartBtn" class="btn-primary"
                  onclick="addToCart('${menu.nome}', '${menu.formato}', 'menu', 'false')">
                  <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-cart"
                    viewBox="0 0 16 16">
                    <path
                      d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                  </svg>
                </button>
              </div>
            </div>
          </c:forEach>

        </div>
 		</c:if>
        <c:if test="${listaBevande!=null}">
        <h2 id="page-title">Bevande</h2>
        <div class="item">

          <c:forEach var="bevanda" items="${listaBevande}">
            <div class="card-catalog" productName="${bevanda.nome}" formato="${bevanda.formato}">
              <img class="card-img-catalog" src="../../assets/bevande/${bevanda.path_image}.png" alt="Card image cap" />
              <div class="card-body-catalog">
                <h5>${bevanda.nome}</h5>
                <h8>${bevanda.prezzo} &euro; </h8>
                <input class="input-item" type="number" min="1"
                  id="quantita-count-${bevanda.getNameWithoutSpace()}${bevanda.formato}" value="1">
                <button id="addToCartBtn" class="btn-primary"
                  onclick="addToCart('${bevanda.nome}', '${bevanda.formato}', 'bevanda', 'false')">
                  <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-cart"
                    viewBox="0 0 16 16">
                    <path
                      d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                  </svg>
                </button>
              </div>
            </div>
          </c:forEach>

        </div>

	  </c:if>
      </div>

      <c:if test="${listaBevande==null && listaPanini==null && listaMenu==null}">
        <h5 id="result"> Mi dispiace, la ricerca non ha prodotto risultati!</h5>
      </c:if>
      
      <!-- FOOTER -->
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