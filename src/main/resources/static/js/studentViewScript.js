function currentStudentId() {
    return parseInt(window.location.href.substring(window.location.href.lastIndexOf('/') + 1));
}

function formUrl(studentId) {
    let currentUrl = window.location.href;
    return currentUrl.replaceAll(currentStudentId(), studentId);
}

function page(x) {
    $.ajax({
        type: "GET",
        url: "/api/newStudentURL",
        data: jQuery.param([
            {name: "studentId", value: currentStudentId()},
            {name: "requestType", value: x}
        ]),
    }).done(function (data) {
        document.location.replace(formUrl(data));
    })
}

function lastPage() {
    page(-1);
}

function nextPage() {
    page(1);
}