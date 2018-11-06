(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointController', EndpointController);

    EndpointController.$inject = ['$state', 'Endpoint', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','EndpointSync','FiestaUserSync','$scope','EndpointSearch','TestbebService','QuantityKinds'];

    function EndpointController($state, Endpoint, ParseLinks, AlertService, paginationConstants, pagingParams,EndpointSync,FiestaUserSync,$scope, EndpointSearch,TestbebService,QuantityKinds) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchQuery = null;
        vm.currentSearch = null;




         getTestbed();
        function getTestbed() {
          vm.testbedInformation = TestbebService.query();

        }

        vm.endpointSync = {
           status: null,
           created: null,
           updated: null,
           id: null
        };

        vm.fiestaUserSync = {
            status: null,
           id: null
         };

        loadAll();
        function loadAll () {
            Endpoint.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                share:'all',
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
                vm.endpoints = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


         $scope.search = function(searchQuery) {

                vm.totalItems = 0;
                vm.queryCount = 9;
                vm.endpoints = null;
                vm.page = null;
                vm.currentSearch = searchQuery;


            EndpointSearch.query({
                                query: searchQuery
                            }, onSearchSuccess, onSearchError);
        }

         $scope.clear = function() {
            vm.currentSearch = null;
            vm.searchQuery = null;

            loadAll();

        }
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        $scope.syncEndpoints = function() {
            console.log('syncEndpoints');
           EndpointSync.save(vm.endpointSync, onSaveSuccess, onSaveError);
        }

        $scope.syncUsers = function(){
            console.log('syncUsers');
           FiestaUserSync.save(vm.fiestaUserSync, onSaveSuccess, onSaveError);
        }
         function onSearchSuccess (result) {
                           // $scope.$emit('fiestaSecurityUiApp:endpointSyncUpdate', result);
                           // $uibModalInstance.close(result);
                            vm.isSaving = false;
                           // loadAll();
                           vm.endpoints = result;
         }

         function onSearchError () {
                             vm.isSaving = false;
                           //  loadAll();
                            vm.endpoints = [];
                   }

        function onSaveSuccess (result) {
                   // $scope.$emit('fiestaSecurityUiApp:endpointSyncUpdate', result);
                   // $uibModalInstance.close(result);
                    vm.isSaving = false;
                    loadAll();
                }

         function onSaveError () {
                    vm.isSaving = false;
                    loadAll();
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
