// a function to POST a new assignment to the server
function submitAssignment() {
    $.ajax({
        url: '/api/newAssignment',
        type: 'POST',
        data: jQuery.param({
            assignmentName: $('#assignment-name').val(),
            assignmentDescription: $('#assignment-description').val(),
            dueDate: $('#due-date').val(),
            conferenceId: $('#conference-select').val()
        })
    }).done(function (data) {
        console.log("SUCCESS");
        window.location.reload();
    }).fail(function (data) {
        console.log("ERROR");
    });
}

// a function to POST a new conference to the server
function submitConference() {
    let url = new URL('/api/newConference', window.location.origin);
    url.searchParams.append('conferenceName', $('#project-name').val());

    $('#students').find('input:not(:checked)').each(function () {
        url.searchParams.append('excludedStudents', $(this).attr('id'));
    });

    $.ajax({
        url: url.toString(), type: 'POST'
    }).done(function (data) {
        console.log("SUCCESS");
        window.location.reload();
    }).fail(function () {
        console.log("ERROR")
    });
}

function pageSelect() {
    const conf = document.getElementById("conf-select").value;
    const assignment = document.getElementById("assign-select").value;
    if (conf !== "default" && assignment != -1 && !$("#assign-select").attr("disabled")) {
        window.location.href = "/c/" + conf + "/a/" + assignment;
    } else if (conf !== "default") {
        window.location.href = "/c/" + conf;
    }
}

// a function to call the flag submission API
function toggleFlagged(submissionId) {
    $.ajax({
        url: '/api/toggleFlagged',
        type: 'POST',
        data: jQuery.param({
            submissionId: submissionId
        })
    }).done(function (data) {
        console.log("SUCCESS");
        window.location.reload();
    }).fail(function (data) {
        console.log("ERROR");
    });
}