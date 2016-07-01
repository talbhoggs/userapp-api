angular
  .module('main')
  .controller('MainCtrl', MainCtrl);

MainCtrl.$inject = ['userservice', '$timeout', '$scope']
    

function MainCtrl(userservice, $timeout, $scope) {

  var vm = this;

  vm.selectedUser = null;
  vm.selectedIndex = null;
  vm.navigation = 'search';
  vm.totalRecords = null;

  vm.userservice = userservice;
   

  vm.selectedRow = selectedRow;
  vm.save = save;
  vm.remove = remove;
  vm.sortBy = sortBy;
  vm.sortFlag = true;
  vm.sortPredicate = 'firstName';
  vm.sortDirection = 'asc'
  vm.message = "";

  $scope.$watch('search', function (search, oldVal) {
    console.log(search);
    if (angular.isDefined(search)) {
      userservice.searchUser(search);
    }
 
  });



  function selectedRow(index, user) {
     /*
      date are formated into millisecond in mysql
      we need to wrap this first with Java script date object
      in order for it to be part by the input date form.

      1454601600000 - mysql form
      new Date(1454601600000)
     */
     user.dob = new Date(user.dob);
	   vm.selectedUser = user;
	   vm.selectedIndex = index;
  }

  function save(form) {

     if(form.$valid) {
        userservice.updateUser(vm.selectedUser).then(function(result) {

          vm.message = result.data.description;

          $timeout(function (argument) {
            clear();
            form.$setPristine();
            form.$setUntouched();
          }, 3000);

        }, function(error) {
          console.log(result.data.description);
        });
     } 

  }

  function clear() {
  	vm.selectedUser = null;
  	vm.selectedIndex = null;
    vm.message = "";
  }

  function remove() {
  	userservice.deleteUser(vm.selectedUser.id).then(function(result) {

        vm.message = "Delete Successful";
       
        $timeout(function (argument) {
          clear();
          form.$setPristine();
          form.$setUntouched();
        }, 1000);
  	});
  }

  function sortBy(name) {
    vm.sortFlag = !vm.sortFlag;
    vm.sortDirection = (vm.sortFlag) ? 'asc' : 'desc';
    vm.sortPredicate = name;
    userservice.sortBy(vm.sortPredicate, vm.sortDirection);
  }




    // or
   
};

//C:\Program Files\Blue Coat Systems\Unified Agent

//http://angularscript.com/angularjs-based-loading-spinner-overlay/
