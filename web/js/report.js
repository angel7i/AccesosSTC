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
    $('#buscar').puibutton();

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
            $('#t1').puidatatable(
                {
                    responsive : true,
                    paginator:
                    {
                        rows: 20
                    },
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
                            {field:'estado', headerText: 'Estado'},
                            {field:'fecha', headerText: 'Fecha', sortable:true}
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
                    selectionMode: 'single',
                    rowSelect: function(event, data)
                    {
                        $('#messages').remove();
                        var message = "<div id='messages' title='Detalle'>" +
                            "Torniquete: " + data.torniq + "<br>" +
                            "Boletos :" + data.boleto + "<br>" +
                            "Tarjeta: " + data.tarjeta + "<br>" +
                            "Total: " + data.total + "<br>" +
                            "Estado: "  + data.estado +  "<br>" +
                            "Fecha: " + data.fecha +  "<br>" +
                            "</div>";
                        $("#t1").before(message);
                        $('#messages').puidialog( {
                            location: "left top",
                            closable: true,
                            draggable: true,
                            responsive: true
                        });
                        $('#messages').puidialog("show");
                    },
                    rowUnselect: function(event, data)
                    {
                        $('#messages').remove();
                    }
                });
        }
    });
});

