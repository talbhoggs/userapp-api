angular
  .module('main', ['ngRoute'])
  .config(config);

function config($routeProvider) {
    var d = new Date();
    var n = d.getTime();
    $routeProvider
        .when('/search', {
          templateUrl: 'search-user.html?_=' + d,
          controller: 'MainCtrl',
          controllerAs: 'vm'
        }).when('/create', {
          templateUrl: 'create-user.html',
          controller: 'CreateUserCtrl',
          controllerAs: 'vm'
        }).when('/todo', {
          templateUrl: 'about-page.html',
        }).otherwise({
      redirectTo : "/search"
      });
}



//{xor}LTAwKwww
//{xor}LTAwKw==