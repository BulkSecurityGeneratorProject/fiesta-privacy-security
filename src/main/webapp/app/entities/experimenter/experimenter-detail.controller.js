(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('ExperimenterDetailController', ExperimenterDetailController);

    ExperimenterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Experimenter'];

    function ExperimenterDetailController($scope, $rootScope, $stateParams, previousState, entity, Experimenter) {
        var vm = this;

        vm.experimenter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:experimenterUpdate', function(event, result) {
            vm.experimenter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
