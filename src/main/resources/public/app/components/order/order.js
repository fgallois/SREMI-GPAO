(function () {
    var app = angular.module('order-controller', ['ui.grid', 'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav']);

    app.controller('OrderController', ['$scope', 'uiGridConstants', '$http', '$q', function ($scope, uiGridConstants, $http, $q) {

        $scope.gridOrder = {
            enableSorting: false,
            columnDefs: [
                {
                    field: 'id',
                    visible: false
                },
                {
                    field: 'orderReference',
                    displayName: 'Référence',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    field: 'buyerName',
                    displayName: 'Acheteur',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    field: 'status',
                    displayName: 'Status',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                },
                {
                    name: 'Action',
                    // cellTemplate: '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteRowBL(row)">' +
                    // 'Supprimer' +
                    // '</button>',
                    headerCellClass: 'ui-grid-cell-center-align',
                    cellClass: 'ui-grid-cell-center-align',
                    enableCellEdit: false,
                    allowCellFocus: false,
                    enableColumnMenu: false
                }
            ]
        };

        this.refreshOrderList = function () {
            $http.get('./openOrders.json')
                .success(function (data) {
                    // console.log("data = " + data.allOrderDetails);
                    // angular.forEach(data.allOrderDetails, function (row) {
                    //     row.calculateTotalHT = function () {
                    //         var total = 20;
                    //         if (this.unitPriceHT >= 0) {
                    //             total = this.quantity * this.unitPriceHT;
                    //         }
                    //         return total;
                    //     }
                    // });
                    $scope.gridOrder.data = data;
                });
        };
        this.refreshOrderList();
        

    }]);
})();