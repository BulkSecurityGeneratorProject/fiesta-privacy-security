(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(localStorageConfig);

    localStorageConfig.$inject = ['$localStorageProvider', '$sessionStorageProvider'];

    function localStorageConfig($localStorageProvider, $sessionStorageProvider) {
        $localStorageProvider.setKeyPrefix('jbt-');
        $sessionStorageProvider.setKeyPrefix('jbt-');
    }
})();
