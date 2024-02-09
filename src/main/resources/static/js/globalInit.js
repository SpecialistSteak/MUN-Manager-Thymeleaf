/*<![CDATA[*/
$(document).ready(function () {
    DataTable.datetime('h:mm a, MMMM dd yyyy');
    // initialize all tables as dataTables
    $('.table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 12,
        "info": false,
        "searching": true,
    });
    $('.paginate_button').addClass('dtableButton');
});
/*]]>*/

$(document).ready(function () {
    $('#assign-select').prop("disabled", true);

    // load assignments for selected conference
    // this is for when the conference selector is loaded to dynamically load assignments
    function loadAssignments(confId) {
        $.ajax({
            url: '/api/getAssignmentsByConference',
            type: 'GET',
            data: jQuery.param({confId: confId})
        }).done(function (data) {
            $('#assign-select').empty()
                .prop("disabled", false)
                .append('<option value="-1">' + " " + '</option>');
            for (let assignment of data) {
                let assignmentId = assignment.assignmentId;
                let assignmentName = assignment.assignmentName;
                if (assignmentId === null || assignmentName === null) {
                    assignmentError();
                } else {
                    $('#assign-select').append('<option value="' + assignmentId + '">' + assignmentName + '</option>');
                }
            }
        }).fail(function (data) {
            assignmentError(data.status);
        });
    }

    loadAssignments($('#conf-select').val());

    $('#conf-select').change(function () {
        loadAssignments($(this).val());
    });
});

function assignmentError(data) {
    console.log("ERROR");
    $('#assign-select')
        .empty()
        .prop("disabled", true);
    if (data != 400) {
        $('#assign-select').append('<option>' + "INTERNAL ERROR" + '</option>')
    }
}

function checkForSubmissions() {
    // function to call API.
    $.ajax({
        url: '/api/checkForSubmissions',
        type: 'GET'
    }).done(function (data) {
        console.log("SUBMISSIONS UPDATED");
    }).fail(function (data) {
        console.log("ERROR");
    });
}