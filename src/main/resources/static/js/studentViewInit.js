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

$(document).ready(function() {
    $(".individual-student-assignment").click(function() {
        $.ajax({
            type: "GET",
            url: "/api/getStudentAssignmentContentBodyById",
            data: {submissionId: $(this).attr("id")},
        }).done(function(data) {
            $("#assignment-content").empty();
            $("#assignment-content").append(data);
        });
    });
});