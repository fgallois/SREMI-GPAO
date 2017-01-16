(function () {
    var app = angular.module('bl-controller', []);

    app.controller('BLController', ['$http', function ($http) {
        var bl = this;
        bl.orders = [];
        bl.orderListLabel = 'Référence Commande';
        bl.receiptNumber = 0;

        $http.get('./receiptNumber.json')
            .then(function successCallback(response) {
                console.log("Receipt # = " + response.data);
                bl.receiptNumber = response.data;
            });

        this.orderSelected = function (order) {
            bl.orderListLabel = order.orderReference;
            $http.get('./order.json/' + order.orderReference)
                .then(function successCallback(response) {
                    console.log("data = " + response.data);
                    $('#tableCde').bootstrapTable($('#tableCde').data('method'), response.data);
                });
        };

        this.refreshOrderList = function () {
            $http.get('./orders.json')
                .then(function successCallback(response) {
                    bl.orders = response.data;
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
        };
        this.refreshOrderList();

        this.printBL = function () {
            var lineSelection = $('#tableCde').bootstrapTable('getSelections');
            if (typeof lineSelection != "undefined" && lineSelection != null && lineSelection.length > 0) {
                $("#alert1").toggle(false);
                var dataBL = JSON.stringify({orderRef: bl.orderListLabel, lines: lineSelection});
                $http.post('./receipt', dataBL)
                    .then(function successCallback(response) {
                        bl.receiptNumber = parseInt(response.headers('receiptNumber'));
                        window.open(response.headers('Location'), "_blank");
                    }, function errorCallback(response) {
                        console.log('Error = ' + response.data);
                    });
            } else {
                $("#alert1").toggle(true);
            }
        };

        this.updateReceiptNumber = function () {
            var newNumber = JSON.stringify({documentNumber: bl.receiptNumber});
            console.log("receiptNumber " + newNumber);
            $http.post('./receiptNumber', newNumber)
                .then(function successCallback(response) {
                    console.log("SUCCESS");
                }, function errorCallback(response) {
                    console.log("ERROR");
                });
        };
    }]);
})();
