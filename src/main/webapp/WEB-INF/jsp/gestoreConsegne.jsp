<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="it">

    <head>
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
      <link rel="stylesheet" href="css/gestoreconsegne.css" type="text/css" />
      <!-- JS -->
      <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
      <script src="../../js/gestoreconsegne.js"></script>


    </head>

    <body>
      <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top NavBar">
        <div class="container-fluid">
          <a class="navbar-brand" href="">
            <img src="assets/LogoNoBackground.png" width="80" height="80" class="d-inline-block align-top" />
            <p class="NavBar-Title">Food Delivery</p>
          </a>

          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav ml-auto">

              <li class="nav-item">

              </li>
              <c:choose>
                <c:when test="${usernameLogged==null}">
                  <form method="POST" action="login">
                    <button class="btn loginBtn">Accedi</button>
                  </form>
                </c:when>
                <c:otherwise>
                  <button id="logout-btn" class="btn loginBtn">Logout</button>
                </c:otherwise>
              </c:choose>
            </ul>
          </div>
        </div>
      </nav>

      <!-- HEADER -->
      <div class="headerSection">
        <div id="divOrdini">

        </div>
        <div id="divButton">
          <button id="confirm_button" type="button" class="btn btn-success">Registra consegna</button>

        </div>

      </div>

    </body>

    </html>