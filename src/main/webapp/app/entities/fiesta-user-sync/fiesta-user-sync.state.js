(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fiesta-user-sync', {
            parent: 'entity',
            url: '/fiesta-user-sync?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'FiestaUserSyncs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-syncs.html',
                    controller: 'FiestaUserSyncController',
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
        .state('fiesta-user-sync-detail', {
            parent: 'fiesta-user-sync',
            url: '/fiesta-user-sync/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'FiestaUserSync'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-sync-detail.html',
                    controller: 'FiestaUserSyncDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FiestaUserSync', function($stateParams, FiestaUserSync) {
                    return FiestaUserSync.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fiesta-user-sync',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fiesta-user-sync-detail.edit', {
            parent: 'fiesta-user-sync-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-sync-dialog.html',
                    controller: 'FiestaUserSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FiestaUserSync', function(FiestaUserSync) {
                            return FiestaUserSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiesta-user-sync.new', {
            parent: 'fiesta-user-sync',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-sync-dialog.html',
                    controller: 'FiestaUserSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fiesta-user-sync', null, { reload: 'fiesta-user-sync' });
                }, function() {
                    $state.go('fiesta-user-sync');
                });
            }]
        })
        .state('fiesta-user-sync.edit', {
            parent: 'fiesta-user-sync',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-sync-dialog.html',
                    controller: 'FiestaUserSyncDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FiestaUserSync', function(FiestaUserSync) {
                            return FiestaUserSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiesta-user-sync', null, { reload: 'fiesta-user-sync' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiesta-user-sync.delete', {
            parent: 'fiesta-user-sync',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user-sync/fiesta-user-sync-delete-dialog.html',
                    controller: 'FiestaUserSyncDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FiestaUserSync', function(FiestaUserSync) {
                            return FiestaUserSync.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiesta-user-sync', null, { reload: 'fiesta-user-sync' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
