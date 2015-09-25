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
    $('#toExcel').puibutton('disable');

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
                            {field:'salida', headerText: 'Salidas', sortable:true},
                            {field:'fecha', headerText: 'Fecha', sortable:true, content: format}
                        ],
                    datasource: function(callback)
                    {
                        $.ajax(
                            {
                                type: 'POST',
                                url: 'reporteSalida',
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
                                        var range = String(from).split(" ")[0].replace("/", "").replace("/", "") + "-" + String(to).split(" ")[0].replace("/", "").replace("/", "");
                                        $("#toExcel").attr('href','http://localhost:8080/TorniquetesSalida' + range + '.xls');
                                        $("#toExcel").puibutton("enable");
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
                            data.fase + "<br>" +
                            "Salidas :" + data.salida + "<br>" +
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
        }
    });
});


function format(data)
{
    return data.fecha.replace(/(\r\n|\n|\r)/gm, "<br>");
}
