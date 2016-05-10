/**
 * Created by Alex on 18.02.2016.
 */

angular.module('myApp')
    .controller('postController', function ($scope, $http) {
        $scope.isUploading = false;
        $scope.isPictureExist = false;
        $scope.posts = [];
        $scope.currentIndex;
        $scope.currentTemplate = -1;
        $scope.title;
        $scope.text;
        $scope.tags = [];
        $scope.postListLen = 0;
        var e = document.getElementById("category");
        $scope.category = e.options[e.selectedIndex].value;
        $scope.showField = false;
        $scope.achievements = [];
        $scope.video;
        $scope.showField = false;
        $scope.templateType = 0;
        $scope.readyYouTube = "";
        $scope.showEditor=false;




        $scope.setTemplate = function (value) {
            if (value >= 0 || value <= 1) {
                $scope.templateType = value;
            }
            else {
                $scope.showField = false;
            }
        };

        $scope.handleYouTube = function (video) {
            if (typeof video !== 'undefined') {
                $scope.readyYouTube = video.replace('https://www.youtube.com/watch?v=','');
                $scope.readyYouTube = $scope.readyYouTube.replace('https://www.youtube.com/embed/','');
                $scope.readyYouTube.replace('&index=18&', '?');
                $scope.readyYouTube.replace('&', '?');
                $scope.readyYouTube = "https://www.youtube.com/embed/" + $scope.readyYouTube;
                return $scope.readyYouTube;
            }
            return "";
        };

        $scope.showTemplate = function () {
            $scope.showField = !$scope.showField;
            $scope.templateType = 0;
        };

        $scope.getPosts = function () {
            $http({
                method: 'GET',
                url: '/getposts'
            }).then(function successCallback(response) {
                $scope.posts = response.data;
            }, function errorCallback(response) {

            });
        };

        $scope.getPosts();

        $scope.savePost = function () {
            $scope.isUploading = true;
            var post = $scope.posts[$scope.currentIndex];
            post.title = $scope.title;
            post.text = CKEDITOR.instances.editor1.getData();
            if (post.text.length > 30000) {
                alert("Too mush text")
                $scope.isUploading = false;
                return;
            }
            post.category = $scope.category;
            post.tags = $scope.tags;
            var isUnique = true;
            for (var i = 0; i < $scope.posts.length; i++) {
                if ($scope.title == $scope.posts[i].title && $scope.title != post.title) {
                    isUnique = false;
                }
            }
            if ($scope.title != "" && isUnique)
                post.title = $scope.title;
            if($scope.currentTemplate==0) {
                $scope.saveImage()
                    .then (function (response) {
                        post.image = response.data.data;
                    }).then(function () {
                }, function () {
                    console.log("empty image")
                }).then(function () {
                    return $http({
                        method: 'POST',
                        url: '/savepost',
                        headers: {'Content-Type': undefined},
                        data: post
                    }).then(function successCallback(response) {
                        $scope.isUploading = false;
                    }, function errorCallback(response) {
                        $scope.isUploading = false;
                    });

                })
            } else {
                if (typeof $scope.video === 'undefined' || $scope.video === "") {
                    alert("Please insert video URL");
                    $scope.isUploading = false;
                    return;
                }
                post.image = $scope.handleYouTube($scope.video);
                $http({
                    method: 'POST',
                    url: '/savepost',
                    headers: {'Content-Type': undefined},
                    data: post
                }).then(function successCallback(response) {
                    $scope.isUploading = false;
                }, function errorCallback(response) {
                    $scope.isUploading = false;
                });
            }
        };

        $scope.deletePost = function (index) {
            var post = $scope.posts[index];
            $http({
                method: 'POST',
                url: '/deletepost',
                headers: {'Content-Type': undefined},
                data: post
            }).then(function successCallback(response) {
                $scope.showEditor=false;
                $scope.getPosts();
            }, function errorCallback(response) {
                $scope.getPosts();
                $scope.showEditor=false;
            });
        };

        $scope.setPost = function (index) {
            $scope.showEditor=true;
            $scope.postImage=" ";
            $scope.title = $scope.posts[index].title;
            $scope.currentIndex = index;
            $scope.currentTemplate = $scope.posts[index].template;
            $scope.text = $scope.posts[$scope.currentIndex].text;
            if ($scope.posts[$scope.currentIndex].image !== null) {
                $scope.postImage = $scope.posts[$scope.currentIndex].image;
                $scope.isPictureExist = true;
                if ($scope.currentTemplate == 1) {
                    $scope.video = $scope.posts[$scope.currentIndex].image;
                }
            }
            else {
                $scope.isPictureExist = false;
            }
            CKEDITOR.instances.editor1.setData($scope.text, function () {
                this.checkDirty();  // true
            });
            $scope.category = $scope.posts[$scope.currentIndex].category;
            //$scope.comments=$scope.posts[$scope.currentIndex].comments; todo
            $scope.date = $scope.posts[$scope.currentIndex].date;
            $scope.tags = $scope.posts[$scope.currentIndex].tags;
            $("#textbox").trigger("change");
        };

        $scope.newPost = function () {
            $scope.isUploading = true;
            if ($scope.title == "" || $scope.title == undefined) {
                $scope.isUploading = false;
                return;
            }
            for (var i = 0; i < $scope.posts.length; i++) {
                if ($scope.title == $scope.posts[i].title) {
                    alert("The same post is already exist!")
                    $scope.isUploading = false;
                    return;
                }
            }
            $scope.post = {};
            $scope.post.template = $scope.templateType;
            $scope.post.text = CKEDITOR.instances.editor1.getData();
            if ($scope.post.text.length > 30000) {
                alert("Too mush text")
                return;
            }
            $scope.post.category = $scope.category;
            $scope.post.tags = $scope.tags;
            if ($scope.post.template == 0) {

                $scope.saveImage()
                    .then(function (response) {
                        $scope.post.image = response.data.data;
                    })
                    .then(function () {
                        $scope.getPosts();
                    }, function () {
                        console.log("empty image")
                    }).then(function () {
                    $scope.post.title = $scope.title;
                    return $http({
                        method: 'POST',
                        url: '/savepost',
                        headers: {'Content-Type': undefined},
                        data: $scope.post
                    })
                }).then(function () {
                    $scope.getPosts();
                    $scope.showField = false;
                    $scope.isUploading = false;
                    $scope.getAchievements();
                });
            } else {
                $scope.post.image = $scope.readyYouTube;
                if ($scope.post.image === "") {
                    alert("Please insert video URL");
                    return;
                }
                $scope.post.title = $scope.title;
                $http({
                    method: 'POST',
                    url: '/savepost',
                    headers: {'Content-Type': undefined},
                    data: $scope.post
                }).then(function successCallback(response) {
                    $scope.getPosts();
                    $scope.showField = false;
                    $scope.isUploading = false;
                    $scope.getAchievements();
                }, function errorCallback(response) {
                });
            }
        }
        $scope.saveImage = function () {
            return $http({
                method: 'POST',
                url: '/saveimage',
                headers: {
                    'Content-Type': "application/json"
                },
                data: $scope.postImage

            });
        };
        $scope.convertDate = function (date) {
            if (typeof date === 'undefined')
                return "";
            var _date = new Date(date);
            var tempDate = _date.toString().split(" ");
            return tempDate[1] + " " + tempDate[2] + " " + tempDate[3];
        };

        $scope.extendList = function () {
            for (var i = 0; i < 2 && $scope.posts.length > $scope.postListLen; i++) {
                $scope.postListLen++;
            }
        };

        $scope.getAchievements = function () {
            $http({
                method: 'GET',
                url: '/getAchievements'
            }).then(function successCallback(response) {
                $scope.achievements = response.data;
            }, function errorCallback(response) {

            });
        };
        $scope.getAchievements();

    });
