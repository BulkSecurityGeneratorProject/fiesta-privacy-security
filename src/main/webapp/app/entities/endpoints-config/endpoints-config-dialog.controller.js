(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('EndpointsConfigDialogController', EndpointsConfigDialogController);

    EndpointsConfigDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EndpointsConfig', 'Endpoint', 'FiestaUser','EndpointUser','AlertService','QuantityKinds'];

    function EndpointsConfigDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EndpointsConfig, Endpoint, FiestaUser,EndpointUser,AlertService,QuantityKinds) {
        var vm = this;

        vm.endpointsConfig = entity;
        vm.clear = clear;
        vm.save = save;



        vm.quantityKinds = QuantityKinds.query(function(response) {
                 var quantityKind = vm.selectedQuantityKind;
               //  var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
                 vm.selectedQuantityKind = quantityKind;
              });


         $scope.selectQuantitykind = function() {
             vm.endpoints = Endpoint.query({page:0, size: 3000, share:vm.searchQuery});
         }

        vm.endpoints = Endpoint.query({page:0, size: 3000, share:'all'});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if(vm.endpointsConfig.isPublic ==false && vm.endpointsConfig.isPrivate == false ) {
              alert('Please select public or private');
            }
            if(vm.endpointsConfig.isPublic == null && vm.endpointsConfig.isPrivate == null ) {

                alert('Please select public or private');
            }
            if(vm.endpoints == null || vm.endpoints.length ==0 || vm.endpointUsers == null || vm.endpointUsers == 0) {

             alert('Please sync endpoints or users before create policy for endpoints! ')

            } else {

                console.log('vm.endpointsConfig.isPublic'+ vm.endpointsConfig.isPublic);
                console.log('vm.endpointsConfig.isPrivate'+ vm.endpointsConfig.isPrivate);

                vm.endpointsConfig.endpointUsers = vm.endpointUsers;
                EndpointsConfig.save(vm.endpointsConfig, onSaveSuccess, onSaveError);

            }

        }


        $scope.checkPublic = function() {
                    console.log('check public');
                    if(vm.endpointsConfig.isPublic) {
                       vm.endpointsConfig.isPrivate = false;
                    } else {
                        //vm.isPrivate = null;
                    }
                    console.log('private:'+ vm.endpointsConfig.isPrivate);
                    console.log('public:'+ vm.endpointsConfig.isPublic);
                }

                $scope.checkPrivate = function() {


                     if(vm.endpointsConfig.isPrivate) {
                                   vm.endpointsConfig.isPublic = false;
                                } else {
                                    //vm.isPublic = null;
                                }


                      console.log('private:'+ vm.endpointsConfig.isPrivate);
                      console.log('public:'+ vm.endpointsConfig.isPublic);
                }


        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:endpointUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

          $scope.checkAllEndpoints = function(){
          console.log('checkAllEndpoints');
            vm.endpointsConfig.endpoints = vm.endpoints;
          };
          $scope.uncheckAllEndpoints = function(){
            console.log('uncheckAllEndpoints');
            vm.endpointsConfig.endpoints = [];
          };

          $scope.checkAllUsers = function(){
          console.log('checkAllUsers');
                      vm.endpointsConfig.fiestaUsers = vm.fiestausers;
          };
           $scope.unCheckAllUsers = function(){
           console.log('unCheckAllUsers');
                      vm.endpointsConfig.fiestaUsers = [];
           };


      //

         $scope.disallowAccessAll = function(endpointUser, disallowAccess, allowAccess, visible) {
               if(disallowAccess) {
                  endpointUser.allowAccess = null;
               }
                console.log(endpointUser);
              }
              //
               vm.loadPage = loadPage;
                       loadAll();
                       function loadAll () {
                           EndpointUser.query({
                               page: 0,
                               size: 100,
                               sort: sort(),
                               endpointUrl:  'all'
                           }, onSuccess, onError);
                           function sort() {
                               var result = "id,desc"
                               return result;
                           }
                           function onSuccess(data, headers) {
                               vm.endpointUsers = data;
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
