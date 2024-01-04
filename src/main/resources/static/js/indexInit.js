$(document).ready(function () {
    // NOT ON THEAD
    $('.table').on('click', 'tbody tr', function () {
        const id = $(this).attr('id');
        window.location.href = '/studentView/' + id;
    });
})