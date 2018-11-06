(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('EndpointUser', EndpointUser);

    EndpointUser.$inject = ['$resource'];

    function EndpointUser ($resource) {
        var resourceUrl =  'api/endpoint-users/:id';

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
