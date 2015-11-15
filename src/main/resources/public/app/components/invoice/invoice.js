(function () {
    var app = angular.module('invoice-controller', []);

    app.controller('InvoiceController', ['$http', function ($http) {
        var invoice = this;
        invoice.orders = {};
        invoice.orderListLabel = 'Référence Commande';
        invoice.invoiceNumber = 0;

        $http.get('./invoiceNumber.json').success(function (data) {
            console.log("Facture # = " + data);
            invoice.invoiceNumber = data;
        });

        this.refreshOrderList = function () {
            $http.get('./openOrders.json').success(function (data) {
                invoice.orders = data;
            });
        };
        this.refreshOrderList();

        this.orderSelected = function (order) {
            invoice.orderListLabel = order.orderReference;
            $http.get('./openOrder.json/' + order.orderReference)
                .success(function (data) {
                    console.log("data = " + data);
                    $('#tableCde').bootstrapTable($('#tableCde').data('method'), data);
                });
        };

//        this.printBL = function () {
//            var lineSelection = $('#tableCde').bootstrapTable('getSelections');
//            if(typeof lineSelection != "undefined" && lineSelection != null && lineSelection.length > 0) {
//                $( "#alert1" ).toggle(false);
//                var dataBL = JSON.stringify({orderRef: bl.orderListLabel, lines: lineSelection});
//                $http.post('./bonLivraison', dataBL)
//                    .success(function (data, status, headers, config) {
//                        bl.invoiceNumber = parseInt(headers('receiptNumber'));
//                        window.open(headers('Location'), "_blank");
//                    })
//                    .error(function (data) {
//                        console.log('Error = ' + data);
//                    });
//            } else {
//                $( "#alert1" ).toggle(true);
//            }
//        };

        this.updateInvoiceNumber = function () {
            var newNumber = JSON.stringify({documentNumber: invoice.invoiceNumber});
            console.log("invoiceNumber " + newNumber);
            $http.post('./invoiceNumber', newNumber)
                .success(function (data) {
                    console.log("SUCCESS");
                })
                .error(function (data) {
                    console.log("ERROR");
                });
        };
    }]);
})();
