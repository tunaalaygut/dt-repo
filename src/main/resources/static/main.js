$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
});

jQuery('a').click( function(e) {
    jQuery('.collapse').collapse('hide');
});