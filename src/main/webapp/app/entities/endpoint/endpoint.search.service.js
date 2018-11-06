(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .factory('EndpointSearch', EndpointSearch);

    EndpointSearch.$inject = ['$resource'];

    function EndpointSearch($resource) {
        var resourceUrl =  'api/_search/endpoints/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
