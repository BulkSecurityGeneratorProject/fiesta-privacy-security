(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('Endpoint', Endpoint);

    Endpoint.$inject = ['$resource', 'DateUtils'];

    function Endpoint ($resource, DateUtils) {
        var resourceUrl =  'api/endpoints/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                        data.updated = DateUtils.convertDateTimeFromServer(data.updated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
