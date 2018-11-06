(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointDialogController', EndpointDialogController);

    EndpointDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Endpoint', 'FiestaUser','EndpointUser','ParseLinks'];

    function EndpointDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Endpoint, FiestaUser, EndpointUser,ParseLinks) {
        var vm = this;

        vm.endpoint = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.fiestausers = FiestaUser.query();


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        vm.isPrivate = vm.endpoint.private;
        vm.isPublic = vm.endpoint.public;

        function save () {
            vm.isSaving = true;
            var data = {
               endpoint: vm.endpoint,
               isPublic: vm.isPublic,
               isPrivate: vm.isPrivate,
               endpointUsers: vm.endpointUsers
            };

             console.log('final private:'+ vm.isPrivate);
             console.log('final public:'+ vm.isPublic);

            if (vm.endpoint.id !== null) {
                Endpoint.update(vm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:endpointUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        $scope.checkPublic = function() {
            console.log('check public');
            if(vm.isPublic) {
               vm.isPrivate = false;
            } else {
                //vm.isPrivate = null;
            }
            console.log('private:'+ vm.isPrivate);
            console.log('public:'+ vm.isPublic);
        }

        $scope.checkPrivate = function() {


             if(vm.isPrivate) {
                           vm.isPublic = false;
                        } else {
                            //vm.isPublic = null;
                        }


              console.log('private:'+ vm.isPrivate);
              console.log('public:'+ vm.isPublic);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        $scope.checkDeinedAccessUser = function() {
            console.log("checkDeinedAccessUser");
            syncSelectUser();
        }
        $scope.checkPublicAccessUser = function() {
            console.log("checkPublicAccessUser");
            syncSelectUser();
        }
        $scope.checkVisibleAccessUser = function() {
            console.log("checkVisibleAccessUser");
            syncSelectUser();
        }

        function syncSelectUser() {
           console.log(vm.endpoint.publicAccessUsers);
           console.log(vm.endpoint.deniedAccessUsers);
           console.log(vm.endpoint.visibleAccessUsers);
        }
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }




        $scope.disallowAccessAll = function(endpointUser, disallowAccess, allowAccess, visible) {


         if(disallowAccess) {
            endpointUser.allowAccess = null;
           // endpointUser.allowAccess = null;
         }

          console.log(endpointUser);


        }


        //
         vm.loadPage = loadPage;
                 //vm.predicate = pagingParams.predicate;
                 //vm.reverse = pagingParams.ascending;
                 //vm.transition = transition;
                 //vm.itemsPerPage = paginationConstants.itemsPerPage;

                 loadAll();

                 function loadAll () {
                     EndpointUser.query({
                         page: 0,
                         size: 100,
                         sort: sort(),
                         endpointUrl:  vm.endpoint.url
                     }, onSuccess, onError);
                     function sort() {
                         var result = "id,desc"
                         return result;
                     }
                     function onSuccess(data, headers) {
                         vm.links = ParseLinks.parse(headers('link'));
                         vm.totalItems = headers('X-Total-Count');
                         vm.queryCount = vm.totalItems;
                         vm.endpointUsers = data;
                        // vm.page = pagingParams.page;
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

        //
    }
})();
