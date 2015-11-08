(function () {
    var app = angular.module('bl-controller', []);

    app.controller('BLController', ['$http', function ($http) {
        var bl = this;
        bl.orders = {};
        bl.orderListLabel = 'Référence Commande';
        bl.receiptNumber = 0;

        $http.get('./receiptNumber.json').success(function (data) {
            console.log("Receipt # = " + data);
            bl.receiptNumber = data;
        });

        this.orderSelected = function (order) {
            bl.orderListLabel = order.orderReference;
            $http.get('./order.json/' + order.orderReference)
                .success(function (data) {
                    console.log("data = " + data);
                    $('#tableCde').bootstrapTable($('#tableCde').data('method'), data);
                });
        };

        this.refreshOrderList = function () {
            $http.get('./orders.json').success(function (data) {
                bl.orders = data;
            });
        };
        this.refreshOrderList();

        this.printBL = function () {
            var lineSelection = $('#tableCde').bootstrapTable('getSelections');
            if(typeof lineSelection != "undefined" && lineSelection != null && lineSelection.length > 0) {
                $( "#alert1" ).toggle(false);
                var dataBL = JSON.stringify({orderRef: bl.orderListLabel, lines: lineSelection});
                $http.post('./receipt', dataBL)
                    .success(function (data, status, headers, config) {
                        bl.receiptNumber = parseInt(headers('receiptNumber'));
                        window.open(headers('Location'), "_blank");
                    })
                    .error(function (data) {
                        console.log('Error = ' + data);
                    });
            } else {
                $( "#alert1" ).toggle(true);
            }
        };

        this.updateReceiptNumber = function () {
            var newNumber = JSON.stringify({documentNumber: bl.receiptNumber});
            console.log("receiptNumber " + newNumber);
            $http.post('./receiptNumber', newNumber)
                .success(function (data) {
                    console.log("SUCCESS");
                })
                .error(function (data) {
                    console.log("ERROR");
                });
        };
    }]);
})();
