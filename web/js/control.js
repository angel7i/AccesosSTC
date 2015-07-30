var noClose =  false;
var loop = false;
var error = false;

$(document).ready(function()
{
    $("#bt1").on("click", function()
    {
        if ($(this).html() == "Detener")
        {
            noClose = false;
            clearInterval(loop);
            $(this).html("Guardar");
            $(this).removeClass("btn btn-danger").addClass("btn btn-success");
        }
        else
        {
            save();

            if (!error)
                loop = setInterval(save, 1800000);

            noClose = true;
            $(this).html("Detener");
            $(this).removeClass("btn btn-success").addClass("btn btn-danger");
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
                //{field:'noaut', headerText: 'No Autorizado'},
                {field:'estado', headerText: 'Estado'}
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

