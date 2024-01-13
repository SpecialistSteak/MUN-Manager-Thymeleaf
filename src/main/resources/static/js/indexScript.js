function submitAssignment() {
    console.log("submitAssignment");
}

function submitProject() {
    console.log("submitProject");
}

function pageSelect() {
    const conf = document.getElementById("conf-select").value;
    const assignment = document.getElementById("assign-select").value;
    if (conf !== "default" && assignment != -1) {
        window.location.href = "/c/" + conf + "/a/" + assignment;
    } else if (conf !== "default") {
        window.location.href = "/c/" + conf;
    }
}
