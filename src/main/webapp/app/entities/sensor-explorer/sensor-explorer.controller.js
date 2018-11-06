(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('SensorExplorerController', SensorExplorerController);

    SensorExplorerController.$inject = ['$rootScope','$scope', '$state', 'SensorExplorer', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','QuantityKinds', 'Sensor', 'SensorSearch'];

    function SensorExplorerController($rootScope, $scope,$state, SensorExplorer, ParseLinks, AlertService, paginationConstants, pagingParams,QuantityKinds, Sensor, SensorSearch) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.selectedQuantityKind = '';



         vm.searchQuery = pagingParams.search;
         vm.currentSearch = pagingParams.search;



        loadAll('all');


         vm.quantityKinds = QuantityKinds.query(function(response) {
                               vm.sensors = [];
                                     var quantityKind = vm.selectedQuantityKind;
                                    var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
                                    vm.selectedQuantityKind = shortQuantityKind;
                                     $rootScope.quantityKinds = vm.quantityKinds;

                });


        function loadAll () {
        if (pagingParams.search) {
            SensorSearch.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort(),
                query: pagingParams.search
            }, onSuccess, onError);
        } else {

            SensorExplorer.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
        }
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
                vm.sensorExplorers = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }


        }

         $scope.selectQuantitykind = function() {
            console.log(vm.searchQuery);
             search(vm.searchQuery);
         }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function search(searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
           // vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
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
