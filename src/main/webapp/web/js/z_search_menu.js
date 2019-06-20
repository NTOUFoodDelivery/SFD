var pagetype = "restaurant";
var searchbar = "<input type='text' id ='searcher' >";
if ((pagetype == "restaurant" || pagetype == "menu")) {

}
else {

}
function searcher() {
    const string_search = document.getElementById('searcher').value;
    //alert(string_search);
    var strss = string_search;

    if (pagetype == "restaurant") { import_restaurant(strss); }
    else if(pagetype=="menu"){}





}
