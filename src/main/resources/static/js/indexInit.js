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