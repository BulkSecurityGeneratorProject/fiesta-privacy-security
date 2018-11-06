(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('AccessLogDeleteController',AccessLogDeleteController);

    AccessLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'AccessLog'];

    function AccessLogDeleteController($uibModalInstance, entity, AccessLog) {
        var vm = this;

        vm.accessLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AccessLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
