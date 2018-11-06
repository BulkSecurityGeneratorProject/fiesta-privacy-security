(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('experimenter', {
            parent: 'entity',
            url: '/experimenter?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'Experimenters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/experimenter/experimenters.html',
                    controller: 'ExperimenterController',
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
        .state('experimenter-detail', {
            parent: 'experimenter',
            url: '/experimenter/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'Experimenter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/experimenter/experimenter-detail.html',
                    controller: 'ExperimenterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Experimenter', function($stateParams, Experimenter) {
                    return Experimenter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'experimenter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('experimenter-detail.edit', {
            parent: 'experimenter-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/experimenter/experimenter-dialog.html',
                    controller: 'ExperimenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Experimenter', function(Experimenter) {
                            return Experimenter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('experimenter.new', {
            parent: 'experimenter',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/experimenter/experimenter-dialog.html',
                    controller: 'ExperimenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                groups: null,
                                userId: null,
                                mainGroupName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('experimenter', null, { reload: 'experimenter' });
                }, function() {
                    $state.go('experimenter');
                });
            }]
        })
        .state('experimenter.edit', {
            parent: 'experimenter',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/experimenter/experimenter-dialog.html',
                    controller: 'ExperimenterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Experimenter', function(Experimenter) {
                            return Experimenter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('experimenter', null, { reload: 'experimenter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('experimenter.delete', {
            parent: 'experimenter',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/experimenter/experimenter-delete-dialog.html',
                    controller: 'ExperimenterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Experimenter', function(Experimenter) {
                            return Experimenter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('experimenter', null, { reload: 'experimenter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
