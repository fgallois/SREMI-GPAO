/**
 * Created by fgallois on 8/18/15.
 */
(function () {
    var app = angular.module('admin-controller', []);

    app.controller('AdministrationController', ['$http', function ($http) {
        var gpaoConfig = this;
        gpaoConfig.config = {};
        $http.get('./configuration.json').success(function (data) {
            console.log("data = " + data.invoiceNumber);
            gpaoConfig.config = data;
        });
    }]);

})();
