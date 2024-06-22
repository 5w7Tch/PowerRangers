function update() {

    let radios = document.getElementsByName('options');
    var selectedValue = null;
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            selectedValue = radios[i].value;
            break;
        }
    }
    let url = '/personData?orderBy=' + encodeURIComponent(selectedValue);
    let result;
    fetch(url, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            updateTable(data);
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
    return result;
}

function updateTable(newData) {
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
        cell1.innerHTML = ROW.scoreString;
        cell2.innerHTML = ROW.timeString;
        cell3.innerHTML = ROW.dateString;
    });
}