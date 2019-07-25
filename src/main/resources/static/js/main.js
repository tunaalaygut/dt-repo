$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
    $('.table-hover').DataTable({
        "language": {
            "searchPlaceholder": "Filtrele",
            "search": "",
            "lengthMenu": "_MENU_ kayıt görüntüleniyor",
            "zeroRecords": "Gösterilecek kayıt bulunamadı.",
            "info": "_PAGES_ sayfadan _PAGE_. gösteriliyor",
            "infoEmpty": "Kayıt yok",
            "infoFiltered": "(_MAX_ kayıt içinden filtrelendi)",
            "paginate": {
                "previous": "Önceki",
                "next" : "Sonraki"
            }
        }
    });

    $('#example').DataTable( {
        "pageLength": 5,
        "bLengthChange": false,
        "language": {
            "searchPlaceholder": "Filtrele",
            "search": "",
            "lengthMenu": "_MENU_ kayıt görüntüleniyor",
            "zeroRecords": "Gösterilecek kayıt bulunamadı.",
            "info": "_PAGES_ sayfadan _PAGE_. gösteriliyor",
            "infoEmpty": "Kayıt yok",
            "infoFiltered": "(_MAX_ kayıt içinden filtrelendi)",
            "paginate": {
                "previous": "Önceki",
                "next" : "Sonraki"
            }
        }
    } );

    $('#example2').DataTable( {
        "pageLength": 5,
        "bLengthChange": false,
        "language": {
            "searchPlaceholder": "Filtrele",
            "search": "",
            "lengthMenu": "_MENU_ kayıt görüntüleniyor",
            "zeroRecords": "Gösterilecek kayıt bulunamadı.",
            "info": "_PAGES_ sayfadan _PAGE_. gösteriliyor",
            "infoEmpty": "Kayıt yok",
            "infoFiltered": "(_MAX_ kayıt içinden filtrelendi)",
            "paginate": {
                "previous": "Önceki",
                "next" : "Sonraki"
            }
        }
    } );

    $("body").tooltip({ selector: '[data-toggle=tooltip]' });

    updatePendingBadge();
    updateOtherMemberRequestBadge();

});

function updatePendingBadge(){
    let badge = $("#numOfPendingRequets");

    $.ajax({
        url: "/getNumOfPendingRequests"
    }).done(function(num) {
        if (num !== 0)
            badge.text(num);
    });
}

function updateOtherMemberRequestBadge(){
    let badge = $(".otherMemberRequests");

    $.ajax({
        url: "/getOtherMemberRequests"
    }).done(function(num) {
        if (num !== 0)
            badge.text(num);
    });
}

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

/*!
 DataTables Bootstrap 4 integration
 ©2011-2017 SpryMedia Ltd - datatables.net/license
*/
(function(b) {
    "function" === typeof define && define.amd ? define(["jquery", "datatables.net"], function(a) {
        return b(a, window, document)
    }) : "object" === typeof exports ? module.exports = function(a, d) {
        a || (a = window);
        if (!d || !d.fn.dataTable) d = require("datatables.net")(a, d).$;
        return b(d, a, a.document)
    } : b(jQuery, window, document)
})(function(b, a, d, m) {
    var f = b.fn.dataTable;
    b.extend(!0, f.defaults, {
        dom: "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        renderer: "bootstrap"
    });
    b.extend(f.ext.classes, {
        sWrapper: "dataTables_wrapper dt-bootstrap4",
        sFilterInput: "form-control form-control-sm",
        sLengthSelect: "custom-select custom-select-sm form-control form-control-sm",
        sProcessing: "dataTables_processing card",
        sPageButton: "paginate_button page-item"
    });
    f.ext.renderer.pageButton.bootstrap = function(a, h, r, s, j, n) {
        var o = new f.Api(a),
            t = a.oClasses,
            k = a.oLanguage.oPaginate,
            u = a.oLanguage.oAria.paginate || {},
            e, g, p = 0,
            q = function(d, f) {
                var l, h, i, c, m = function(a) {
                    a.preventDefault();
                    !b(a.currentTarget).hasClass("disabled") && o.page() != a.data.action && o.page(a.data.action).draw("page")
                };
                l = 0;
                for (h = f.length; l < h; l++)
                    if (c = f[l], b.isArray(c)) q(d, c);
                    else {
                        g = e = "";
                        switch (c) {
                            case "ellipsis":
                                e = "&#x2026;";
                                g = "disabled";
                                break;
                            case "first":
                                e = k.sFirst;
                                g = c + (0 < j ? "" : " disabled");
                                break;
                            case "previous":
                                e = k.sPrevious;
                                g = c + (0 < j ? "" : " disabled");
                                break;
                            case "next":
                                e = k.sNext;
                                g = c + (j < n - 1 ? "" : " disabled");
                                break;
                            case "last":
                                e = k.sLast;
                                g = c + (j < n - 1 ? "" : " disabled");
                                break;
                            default:
                                e = c + 1, g = j === c ? "active" : ""
                        }
                        e && (i = b("<li>", {
                            "class": t.sPageButton + " " + g,
                            id: 0 === r && "string" === typeof c ? a.sTableId + "_" + c : null
                        }).append(b("<a>", {
                            href: "#",
                            "aria-controls": a.sTableId,
                            "aria-label": u[c],
                            "data-dt-idx": p,
                            tabindex: a.iTabIndex,
                            "class": "page-link"
                        }).html(e)).appendTo(d), a.oApi._fnBindAction(i, {
                            action: c
                        }, m), p++)
                    }
            },
            i;
        try {
            i = b(h).find(d.activeElement).data("dt-idx")
        } catch (v) {}
        q(b(h).empty().html('<ul class="pagination "/>').children("ul"), s);
        i !== m && b(h).find("[data-dt-idx=" + i + "]").focus()
    };
    return f
});