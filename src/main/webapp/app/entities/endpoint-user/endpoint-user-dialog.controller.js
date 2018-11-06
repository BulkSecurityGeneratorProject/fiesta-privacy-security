(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointUserDialogController', EndpointUserDialogController);

    EndpointUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EndpointUser'];

    function EndpointUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EndpointUser) {
        var vm = this;

        vm.endpointUser = entity;
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
            if (vm.endpointUser.id !== null) {
                EndpointUser.update(vm.endpointUser, onSaveSuccess, onSaveError);
            } else {
                EndpointUser.save(vm.endpointUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:endpointUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
