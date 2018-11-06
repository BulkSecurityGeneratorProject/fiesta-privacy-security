(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('RequestAccessDetailController', RequestAccessDetailController);

    RequestAccessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'RequestAccess'];

    function RequestAccessDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, RequestAccess) {
        var vm = this;

        vm.requestAccess = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('fiestaSecurityUiApp:requestAccessUpdate', function(event, result) {
            vm.requestAccess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
