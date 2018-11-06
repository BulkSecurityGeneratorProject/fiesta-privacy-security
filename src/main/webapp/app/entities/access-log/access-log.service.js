(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('AccessLog', AccessLog);

    AccessLog.$inject = ['$resource', 'DateUtils'];

    function AccessLog ($resource, DateUtils) {
        var resourceUrl =  'api/access-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.accessTime = DateUtils.convertDateTimeFromServer(data.accessTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
