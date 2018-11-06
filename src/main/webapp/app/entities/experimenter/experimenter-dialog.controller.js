(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('ExperimenterDialogController', ExperimenterDialogController);

    ExperimenterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Experimenter'];

    function ExperimenterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Experimenter) {
        var vm = this;

        vm.experimenter = entity;
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
            if (vm.experimenter.id !== null) {
                Experimenter.update(vm.experimenter, onSaveSuccess, onSaveError);
            } else {
                Experimenter.save(vm.experimenter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:experimenterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
