(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserSyncDeleteController',FiestaUserSyncDeleteController);

    FiestaUserSyncDeleteController.$inject = ['$uibModalInstance', 'entity', 'FiestaUserSync'];

    function FiestaUserSyncDeleteController($uibModalInstance, entity, FiestaUserSync) {
        var vm = this;

        vm.fiestaUserSync = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FiestaUserSync.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
