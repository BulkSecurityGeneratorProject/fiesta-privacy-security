(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointDeleteController',EndpointDeleteController);

    EndpointDeleteController.$inject = ['$uibModalInstance', 'entity', 'Endpoint'];

    function EndpointDeleteController($uibModalInstance, entity, Endpoint) {
        var vm = this;

        vm.endpoint = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Endpoint.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
