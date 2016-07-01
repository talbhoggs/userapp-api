angular.module('main')
  .factory('UserServiceFactory', UserServiceFactory);

UserServiceFactory.$inject = ['$http', '$q'];

function UserServiceFactory($http, $q){

  var service = {
    getUsers: getUsers,
    getUser: getUser,
    deleteUser : deleteUser,
    createUser : createUser,
    updateUser : updateUser,
    selectedUser : null
  };

  return service;

  /*
  function updateUser(user){
    return $http.put('http://localhost:9080/user-api/rest/service/'+user.id, user);
  }

  function getUsers(params){
    return $http.get('http://localhost:9080/user-api/rest/service/'+params);
  }

  function getUser(id){
    return $http.get('http://localhost:9080/user-api/rest/service/'+ id);
  }

  function deleteUser(id) {
    return $http.delete('http://localhost:9080/user-api/rest/service/'+ id);
  }

  function createUser(data) {
    return $http.post('http://localhost:9080/user-api/rest/service/', data);
  }
  */
  /*
  function updateUser(user){
    return $http.put('http://user-api.mybluemix.net/user-api/rest/service/'+user.id, user);
  }

  function getUsers(params){
    return $http.get('http://user-api.mybluemix.net/user-api/rest/service/'+params);
  }

  function getUser(id){
    return $http.get('http://user-api.mybluemix.net/user-api/rest/service/'+ id);
  }

  function deleteUser(id) {
    return $http.delete('http://user-api.mybluemix.net/user-api/rest/service/'+ id);
  }

  function createUser(data) {
    return $http.post('http://user-api.mybluemix.net/user-api/rest/service/', data);
  }
  */
  
  function updateUser(user){
	    return $http.put('rest/service/'+user.id, user);
	  }

	  function getUsers(params){
	    return $http.get('rest/service/'+params);
	  }

	  function getUser(id){
	    return $http.get('rest/service/'+ id);
	  }

	  function deleteUser(id) {
	    return $http.delete('rest/service/'+ id);
	  }

	  function createUser(data) {
	    return $http.post('rest/service/', data);
	  }

}