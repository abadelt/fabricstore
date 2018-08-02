require('./angular.min.js');

angular.module('templatesApp', [])
    .controller('TemplatesListController', function($http) {
        var templatesList = this;
        templatesList.templates = [];

        templatesList.gettemplatesList = function() {
            $http.get('/templates/?page=0&size=10000').
            success(function(data, status, headers, config) {
                if (data._embedded != undefined) {
                    templatesList.templates = [];
                    angular.forEach(data._embedded.templates, function(template) {
                        templatesList.templates.push(template);
                    });
                }
            });
        };
        templatesList.gettemplatesList();

        templatesList.getHref = function(template) {
            return template._links["self"].href
        };

        templatesList.upload = function() {
            var f = document.getElementById('template').files[0];
            console.log("File f is: " + f.name);
            var template = {fileName: f.name, description: templatesList.description, content: f.name};

            $http.post('/templates/', template).
                then(function(response) {
                    var fd = new FormData();
                    fd.append('content', f, f.name);
                    console.log("Uploading to: " + response.headers("Location") + "/content");
                    return $http.put(response.headers("Location") + "/content", fd, {
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    });
                })
                .then(function(response) {
                    templatesList.title = "";
                    templatesList.keywords = "";
                    templatesList.gettemplatesList();
                    document.getElementById('template').files[0] = undefined;
                });
        }
    });