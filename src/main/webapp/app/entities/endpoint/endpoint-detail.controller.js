(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointDetailController', EndpointDetailController);

    EndpointDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Endpoint', 'FiestaUser'];

    function EndpointDetailController($scope, $rootScope, $stateParams, previousState, entity, Endpoint, FiestaUser) {
        var vm = this;

        vm.endpoint = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:endpointUpdate', function(event, result) {
            vm.endpoint = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
