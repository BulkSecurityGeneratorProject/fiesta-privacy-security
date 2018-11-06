(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'EndpointSync','FiestaUserSync', '$state','TestbebService'];

    function HomeController ($scope, Principal, LoginService,EndpointSync,FiestaUserSync,  $state, TestbebService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

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


        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        getTestbed();
        function getTestbed() {
          vm.testbedInformation = TestbebService.query();

        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

         function startSyncEndpointIndicator() {
             $('#syncEndpointSpan').attr('class','glyphicon glyphicon-refresh fa-spin');
         }
         function stopSyncEndpointIndicator() {
            $('#syncEndpointSpan').attr('class','glyphicon glyphicon-refresh');
         }

          function startSyncUserIndicator() {
                      $('#syncUserSpan').attr('class','glyphicon glyphicon-refresh fa-spin');
                  }
                  function stopSyncUserIndicator() {
                     $('#syncUserSpan').attr('class','glyphicon glyphicon-refresh');
                  }

         $scope.syncEndpoints = function() {
                    console.log('syncEndpoints');
                    startSyncEndpointIndicator();
                   EndpointSync.save(vm.endpointSync, onSaveEndpointSuccess, onSaveEndpointError);
                }

                $scope.syncUsers = function(){
                    console.log('syncUsers');
                    startSyncUserIndicator();
                   FiestaUserSync.save(vm.fiestaUserSync, onSaveUserSuccess, onSaveUserError);
                }
                 function onSearchSuccess (result) {
                       vm.isSaving = false;
                       vm.endpoints = result;

                 }

                 function onSearchError () {
                       vm.isSaving = false;

                       vm.endpoints = [];

                    }

                function onSaveEndpointSuccess (result) {
                        vm.isSaving = false;
                        stopSyncEndpointIndicator();
                  }

                 function onSaveEndpointError () {
                      vm.isSaving = false;
                       stopSyncEndpointIndicator();
                  }


                   function onSaveUserSuccess (result) {
                                          vm.isSaving = false;
                                          stopSyncUserIndicator();
                                    }

                                   function onSaveUserError () {
                                        vm.isSaving = false;
                                         stopSyncUserIndicator();
                                    }

    }
})();
