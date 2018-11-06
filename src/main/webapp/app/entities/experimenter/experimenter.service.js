(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('Experimenter', Experimenter);

    Experimenter.$inject = ['$resource'];

    function Experimenter ($resource) {
        var resourceUrl =  'api/experimenters/:id';

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
