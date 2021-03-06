var noClose =  false;
var loop = false;
var error = false;

$(document).ready(function()
{
    $("#bt1").puibutton({
        icon: 'fa fa-play'
    });

    $("#bt1").on("click", function()
    {
        if ($(this).html() == "Detener")
        {
            noClose = false;
            clearInterval(loop);
            $(this).hide();
            $(this).html("Guardar");
            $("#bt1").puibutton(
                {
                    icon: 'fa fa-play'
                });
            $(this).show();
        }
        else
        {
            save();

            // Intervalo de captura de datos
            // 1000 = 1 s
            // 1800000 = 30 Min
            // 3600000 = 1 H
            if (!error)
                loop = setInterval(save, 1800000);

            noClose = true;
            $(this).hide();
            $(this).html("Detener");
            $("#bt1").puibutton(
                {
                    icon: 'fa fa-stop'
                });
            $(this).show();
        }

    });

    $(window).on("beforeunload", function()
    {
        if (noClose)
        {
            return "�Se estan guardando datos desea salir?";
        }
    });
});

function save()
{
    $('#tentrada').puidatatable(
    {
        caption: "Entradas",
        responsive : true,
        columns:
            [
                {field:'torniq', headerText: 'Torniquete'},
                {field:'boleto', headerText: 'Boletos'},
                {field:'noautorizado', headerText: 'No Autorizado'},
                {field:'tarjeta', headerText: 'Tarjeta'},
                {field:'total', headerText: 'Total'},
                {field:'estado', headerText: 'Estado', content: getState}
            ],
        datasource: function(callback)
        {
            $.ajax({
                type: 'POST',
                url: 'entradas',
                dataType: 'json',
                context: this,
                //async: false,
                success: function(response)
                {
                    if (response.error)
                    {
                        console.log(response.error);
                    }
                    else
                    {
                        callback.call(this, response);
                    }
                },
                error: function (textStatus, errorThrown)
                {
                    error = true;
                    console.log("Error->" + errorThrown + ' : ' + textStatus);
                }
            });
        },
        selectionMode: 'single'
    });

    $('#tsalida').puidatatable(
        {
            caption: "Salidas",
            responsive : true,
            columns:
                [
                    {field:'torniq', headerText: 'Torniquete'},
                    {field:'salida', headerText: 'Salidas'}
                ],
            datasource: function(callback)
            {
                $.ajax({
                    type: 'POST',
                    url: 'salidas',
                    dataType: 'json',
                    context: this,
                    //async: false,
                    success: function(response)
                    {
                        if (response.error)
                        {
                            console.log(response.error);
                        }
                        else
                        {
                            callback.call(this, response);
                        }
                    },
                    error: function (textStatus, errorThrown)
                    {
                        error = true;
                        console.log("Error->" + errorThrown + ' : ' + textStatus);
                    }
                });
            },
            selectionMode: 'single'
        });
}

function getState(data)
{
    if (data.estado == "Habilitado")
    {
        return $("<div title='Estado'>" +
            "<img src='img/ok.png' width='50px' />" +
            "</div>");
    }
    else if (data.estado == "Boleto inhabilitado")
    {
        return $("<div title='Estado'>" +
            "<img src='img/notpass.png' width='50px' />" +
            "</div>");
    }
    else
    {
        return $("<div title='Estado'>" +
            "<img src='img/error.png' width='50px'/>" +
            "</div>");
    }
}

