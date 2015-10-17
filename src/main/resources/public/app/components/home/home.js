/**
 * Created by fgallois on 8/18/15.
 */
(function () {
    var app = angular.module('home-directives', []);

    app.directive("homeBl", function () {
        return {
            restrict: 'E',
            templateUrl: "app/components/bl/home-bl.html"
        };
    });

    app.directive("homeCatalog", function () {
        return {
            restrict: 'E',
            templateUrl: "app/components/catalog/home-catalog.html"
        };
    });

    app.directive("homeAdmin", function () {
        return {
            restrict: 'E',
            templateUrl: "app/components/admin/home-admin.html"
        };
    });

    app.directive("homeTabs", function () {
        return {
            restrict: "E",
            templateUrl: "app/components/home/home-tabs.html",
            controller: function () {
                this.tab = 1;

                this.isSet = function (checkTab) {
                    return this.tab === checkTab;
                };

                this.setTab = function (activeTab) {
                    this.tab = activeTab;
                };
            },
            controllerAs: "tab"
        };
    });

    app.controller('AdministrationController', ['$http', function ($http) {
        var gpaoConfig = this;
        gpaoConfig.config = {};
        $http.get('./configuration.json').success(function (data) {
            console.log("data = " + data.invoiceNumber);
            gpaoConfig.config = data;
        });
    }]);

    app.controller('BLController', ['$http', function ($http) {
        var bl = this;
        bl.orders = {};
        bl.orderListLabel = 'Référence Commande';
        bl.invoiceNumber = 0;

        $http.get('./orders.json').success(function (data) {
            bl.orders = data;
        });

        $http.get('./invoiceNumber.json').success(function (data) {
            console.log("Invoice # = " + data);
            bl.invoiceNumber = data;
        });

        this.orderSelected = function (order) {
            bl.orderListLabel = order.orderReference;
            $http.get('./order.json/' + order.orderReference)
                .success(function (data) {
                    console.log("data = " + data);
                    $('#tableCde').bootstrapTable($('#tableCde').data('method'), data);
                });
        };

        this.printBL = function () {
            var lineSelection = $('#tableCde').bootstrapTable('getSelections');
            if(typeof lineSelection != "undefined" && lineSelection != null && lineSelection.length > 0) {
                $( "#alert1" ).toggle(false);
                var dataBL = JSON.stringify({orderRef: bl.orderListLabel, lines: lineSelection});
                $http.post('./bonLivraison', dataBL)
                    .success(function (data, status, headers, config) {
                        bl.invoiceNumber = parseInt(headers('receiptNumber'));
                        window.open(headers('Location'), "_blank");
                    })
                    .error(function (data) {
                        console.log('Error = ' + data);
                    });
            } else {
                $( "#alert1" ).toggle(true);
            }
        };

        this.updateInvoiceNumber = function () {
            var newNumber = JSON.stringify({invoiceNumber: bl.invoiceNumber});
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
