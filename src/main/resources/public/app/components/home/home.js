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

        $http.get('./orders.json').success(function (data) {
            bl.orders = data;
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
            var dataBL = JSON.stringify({orderRef: bl.orderListLabel, lines:lineSelection});
//            alert('dataBL = ' + dataBL);
            $http.post('./bonLivraison', dataBL)
                .success(function (data, status, headers, config) {
                    console.log("success = " + data);
                    console.log("status = " + status);
                    console.log("headers = " + headers('Location'));

                    window.open(headers('Location'), "_blank");
//                    var uriString = parseReturn(returnData);
//                    console.location("uriString = " + uriString);
//                    location.href = uriString;
//                    window.open("data:application/pdf, " + data, "_blank");
                    //                    window.open(data);
                })
                .error(function (data) {
                    console.log('Error = ' + data);
                });
        };

    }]);
})();
