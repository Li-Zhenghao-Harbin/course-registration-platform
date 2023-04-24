function setNavigation(path) {
    var locations = window.location.toString().split('/');
    var currentLocation = locations[locations.length - 1]
    document.getElementById("navigation").innerHTML = "<a href=platform.html>平台</a>&nbsp;&gt;&gt;&nbsp;<a href=" + currentLocation + ">"+ path +"</a>";
}