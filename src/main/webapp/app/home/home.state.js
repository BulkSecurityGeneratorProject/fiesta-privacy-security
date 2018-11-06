(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            }
        }).state('home.new', {
                      parent: 'home',
                                 url: '/new',
                                 data: {
                                     //authorities: ['ROLE_USER']
                                 },
                                 onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                                     $uibModal.open({
                                         templateUrl: 'app/entities/endpoints-config/endpoints-config-dialog.html',
                                         controller: 'EndpointsConfigDialogController',
                                         controllerAs: 'vm',
                                         backdrop: 'static',
                                         size: 'lg',
                                         resolve: {
                                             entity: function () {
                                                 return {
                                                     checkAll: null,
                                                     id: null
                                                 };
                                             }
                                         }
                                     }).result.then(function() {
                                         $state.go('home', null, { reload: 'home' });
                                     }, function() {
                                         $state.go('^');
                                     });
                                 }]
                  });
    }
})();
