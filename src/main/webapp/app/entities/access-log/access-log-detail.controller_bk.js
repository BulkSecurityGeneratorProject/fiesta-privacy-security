(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('AccessLogDetailController', AccessLogDetailController);

    AccessLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AccessLog'];

    function AccessLogDetailController($scope, $rootScope, $stateParams, previousState, entity, AccessLog) {
        var vm = this;

        vm.accessLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:accessLogUpdate', function(event, result) {
            vm.accessLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
