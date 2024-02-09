// a function to get the url for the form
$(document).ready(function () {
    $("#student-select").change(function () {
        let value = $(this).val();
        document.location.replace(formUrl(value))
    });
});

// // change margin left on iframe load
// $(document).ready(function() {
//     $("iframe").on("load", function() {
//         $(".doc-content").css("margin-left", "21% !important");
//     });
// });

// this function will call the api to update the content body of the document whenever a student is selected
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