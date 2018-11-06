(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointSyncDialogController', EndpointSyncDialogController);

    EndpointSyncDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EndpointSync'];

    function EndpointSyncDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EndpointSync) {
        var vm = this;

        vm.endpointSync = entity;
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
            if (vm.endpointSync.id !== null) {
                EndpointSync.update(vm.endpointSync, onSaveSuccess, onSaveError);
            } else {
                EndpointSync.save(vm.endpointSync, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:endpointSyncUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
