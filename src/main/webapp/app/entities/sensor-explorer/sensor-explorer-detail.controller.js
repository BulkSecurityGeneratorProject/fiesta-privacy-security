(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('SensorExplorerDetailController', SensorExplorerDetailController);

    SensorExplorerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SensorExplorer'];

    function SensorExplorerDetailController($scope, $rootScope, $stateParams, previousState, entity, SensorExplorer) {
        var vm = this;

        vm.sensorExplorer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:sensorExplorerUpdate', function(event, result) {
            vm.sensorExplorer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
