(function () {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
