(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('JbtConfigurationController', JbtConfigurationController);

    JbtConfigurationController.$inject = ['$filter','JbtConfigurationService'];

    function JbtConfigurationController (filter,JbtConfigurationService) {
        var vm = this;

        vm.allConfiguration = null;
        vm.configuration = null;

        JbtConfigurationService.get().then(function(configuration) {
            vm.configuration = configuration;
        });
        JbtConfigurationService.getEnv().then(function (configuration) {
            vm.allConfiguration = configuration;
        });
    }
})();
