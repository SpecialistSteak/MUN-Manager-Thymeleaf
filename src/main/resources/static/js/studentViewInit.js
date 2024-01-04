$(document).ready(function () {
    $("#student-select").change(function () {
        let value = $(this).val();
        document.location.replace(formUrl(value))
    });
});