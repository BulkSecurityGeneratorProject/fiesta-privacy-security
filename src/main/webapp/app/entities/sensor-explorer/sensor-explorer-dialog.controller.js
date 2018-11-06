(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('SensorExplorerDialogController', SensorExplorerDialogController);

    SensorExplorerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SensorExplorer'];

    function SensorExplorerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SensorExplorer) {
        var vm = this;

        vm.sensorExplorer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sensorExplorer.id !== null) {
                SensorExplorer.update(vm.sensorExplorer, onSaveSuccess, onSaveError);
            } else {
                SensorExplorer.save(vm.sensorExplorer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:sensorExplorerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
