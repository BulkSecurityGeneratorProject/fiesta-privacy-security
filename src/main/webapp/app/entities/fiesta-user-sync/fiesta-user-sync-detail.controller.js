(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserSyncDetailController', FiestaUserSyncDetailController);

    FiestaUserSyncDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FiestaUserSync'];

    function FiestaUserSyncDetailController($scope, $rootScope, $stateParams, previousState, entity, FiestaUserSync) {
        var vm = this;

        vm.fiestaUserSync = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:fiestaUserSyncUpdate', function(event, result) {
            vm.fiestaUserSync = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
