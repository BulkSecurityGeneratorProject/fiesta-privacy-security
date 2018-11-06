(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('endpoint', {
            parent: 'entity',
            url: '/endpoint?page&sort&search',
            data: {

                pageTitle: 'Endpoints'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint/endpoints.html',
                    controller: 'EndpointController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
         .state('shared-endpoint', {
                    parent: 'entity',
                    url: '/shared-endpoint?page&sort&search',
                    data: {

                        pageTitle: 'Endpoints'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/endpoint/shared-endpoints.html',
                            controller: 'SharedEndpointController',
                            controllerAs: 'vm'
                        }
                    },
                    params: {
                        page: {
                            value: '1',
                            squash: true
                        },
                        sort: {
                            value: 'id,asc',
                            squash: true
                        },
                        search: null
                    },
                    resolve: {
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                            return {
                                page: PaginationUtil.parsePage($stateParams.page),
                                sort: $stateParams.sort,
                                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                ascending: PaginationUtil.parseAscending($stateParams.sort),
                                search: $stateParams.search
                            };
                        }],
                    }
                })
        .state('endpoint-detail', {
            parent: 'endpoint',
            url: '/endpoint/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'Endpoint'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint/endpoint-detail.html',
                    controller: 'EndpointDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Endpoint', function($stateParams, Endpoint) {
                    return Endpoint.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'endpoint',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('endpoint-detail.edit', {
            parent: 'endpoint-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint/endpoint-dialog.html',
                    controller: 'EndpointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Endpoint', function(Endpoint) {
                            return Endpoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint.new', {
            parent: 'endpoint',
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
                               $state.go('endpoint', null, { reload: 'endpoint' });
                           }, function() {
                               $state.go('^');
                           });
                       }]
        })
        .state('endpoint.edit', {
            parent: 'endpoint',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint/endpoint-dialog.html',
                    controller: 'EndpointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Endpoint', function(Endpoint) {
                            return Endpoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint', null, { reload: 'endpoint' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint.delete', {
            parent: 'endpoint',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint/endpoint-delete-dialog.html',
                    controller: 'EndpointDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Endpoint', function(Endpoint) {
                            return Endpoint.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint', null, { reload: 'endpoint' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
