(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('RequestAccessDeleteController',RequestAccessDeleteController);

    RequestAccessDeleteController.$inject = ['$uibModalInstance', 'entity', 'RequestAccess'];

    function RequestAccessDeleteController($uibModalInstance, entity, RequestAccess) {
        var vm = this;

        vm.requestAccess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RequestAccess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
