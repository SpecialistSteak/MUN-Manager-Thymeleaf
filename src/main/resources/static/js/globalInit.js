/*<![CDATA[*/
$(document).ready(function () {
    DataTable.datetime('h:mm a, MMMM dd yyyy');
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
    $('thead').removeClass('pointer');
});

$(document).ready(function () {
    $('#assign-select').prop("disabled", true);

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
    $.ajax({
        url: '/api/checkForSubmissions',
        type: 'GET'
    }).done(function (data) {
        console.log("SUBMISSIONS UPDATED");
    }).fail(function (data) {
        console.log("ERROR");
    });
}