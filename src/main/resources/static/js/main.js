$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
    $('.table-hover').DataTable();
    $('#example').DataTable( {
        "pageLength": 5,
        "bLengthChange": false,
        "language": {
            "searchPlaceholder": "Kullanıcı filtrele",
            "search": ""
        }
    } );

    $('#example2').DataTable( {
        "pageLength": 5,
        "bLengthChange": false,
        "language": {
            "searchPlaceholder": "Kullanıcı filtrele",
            "search": ""
        }
    } );

});

jQuery('a').click( function(e) {
    jQuery('.collapse').collapse('hide');
});

$(".alert").alert();