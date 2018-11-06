(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .factory('AccessLogSearch', AccessLogSearch);

    AccessLogSearch.$inject = ['$resource'];

    function AccessLogSearch($resource) {
        var resourceUrl =  'api/_search/access-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
