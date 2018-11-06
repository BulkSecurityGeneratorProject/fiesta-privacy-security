(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserDialogController', FiestaUserDialogController);

    FiestaUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FiestaUser'];

    function FiestaUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FiestaUser) {
        var vm = this;

        vm.fiestaUser = entity;
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
            if (vm.fiestaUser.id !== null) {
                FiestaUser.update(vm.fiestaUser, onSaveSuccess, onSaveError);
            } else {
                FiestaUser.save(vm.fiestaUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:fiestaUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
