var loop = false;

$(document).ready(function()
{
    loop = setInterval(monitorear, 1100);

    $(window).on("beforeunload", function()
    {
        clearInterval(loop);
    });
});

function monitorear()
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
                url: 'monitorearEntrada',
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
                    url: 'monitorearSalida',
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

