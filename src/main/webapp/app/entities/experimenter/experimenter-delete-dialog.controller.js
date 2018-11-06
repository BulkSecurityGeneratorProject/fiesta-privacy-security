(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('ExperimenterDeleteController',ExperimenterDeleteController);

    ExperimenterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Experimenter'];

    function ExperimenterDeleteController($uibModalInstance, entity, Experimenter) {
        var vm = this;

        vm.experimenter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Experimenter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
