/**
 * Created by fgallois on 8/18/15.
 */
(function(){
    var app = angular.module('home-directives', []);

    app.directive("homeBl", function() {
        return {
            restrict: 'E',
            templateUrl: "app/components/bl/home-bl.html"
        };
    });

    app.directive("homeAdmin", function() {
        return {
            restrict: 'E',
            templateUrl: "app/components/admin/home-admin.html"
        };
    });

    app.directive("homeTabs", function() {
        return {
            restrict: "E",
            templateUrl: "app/components/home/home-tabs.html",
            controller: function() {
                this.tab = 1;

                this.isSet = function(checkTab) {
                    return this.tab === checkTab;
                };

                this.setTab = function(activeTab) {
                    this.tab = activeTab;
                };
            },
            controllerAs: "tab"
        };
    });

    app.controller('AdministrationController',  ['$http', function($http){
        var gpaoConfig = this;
        gpaoConfig.config = {};
        $http.get('./configuration.json').success(function(data){
            console.log("data = " + data.invoiceNumber);
            gpaoConfig.config = data;
        });
    }]);

    app.controller('BLController',  ['$http', function($http){
        var bl = this;
        bl.commandes = {};
        bl.cdeListLabel = 'Référence Commande';

        $http.get('./orders.json').success(function(data){
            bl.commandes = data;
        });

        this.commandeSelected = function(commande) {
            bl.cdeListLabel = commande.commandeReference;
            $http.get('./order.json/'+commande.commandeReference).success(function(data){
                console.log("data = " + data);
                $('#tableCde').bootstrapTable($('#tableCde').data('method'), data);
            });
        };
    }]);
})();
