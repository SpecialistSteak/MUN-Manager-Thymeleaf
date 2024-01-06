$(document).ready(function () {
    $("#student-select").change(function () {
        let value = $(this).val();
        document.location.replace(formUrl(value))
    });
});

$(document).ready(function() {
    $("iframe").on("load", function() {
        $(".doc-content").css("margin-left", "21% !important");
    });
});
