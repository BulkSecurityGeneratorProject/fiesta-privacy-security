(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('RequestAccessDialogController', RequestAccessDialogController);

    RequestAccessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'RequestAccess'];

    function RequestAccessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, RequestAccess) {
        var vm = this;

        vm.requestAccess = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.requestAccess.id !== null) {
                RequestAccess.update(vm.requestAccess, onSaveSuccess, onSaveError);
            } else {
                RequestAccess.save(vm.requestAccess, onSaveSuccess, onSaveError);
            }
        }

        $scope.checkApproved = function() {
           if(vm.requestAccess.approved) {
              vm.requestAccess.rejected = false;
           }
        }

        $scope.checkRejected = function() {
            if(vm.requestAccess.rejected) {
              vm.requestAccess.approved = false;
           }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:requestAccessUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.requestDate = false;
        vm.datePickerOpenStatus.approvedDate = false;
        vm.datePickerOpenStatus.seenByRequesterDate = false;
        vm.datePickerOpenStatus.seenByReceiverDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
