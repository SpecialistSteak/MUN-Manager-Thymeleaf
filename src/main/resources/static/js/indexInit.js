$(document).ready(function () {
    $('.table').on('click', 'tbody tr', function () {
        const id = $(this).attr('id');
        if (id === undefined) {
            return;
        }
        window.location.href = '/studentView/' + id;
    });
})