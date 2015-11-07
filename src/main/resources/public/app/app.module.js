(function() {
    var sremiApp = angular.module('sremiGpao', ['ngRoute','home-directives']);

    sremiApp.config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
                when('/bonsLivraison', {
                    templateUrl: 'app/components/bl/bl.html',
                    controller: 'BLController'
                }).
                when('/facturation', {
                    templateUrl: 'app/components/facturation/facturation.html'
                }).
                when('/catalogue', {
                    templateUrl: 'app/components/catalog/catalog.html'
                }).
                when('/administration', {
                    templateUrl: 'app/components/admin/admin.html',
                    controller: 'AdministrationController'
                }).
                otherwise({
                    redirectTo: '/bonsLivraison'
                });
        }]);
})();
