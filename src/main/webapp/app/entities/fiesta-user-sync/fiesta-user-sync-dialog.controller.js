(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserSyncDialogController', FiestaUserSyncDialogController);

    FiestaUserSyncDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FiestaUserSync'];

    function FiestaUserSyncDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FiestaUserSync) {
        var vm = this;

        vm.fiestaUserSync = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fiestaUserSync.id !== null) {
                FiestaUserSync.update(vm.fiestaUserSync, onSaveSuccess, onSaveError);
            } else {
                FiestaUserSync.save(vm.fiestaUserSync, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:fiestaUserSyncUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
