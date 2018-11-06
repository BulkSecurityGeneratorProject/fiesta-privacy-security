(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserDetailController', FiestaUserDetailController);

    FiestaUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FiestaUser'];

    function FiestaUserDetailController($scope, $rootScope, $stateParams, previousState, entity, FiestaUser) {
        var vm = this;

        vm.fiestaUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:fiestaUserUpdate', function(event, result) {
            vm.fiestaUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
