/**
 * Created by fgallois on 8/18/15.
 */
(function () {
    var app = angular.module('admin-controller', []);

    app.controller('AdministrationController', ['$http', function ($http) {
        var gpaoConfig = this;
        gpaoConfig.config = {};
        $http.get('./configuration.json').success(function (data) {
            console.log("data = " + data.receiptNumber);
            gpaoConfig.config = data;
        });

        this.updateCertificateNumber = function () {
            var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.certificateNumber});
            $http.post('./certificateNumber', newNumber)
                .success(function (data) {
                    console.log("SUCCESS");
                })
                .error(function (data) {
                    console.log("ERROR");
                });
        };


        this.updateWithVat = function () {
            var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.withVat});
            $http.post('./withVat', newNumber)
                .success(function (data) {
                    console.log("SUCCESS");
                })
                .error(function (data) {
                    console.log("ERROR");
                });
        };

        this.updateVatRate = function () {
            var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.vatRate});
            $http.post('./vatRate', newNumber)
                .success(function (data) {
                    console.log("SUCCESS");
                })
                .error(function (data) {
                    console.log("ERROR");
                });
        };

    }]);

})();
