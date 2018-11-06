(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('AccessLogController', AccessLogController);

    AccessLogController.$inject = ['$state','$scope', 'AccessLog', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','TestbebService','AccessLogSearch'];

    function AccessLogController($state, $scope,AccessLog, ParseLinks, AlertService, paginationConstants, pagingParams,TestbebService, AccessLogSearch) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

//         vm.searchQuery = pagingParams.search;
//         vm.currentSearch = pagingParams.search;

         vm.searchQuery = null;
         vm.currentSearch = null;

        //console.log('AccessLogController');

        loadAll();


        getTestbed();
                function getTestbed() {
                  vm.testbedInformation = TestbebService.query();
        }

        function loadAll () {

//            if (pagingParams.search) {
//                AccessLogSearch.query({
//                    page: pagingParams.page - 1,
//                    size: vm.itemsPerPage,
//                    sort: sort(),
//                    query: pagingParams.search
//                }, onSuccess, onError);
//            } else {

                AccessLog.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
           // }
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


           // }
            /*
            AccessLog.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                originalId:'all',
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

            */


        }

         $scope.search = function(searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear() {
                    vm.links = null;
                    vm.page = 1;
                    vm.predicate = 'id';
                    vm.reverse = true;
                    vm.currentSearch = null;
                    pagingParams.search = null;
                    vm.transition();
                }


        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        $scope.gotoAccessLogs = function() {
          clear();
          $state.go('access-log');
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
