/**
 * Created by fgallois on 8/18/15.
 */
(function () {
    var app = angular.module('admin-controller', []);

    app.controller('AdministrationController', ['$http', function ($http) {
        var gpaoConfig = this;
        gpaoConfig.config = {};
        $http.get('./configuration.json')
            .then(function successCallback(response) {
                console.log("data = " + response.data.receiptNumber);
                gpaoConfig.config = response.data;
            });

        // this.updateCertificateNumber = function () {
        //     var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.certificateNumber});
        //     $http.post('./certificateNumber', newNumber)
        //         .then(function successCallback(response) {
        //             console.log("SUCCESS");
        //         }, function errorCallback(response) {
        //             console.log("ERROR");
        //         });
        // };

        // this.updateWithVat = function () {
        //     var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.withVat});
        //     $http.post('./withVat', newNumber)
        //         .then(function successCallback(response) {
        //             console.log("SUCCESS");
        //         }, function errorCallback(response) {
        //             console.log("ERROR");
        //         });
        // };

        this.updateVatRate = function () {
            var newNumber = JSON.stringify({documentNumber: gpaoConfig.config.vatRate});
            $http.post('./vatRate', newNumber)
                .then(function successCallback(response) {
                    console.log("SUCCESS");
                }, function errorCallback(response) {
                    console.log("ERROR");
                });
        };

    }]);

})();
