function submitAssignment() {
    console.log("submitAssignment");
}

function submitProject() {
    console.log("submitProject");
}

function logTest() {
    $.ajax({
        url: "/api/conferenceName/" + $("#conf-select").val(),
        type: "GET",
        success: function (data) {
            $("#conference-title").text(data);
        }
    });
}