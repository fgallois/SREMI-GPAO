(function () {
    var app = angular.module('invoice-controller', ['ui.grid', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav']);

    app.controller('InvoiceController', ['$scope', '$http', '$q', function ($scope, $http, $q) {
        var invoice = this;
        invoice.orders = {};
        invoice.orderListLabel = 'Référence Commande';
        invoice.invoiceNumber = 0;

        $scope.gridOptions = {
            enableSorting: false,
            columnDefs: [
                {
                    field: 'id',
                    visible:false
                },
                {
                    field: 'line',
                    displayName: 'Ligne',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus : false,
                    enableColumnMenu: false
                },
                {
                    name: 'reference',
                    displayName: 'Référence',
                    enableCellEdit: false,
                    allowCellFocus : false,
                    enableColumnMenu: false
                },
                {
                    name: 'description',
                    displayName: 'Description',
                    enableCellEdit: false,
                    allowCellFocus : false,
                    enableColumnMenu: false
                },
                {
                    name: 'quantity',
                    displayName: 'Quantité',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus : false,
                    enableColumnMenu: false
                },
                {
                    name: 'unitPriceHT',
                    displayName: 'Prix Unitaire HT',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    type: 'number',
                    enableColumnMenu: false
                },
                {
                    name: 'calculateTotalHT',
                    field: 'calculateTotalHT()',
                    displayName: 'Prix Total HT',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus : false,
                    enableColumnMenu: false
                }
            ],
            onRegisterApi: function(gridApi){
                $scope.gridApi = gridApi;
                gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
            }
        };

        $scope.saveRow = function( rowEntity ) {
            var deferred = $q.defer();
            $http.put('./updateOrderLineItem.json', rowEntity)
                .then(function(data) {
                    deferred.resolve();
                }, function(error){
                    deferred.reject();
                });
            $scope.gridApi.rowEdit.setSavePromise(rowEntity, deferred.promise);
        };

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
                    angular.forEach(data, function(row){
                        row.calculateTotalHT = function() {
                            var total = 20;
                            if (this.unitPriceHT >= 0) {
                                total = this.quantity * this.unitPriceHT;
                            }
                            return total;
                        }
                    });
                    $scope.gridOptions.data = data;
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
