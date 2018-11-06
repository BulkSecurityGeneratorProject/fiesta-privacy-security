(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('AccessLogOneController', AccessLogOneController);

    AccessLogOneController.$inject = ['$state', 'AccessLog', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','TestbebService'];

    function AccessLogOneController($state, AccessLog, ParseLinks, AlertService, paginationConstants, pagingParams,TestbebService) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.currentSensor = pagingParams.search;

        loadAll();
        console.log('AccessLogOneController');

        getTestbed();
                function getTestbed() {
                  vm.testbedInformation = TestbebService.query();

        }


        function loadAll () {
            AccessLog.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                originalId: pagingParams.search,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.accessLogs = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
