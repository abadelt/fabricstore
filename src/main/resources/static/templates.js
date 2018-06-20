angular.module('templatesApp', [])
    .controller('TemplatesListController', function($http) {
        var templatesList = this;
        templatesList.templates = [];

        templatesList.gettemplatesList = function() {
            $http.get('/templates/').
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
            var template = {fileName: f.name, description: templatesList.description};

            $http.post('/templates/', template).
            then(function(response) {
                var fd = new FormData();
                fd.append('file', f);
                return $http.put(response.headers("Location"), fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                });
            })
                .then(function(response) {
                    templatesList.title = "";
                    templatesList.keywords = "";
                    templatesList.gettemplatesList();
                    document.getElementById('template').templates[0] = undefined;
                });
        }
    });