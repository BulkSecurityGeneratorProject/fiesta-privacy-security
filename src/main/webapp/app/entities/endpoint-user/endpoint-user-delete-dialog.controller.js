(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointUserDeleteController',EndpointUserDeleteController);

    EndpointUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'EndpointUser'];

    function EndpointUserDeleteController($uibModalInstance, entity, EndpointUser) {
        var vm = this;

        vm.endpointUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EndpointUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
