(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fiesta-user', {
            parent: 'entity',
            url: '/fiesta-user?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'FiestaUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiesta-user/fiesta-users.html',
                    controller: 'FiestaUserController',
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
        .state('fiesta-user-detail', {
            parent: 'fiesta-user',
            url: '/fiesta-user/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'FiestaUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiesta-user/fiesta-user-detail.html',
                    controller: 'FiestaUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FiestaUser', function($stateParams, FiestaUser) {
                    return FiestaUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fiesta-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fiesta-user-detail.edit', {
            parent: 'fiesta-user-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user/fiesta-user-dialog.html',
                    controller: 'FiestaUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FiestaUser', function(FiestaUser) {
                            return FiestaUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiesta-user.new', {
            parent: 'fiesta-user',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user/fiesta-user-dialog.html',
                    controller: 'FiestaUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                userId: null,
                                groups: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fiesta-user', null, { reload: 'fiesta-user' });
                }, function() {
                    $state.go('fiesta-user');
                });
            }]
        })
        .state('fiesta-user.edit', {
            parent: 'fiesta-user',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user/fiesta-user-dialog.html',
                    controller: 'FiestaUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FiestaUser', function(FiestaUser) {
                            return FiestaUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiesta-user', null, { reload: 'fiesta-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiesta-user.delete', {
            parent: 'fiesta-user',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiesta-user/fiesta-user-delete-dialog.html',
                    controller: 'FiestaUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FiestaUser', function(FiestaUser) {
                            return FiestaUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiesta-user', null, { reload: 'fiesta-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
