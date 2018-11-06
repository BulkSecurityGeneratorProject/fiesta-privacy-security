(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointSyncDetailController', EndpointSyncDetailController);

    EndpointSyncDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EndpointSync'];

    function EndpointSyncDetailController($scope, $rootScope, $stateParams, previousState, entity, EndpointSync) {
        var vm = this;

        vm.endpointSync = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:endpointSyncUpdate', function(event, result) {
            vm.endpointSync = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
