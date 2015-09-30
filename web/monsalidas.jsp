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
    <link rel="stylesheet" href="css/jquery-ui-themes-1.11.4/themes/blitzer/jquery-ui.min.css" />

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
                    <span>Inicio</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="repentradas.jsp" >
                    <span>Consultar Entradas</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="repsalidas.jsp" >
                    <span>Consultar Salidas</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="monentradas.jsp" >
                    <span>Monitorear Entradas</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="monsalidas.jsp" >
                    <span>Monitorear Salidas</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="monentradasalida.jsp" >
                    <span>Monitorear Entradas/Salidas</span>
                </a>
            </li>
            <li class="sub-menu">
                <a href="reset.jsp" >
                    <span>Reset</span>
                </a>
            </li>
        </ul>
    </div>
</aside>

<section id="main-content">
    <section class="wrapper">
        <img class="estl" src="img/zaragoza.png"/>
        <h3 style="display: inline-block"><i class="fa fa-angle-right"></i> Línea 1 - Zaragoza Salida Nororiente</h3>
        <br>
        <hr>
        <div id="tsalida"></div>
        <hr>

    </section>
</section>



<%--Scripts--%>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.min.js"></script>
<script src="js/primeui/primeui-2.0-min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/monitor.js"></script>
</body>
</html>
