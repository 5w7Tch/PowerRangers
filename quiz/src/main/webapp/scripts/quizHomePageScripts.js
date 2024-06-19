function retrievePersonData() {
    // Perform AJAX request to retrieve data
    let radios = document.getElementsByName('options');
    var selectedValue = null;
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            selectedValue = radios[i].value;
            break;
        }
    }
    var xhr = new XMLHttpRequest();
    let url ='/personData?orderBy='+selectedValue;
    xhr.open('post', url, true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            return JSON.parse(xhr.responseText); // Example: Output the person's age
        } else {
            console.error('Error fetching person data: ' + xhr.statusText);
        }
    };
    xhr.send();
}

function updateTable() {
    // Example data to update the table
    let newData = retrievePersonData();

    // Access the table body element
    var tableBody = document.getElementById("tableBody");

    // Clear existing rows
    tableBody.innerHTML = "";

    // Loop through the data and create rows
    newData.forEach((ROW) => {
        var row = tableBody.insertRow();
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.innerHTML = ROW.score;
        cell2.innerHTML = ROW.time;
        cell3.innerHTML = ROW.date;
    });
}