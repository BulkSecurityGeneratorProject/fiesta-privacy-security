(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('SensorExplorerDeleteController',SensorExplorerDeleteController);

    SensorExplorerDeleteController.$inject = ['$uibModalInstance', 'entity', 'SensorExplorer'];

    function SensorExplorerDeleteController($uibModalInstance, entity, SensorExplorer) {
        var vm = this;

        vm.sensorExplorer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SensorExplorer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
