$(document).ready(function () {
    // NOT ON THEAD
    $('.table').on('click', 'tbody tr', function () {
        const id = $(this).attr('id');
        window.location.href = '/studentView/' + id;
    });
})

$(document).ready(function () {
    $.ajax({
        url: "/api/conferenceName/" + $("#conf-select").val(),
        type: "GET",
        success: function (data) {
            $("#conference-title").text(data);
        }
    });
});