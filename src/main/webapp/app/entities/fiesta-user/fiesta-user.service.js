(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('FiestaUser', FiestaUser);

    FiestaUser.$inject = ['$resource'];

    function FiestaUser ($resource) {
        var resourceUrl =  'api/fiesta-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
