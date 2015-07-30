<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">

  <title>Sistema de Monitoreo de Armario de Pilotaje Automático</title>

  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="js/primeui/themes/bootstrap/theme.css" />
  <link rel="stylesheet" href="fonts/font-awesome-4.3.0/css/font-awesome.min.css" />
  <link href="css/bootstrap.css" rel="stylesheet">
  <link rel="stylesheet" href="js/primeui/primeui-2.0-min.css" />
  <link rel="stylesheet" href="css/jquery-ui-themes-1.11.4/themes/ui-lightness/jquery-ui.min.css" />
  <link rel="stylesheet" href="js/datetimepicker-master/jquery.datetimepicker.css" />

  <!-- Custom styles for this template -->
  <link href="css/style.css" rel="stylesheet">
  <link href="css/style-responsive.css" rel="stylesheet">



</head>
<body>

<header class="header black-bg">
  <div class="sidebar-toggle-box">
    <div data-placement="right" data-original-title="Inicio">
      <img src="img/metrol.png" id="ml"/>
    </div>
  </div>
  <h3>Sistema</h3>
</header>

<aside>
  <div id="sidebar"  class="nav-collapse ">
    <ul class="sidebar-menu" >
      <li class="sub-menu">
        <a href="index.jsp" >
          <span>Monitorear</span>
        </a>
      </li>
      <li class="sub-menu">
        <a href="reporte.jsp" >
          <span>Reportes</span>
        </a>
      </li>
    </ul>
  </div>
</aside>

<section id="main-content">
  <section class="wrapper">

    <h3><i class="fa fa-angle-right"></i> Línea 1 - Zaragoza Entrada Nororiente</h3>
    <hr>

    <label for="dateFrom">De: </label><input type="text" id="dateFrom">
    <label for="dateTo">A: </label><input type="text" id="dateTo">
    <button id="buscar" type="button">Buscar</button>
    <hr>

    <div id="t1"></div>

  </section>
</section>

<footer>

</footer>

<%--Scripts--%>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.min.js"></script>
<script src="js/primeui/primeui-2.0-min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/datetimepicker-master/jquery.datetimepicker.js"></script>
<script src="js/report.js"></script>
</body>
</html>