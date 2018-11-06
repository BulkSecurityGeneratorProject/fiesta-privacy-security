(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointsConfigDeleteController',EndpointsConfigDeleteController);

    EndpointsConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'EndpointsConfig'];

    function EndpointsConfigDeleteController($uibModalInstance, entity, EndpointsConfig) {
        var vm = this;

        vm.endpointsConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EndpointsConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
