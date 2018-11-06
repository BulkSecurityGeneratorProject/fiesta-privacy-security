(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('FiestaUserDeleteController',FiestaUserDeleteController);

    FiestaUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'FiestaUser'];

    function FiestaUserDeleteController($uibModalInstance, entity, FiestaUser) {
        var vm = this;

        vm.fiestaUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FiestaUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
