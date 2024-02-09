// this file will be used on clicking a student in the table to redirect to the student view page
$(document).ready(function () {
    $('.table').on('click', 'tbody tr', function (event) {
        if (!$(event.target).is('input[type="checkbox"]')) {
            const id = $(this).attr('id');
            if (id !== undefined) {
                window.location.href = '/studentView/' + id;
            }
        }
    });
})