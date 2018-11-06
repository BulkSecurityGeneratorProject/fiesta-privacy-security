(function() {
    'use strict';
    angular
        .module('fiestaSecurityUiApp')
        .factory('RequestAccess', RequestAccess);

    RequestAccess.$inject = ['$resource', 'DateUtils'];

    function RequestAccess ($resource, DateUtils) {
        var resourceUrl =  'api/request-accesses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.requestDate = DateUtils.convertDateTimeFromServer(data.requestDate);
                        data.approvedDate = DateUtils.convertDateTimeFromServer(data.approvedDate);
                        data.seenByRequesterDate = DateUtils.convertDateTimeFromServer(data.seenByRequesterDate);
                        data.seenByReceiverDate = DateUtils.convertDateTimeFromServer(data.seenByReceiverDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
