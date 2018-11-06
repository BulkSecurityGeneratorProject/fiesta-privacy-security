(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('TestbebService', TestbebService);

    TestbebService.$inject = ['$resource', 'DateUtils'];

    function TestbebService ($resource, DateUtils) {
        var resourceUrl =  'api/testbed/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET'},
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
