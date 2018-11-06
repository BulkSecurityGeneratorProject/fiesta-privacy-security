(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointsConfigDetailController', EndpointsConfigDetailController);

    EndpointsConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EndpointsConfig', 'Endpoint', 'FiestaUser'];

    function EndpointsConfigDetailController($scope, $rootScope, $stateParams, previousState, entity, EndpointsConfig, Endpoint, FiestaUser) {
        var vm = this;

        vm.endpointsConfig = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:endpointsConfigUpdate', function(event, result) {
            vm.endpointsConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
