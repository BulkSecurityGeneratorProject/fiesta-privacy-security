(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('endpoint-sync', {
            parent: 'entity',
            url: '/endpoint-sync?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'EndpointSyncs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint-sync/endpoint-syncs.html',
                    controller: 'EndpointSyncController',
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
        .state('endpoint-sync-detail', {
            parent: 'endpoint-sync',
            url: '/endpoint-sync/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'EndpointSync'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint-sync/endpoint-sync-detail.html',
                    controller: 'EndpointSyncDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EndpointSync', function($stateParams, EndpointSync) {
                    return EndpointSync.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'endpoint-sync',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('endpoint-sync-detail.edit', {
            parent: 'endpoint-sync-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-sync/endpoint-sync-dialog.html',
                    controller: 'EndpointSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointSync', function(EndpointSync) {
                            return EndpointSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint-sync.new', {
            parent: 'endpoint-sync',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-sync/endpoint-sync-dialog.html',
                    controller: 'EndpointSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                created: null,
                                updated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('endpoint-sync', null, { reload: 'endpoint-sync' });
                }, function() {
                    $state.go('endpoint-sync');
                });
            }]
        })
        .state('endpoint-sync.edit', {
            parent: 'endpoint-sync',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-sync/endpoint-sync-dialog.html',
                    controller: 'EndpointSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointSync', function(EndpointSync) {
                            return EndpointSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint-sync', null, { reload: 'endpoint-sync' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint-sync.delete', {
            parent: 'endpoint-sync',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-sync/endpoint-sync-delete-dialog.html',
                    controller: 'EndpointSyncDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EndpointSync', function(EndpointSync) {
                            return EndpointSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint-sync', null, { reload: 'endpoint-sync' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
