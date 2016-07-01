angular
  .module('main')
  .controller('CreateUserCtrl', CreateUserCtrl);

CreateUserCtrl.$inject = ['userservice', '$timeout']

function CreateUserCtrl(userservice, $timeout) {

  var vm = this;
  vm.formData = {};
  vm.save = saveUser;
  vm.message = '';

  function saveUser(form) {
    if(form.$valid) {
      userservice.createUser(vm.formData).then(function(result) {
        vm.message = result.data.description;
        $timeout(function (argument) {
          clear();
          form.$setPristine();
          form.$setUntouched();
        }, 3000);
      }, function(error) {
        console.log(error.data.description);
      });
    }
    else {
      console.log("Form not is valid");
    }
    
  }

  function clear() {
    vm.message = '';
    vm.formData = {};
  }



};