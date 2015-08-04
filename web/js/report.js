$(document).ready(function()
{
    $("#dateFrom").datetimepicker(
        {
            format:'Y/m/d H:i',
            lang: "es",
            onShow:function(ct)
            {
                var date =  new Date($('#dateTo').val());
                var df = date.dateFormat("Y/m/d");

                this.setOptions(
                {
                    maxDate: date != null ? df : new Date()
                })
            }
        }
    );
    $("#dateTo").datetimepicker(
        {
            format:'Y/m/d H:i',
            lang: "es",
            onShow:function(ct)
            {
                var date =  new Date($('#dateFrom').val());
                var df = date.dateFormat("Y/m/d");

                this.setOptions(
                {
                    maxDate: new Date(),
                    minDate: $('#dateFrom').val() ? df : false
                })
            }
        }
    );
    $('#buscar').puibutton(
        {
            icon: 'fa-search'
        }
    );
    $('#toExcel').puibutton(
        {
            icon: 'fa-file-excel-o'
        }
    );

    $('#buscar').on("click", function()
    {
        var from = $("#dateFrom").val();
        var to = $("#dateTo").val();

        if (from.length == 0 || to.length == 0)
        {
            alert("Selecciona las fechas a buscar");
        }
        else
        {
            $('#t1').remove();
            var t = "<div id='t1'></div>";
            $("#nextReport").after(t);
            $('#t1').puidatatable(
                {
                    responsive : true,
                    columns:
                        [
                            //{field:'linea', headerText: 'Linea'},
                            //{field:'estacion', headerText: 'Estacion'},
                            //{field:'acceso', headerText: 'Acceso'},
                            {field:'torniq', headerText: 'Torniquete', sortable:true},
                            {field:'boleto', headerText: 'Boletos', sortable:true},
                            {field:'tarjeta', headerText: 'Tarjeta', sortable:true},
                            {field:'total', headerText: 'Total', sortable:true},
                            //{field:'noaut', headerText: 'No Autorizado'},
                            {field:'estado', headerText: 'Estado', content: getState},
                            {field:'fecha', headerText: 'Fecha', sortable:true, content: format}
                        ],
                    datasource: function(callback)
                    {
                        $.ajax(
                        {
                            type: 'POST',
                            url: 'reporte',
                            data: "from=" + from + "&to=" + to,
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
                    paginator:
                    {
                        rows: 20
                    },
                    selectionMode: 'single',
                    rowSelect: function(event, data)
                    {
                        $('#messages').remove();
                        var message = "<div id='messages' title='Detalle' style='position: fixed; display: inline-block'>" +
                            "Torniquete: " + data.torniq + "<br>" +
                            "Boletos :" + data.boleto + "<br>" +
                            "Tarjeta: " + data.tarjeta + "<br>" +
                            "Total: " + data.total + "<br>" +
                            "Estado: "  + data.estado +  "<br>" +
                            "Fecha: " + data.fecha +  "<br>" +
                            "</div>";
                        $("#nextReport").after(message);
                        $('#messages').puidialog(
                        {
                            closable: true
                        });
                    },
                    rowUnselect: function(event, data)
                    {
                        $('#messages').remove();
                    }
                });
            $('#toExcel').puibutton('enable');
        }
    });

    $('#toExcel').on('click', function()
    {


        var from = $("#dateFrom").val();
        var to = $("#dateTo").val();

        $.ajax(
            {
                type: 'POST',
                url: 'toExcel',
                data: "from=" + from + "&to=" + to,
                dataType: 'json',
                context: this,
                success: function(data)
                {
                    if (data.response == "Error")
                    {
                        console.log(data.response);
                    }
                    else
                    {
                        console.log(data.response);
                    }
                },
                error: function (textStatus, errorThrown)
                {
                    console.log("Error->" + errorThrown + ' : ' + textStatus);
                }
            });
    });
});


function getState(data)
{
    if (data.estado == "Funcionando")
    {
        return $("<div title='Estado'>" +
                "<img src='img/ok.png' width='50px' />" +
                "</div>");
    }
    else if (data.estado == "No funciona boleto")
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

function format(data)
{
    return data.fecha.replace(/(\r\n|\n|\r)/gm, "<br>");
}
