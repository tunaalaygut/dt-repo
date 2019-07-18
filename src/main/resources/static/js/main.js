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

$('.acceptMeetingRequest').on('submit', function(e) {
    var form = this;
    e.preventDefault();
    swal({
        title: "Talebi Onaylamak İstediğinize Emin Misiniz?",
        text: "Toplantı talebi onayı sonradan değiştirilemez.",
        icon: "success",
        buttons: true,
        buttons: ["Vazgeç", "Evet"],
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            form.submit()
        }
    });
});

$('.declineMeetingRequest').on('submit', function(e) {
    var form = this;
    e.preventDefault();
    swal({
        title: "Talebi Reddetmek İstediğinize Emin Misiniz?",
        icon: "info",
        buttons: true,
        buttons: ["Vazgeç", "Evet"],
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            form.submit()
        }
    });
});