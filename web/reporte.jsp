<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">

  <title>Monitoreo de Torniquetes</title>

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
  <h1>Monitoreo de Torniquetes</h1>
</header>

<aside>
  <div id="sidebar"  class="nav-collapse ">
    <ul class="sidebar-menu" >
      <li class="sub-menu">
        <a href="index.jsp" >
          <span>Monitoreo de Torniquetes</span>
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
    <%String path= System.getProperty("user.home") +
            File.separator + "Downloads" + File.separator +
            "ReporteTorniquetes.xls";%>
    <a href="<%=path%>" id="toExcel" download="ReporteTorniquetes.xls">Exportar a Excel</a>
    <a href="<%=path%>"  download="ReporteTorniquetes.xls">Exportar a Excel</a>

    <hr id="nextReport">

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
