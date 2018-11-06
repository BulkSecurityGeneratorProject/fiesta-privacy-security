(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('SensorExplorer', SensorExplorer);

    SensorExplorer.$inject = ['$resource', 'DateUtils'];

    function SensorExplorer ($resource, DateUtils) {
        var resourceUrl =  'api/sensor-explorers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
