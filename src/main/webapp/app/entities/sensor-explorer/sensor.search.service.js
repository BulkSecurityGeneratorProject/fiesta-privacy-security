(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .factory('SensorSearch', SensorSearch);

    SensorSearch.$inject = ['$resource'];

    function SensorSearch($resource) {
        var resourceUrl =  'api/_search/sensor-explorers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
