angular.module('main')
  .factory('userservice', userService);

userService.$inject = ['$http', 'UserServiceFactory'];

function userService($http, UserServiceFactory){

  var service = {
    getUsers: getUsers,
    getUser: getUser,
    deleteUser : deleteUser,
    createUser : createUser,
    updateUser : updateUser,
    usersArray : [],
    nextPage : nextPage,
    prevPage : prevPage,
    page : 1,
    per_page : 5,
    next : false,
    prev : false,
    totalRecords : 0,
    numberOfPages : numberOfPages,
    selectedUser : null,
    sort_direc : 'asc',
    sort : 'firstName',
    sortFlag : true,
    sortBy : sortBy,
    loading : true,
    search : "",
    searchUser : searchUser
  };

  var param = "";
  var usersObj = null;

  loadUsers();

  return service;

  function updateUser(user){
    return UserServiceFactory.updateUser(user);
  }

  function getUsers(params){
    var parameter = (params) ? params : "";
    return UserServiceFactory.getUsers(parameter);
  }

  function getUser(id){
    UserServiceFactory.getUser(id);
  }

  function deleteUser(id) {
    return UserServiceFactory.deleteUser(id).then(function() {
        loadUsers();
    });
    
  }

  function createUser(user) {
    return UserServiceFactory.createUser(user);
  }

  function loadUsers() {
    service.loading = true;

    var d = new Date();
    var n = d.getTime();
    var parameter = "?page=" + service.page;
        parameter += "&per_page=" + service.per_page;
        parameter += "&sort=" + service.sort;
        parameter += "&sort_direc=" + service.sort_direc;
        parameter += "&search=" + service.search;
        parameter += "&_=" + n;

    console.log(parameter)
  
    UserServiceFactory.getUsers(parameter).then(function (result) {
        service.totalRecords = result.data.totalRecords;
        service.page = result.data.currentPage;
        service.next = result.data.next;
        service.prev = result.data.prev;
        usersObj = result.data;
        
        clearUsersArray();
        service.usersArray.push.apply(service.usersArray, usersObj.entries);
        service.loading = false;
        console.log(service.loading);
    });
  
  }

  function sortBy(name, direction) {
    clearUsersArray();
    service.sort = name;
    service.sort_direc = direction;
    loadUsers();
  }

  function searchUser(name) {
    service.page = 1;
    service.search = name;
    loadUsers();
  }

  
  function nextPage() {
      clearUsersArray();
      service.page++;
      loadUsers();
  }

  function prevPage() {
      clearUsersArray();
      service.page--;
      loadUsers();
  }

  function numberOfPages() {
    return Math.ceil(service.totalRecords / service.per_page);
  }

  function clearUsersArray() {
    service.usersArray = [];
  }

}