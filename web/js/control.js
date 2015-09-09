var noClose =  false;
var loop = false;
var error = false;

$(document).ready(function()
{
    $("#bt1").puibutton(
        {
            icon: 'fa-play'
        }
    );

    $("#bt1").on("click", function()
    {
        if ($(this).html() == "Detener")
        {
            noClose = false;
            clearInterval(loop);
            $(this).html("Guardar");
            $("#bt1").puibutton('icon', 'fa fa-play');
        }
        else
        {
            save();

            if (!error)
                loop = setInterval(save, 1800000);

            noClose = true;
            $(this).html("Detener");
            $("#bt1").puibutton('icon', 'fa fa-stop');
        }
    });

    $(window).on("beforeunload", function()
    {
        if (noClose)
        {
            return "¿Se estan guardando datos desea salir?";
        }
    });
});

function save()
{
    $('#testado').puidatatable(
    {
        responsive : true,
        columns:
            [
                //{field:'linea', headerText: 'Linea'},
                //{field:'estacion', headerText: 'Estacion'},
                //{field:'acceso', headerText: 'Acceso'},
                {field:'torniq', headerText: 'Torniquete'},
                {field:'boleto', headerText: 'Boletos'},
                {field:'tarjeta', headerText: 'Tarjeta'},
                {field:'total', headerText: 'Total'},
                {field:'noautorizado', headerText: 'No Autorizado'},
                {field:'estado', headerText: 'Estado', content: getState}
                //{field:'fecha', headerText: 'Fecha', sortable:true}
            ],
        datasource: function(callback)
        {
            $.ajax({
                type: 'POST',
                url: 'estado',
                dataType: 'json',
                context: this,
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

