(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointUserDetailController', EndpointUserDetailController);

    EndpointUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EndpointUser'];

    function EndpointUserDetailController($scope, $rootScope, $stateParams, previousState, entity, EndpointUser) {
        var vm = this;

        vm.endpointUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:endpointUserUpdate', function(event, result) {
            vm.endpointUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
