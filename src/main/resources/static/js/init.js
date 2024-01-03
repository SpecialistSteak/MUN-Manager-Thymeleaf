/*<![CDATA[*/
$(document).ready(function () {
    DataTable.datetime('MMMM dd, yyyy hh:mm a');
    $('.table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 12,
        "info": false,
        "searching": true
    });
});
/*]]>*/

$(document).ready(function () {
    $('.table').on('click', 'tr', function () {
        const id = $(this).attr('id');
        console.log(id);
    });
})

$(document).ready(function () {
    $('#assign-select').prop("disabled", true);
    $('#conf-select').change(function () {
        const confId = $(this).val();
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
            console.log(data.status + ": " + data.responseText);
            assignmentError();
        });
    });
});


function assignmentError() {
    console.log("ERROR");
    $('#assign-select')
        .empty()
        .append('<option>' + "INTERNAL ERROR" + '</option>')
        .prop("disabled", true);
}
