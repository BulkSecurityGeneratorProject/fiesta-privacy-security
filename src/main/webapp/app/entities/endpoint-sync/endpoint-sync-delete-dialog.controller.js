(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointSyncDeleteController',EndpointSyncDeleteController);

    EndpointSyncDeleteController.$inject = ['$uibModalInstance', 'entity', 'EndpointSync'];

    function EndpointSyncDeleteController($uibModalInstance, entity, EndpointSync) {
        var vm = this;

        vm.endpointSync = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EndpointSync.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
