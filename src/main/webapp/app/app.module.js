(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jbooter-needle-angularjs-add-module JBooter will add new module here
            'angular-loading-bar',
            "checklist-model"
        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
