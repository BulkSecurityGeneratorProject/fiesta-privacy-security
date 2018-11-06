(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('FiestaUserSync', FiestaUserSync);

    FiestaUserSync.$inject = ['$resource'];

    function FiestaUserSync ($resource) {
        var resourceUrl =  'api/fiesta-user-syncs/:id';

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
