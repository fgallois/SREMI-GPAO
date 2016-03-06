(function () {
    var app = angular.module('invoice-controller', ['ui.grid', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav']);

    app.controller('InvoiceController', ['$scope', 'uiGridConstants', '$http', '$q', function ($scope, uiGridConstants, $http, $q) {
        var invoice = this;
        invoice.orders = {};
        invoice.orderListLabel = 'Référence Commande';
        invoice.invoiceNumber = 0;

        $scope.gridBlInvoice = {
            enableSorting: false,
            columnDefs: [
                {
                    name: 'id',
                    visible: false
                },
                {
                    name: 'number',
                    displayName: 'Bon de Livraison',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'creationDate',
                    displayName: 'Date',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false,
                    cellTemplate: '<div>{{COL_FIELD | date : "dd/MM/yyyy"}}</div>'
                },
                {
                    name: 'Action',
                    cellTemplate: '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteRowBL(row)">' +
                        'Supprimer' +
                        '</button>',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                }
            ]
        };

        $scope.gridInvoice = {
            enableSorting: false,
            showColumnFooter: true,
            columnDefs: [
                {
                    field: 'id',
                    visible: false
                },
                {
                    field: 'line',
                    displayName: 'Ligne',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'reference',
                    displayName: 'Référence',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'description',
                    displayName: 'Description',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'quantity',
                    displayName: 'Quantité',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'unitPriceHT',
                    displayName: 'Prix Unitaire HT',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    type: 'number',
                    enableColumnMenu: false,
                    cellTemplate: '<div>{{COL_FIELD | currency:"" : 2}}</div>'
                },
                {
                    name: 'calculateTotalHT',
                    field: 'calculateTotalHT()',
                    displayName: 'Prix Total HT',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false,
                    cellTemplate: '<div>{{COL_FIELD | currency:"" : 2}} €</div>',
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    footerCellTemplate: '<div>Total HT: {{col.getAggregationValue() | currency:"" : 2}} €</div>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
            }
        };

        $scope.saveRow = function (rowEntity) {
            var deferred = $q.defer();
            $http.put('./updateOrderLineItem.json', rowEntity)
                .then(function (data) {
                    deferred.resolve();
                }, function (error) {
                    deferred.reject();
                });
            $scope.gridApi.rowEdit.setSavePromise(rowEntity, deferred.promise);
        };

        $scope.deleteRowBL = function (row) {
            BootstrapDialog.confirm('Etes-vous sur de vouloir supprimer le bon de livraison \''
                + row.entity.number + '\'?', function (result) {
                if (result) {
                    $http.delete('./receipt/' + invoice.orderListLabel + '/' + row.entity.id)
                        .success(function (data) {
                            console.log("data = " + data.allOrderDetails);
                            angular.forEach(data.allOrderDetails, function (row) {
                                row.calculateTotalHT = function () {
                                    var total = 20;
                                    if (this.unitPriceHT >= 0) {
                                        total = this.quantity * this.unitPriceHT;
                                    }
                                    return total;
                                }
                            });
                            $scope.gridBlInvoice.data = data.receipts;
                            $scope.gridInvoice.data = data.allOrderDetails;
                        });
                }
            });
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
                    console.log("data = " + data.allOrderDetails);
                    angular.forEach(data.allOrderDetails, function (row) {
                        row.calculateTotalHT = function () {
                            var total = 20;
                            if (this.unitPriceHT >= 0) {
                                total = this.quantity * this.unitPriceHT;
                            }
                            return total;
                        }
                    });
                    $scope.gridBlInvoice.data = data.receipts;
                    $scope.gridInvoice.data = data.allOrderDetails;
                });
        };

        this.printInvoice = function () {
            var dataFacture = JSON.stringify({reference: invoice.orderListLabel});
            $http.post('./invoice', dataFacture)
                .success(function (data, status, headers, config) {
                    invoice.invoiceNumber = parseInt(headers('invoiceNumber'));
                    window.open(headers('Location'), "_blank");
                })
                .error(function (data) {
                    console.log('Error = ' + data);
                });
        };

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
