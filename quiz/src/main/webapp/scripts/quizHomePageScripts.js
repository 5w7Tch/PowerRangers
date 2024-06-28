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
    let hist = sessionStorage.getItem('history');
    console.log(hist);
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

$(window).on('beforeunload', function () {
   if($(window).pendingRequests){
       $(window).pendingRequests.forEach(function (req) {
          req.abort(); 
       });
   } 
});

$(window).ajaxSend(function (event, jqXHR, options) {
    $(window).pendingRequests = $(window).pendingRequests || [];
    $(window).pendingRequests.push(jqXHR);
   jqXHR.always(function () {
       var index = $(window).pendingRequests.indexOf(jqXHR);
       if(index > -1){
           $(window).pendingRequests.splice(index, 1);
       }
   });
});

function confirmDelete(event) {
    event.preventDefault();

    var userConfirmed = confirm("Are you sure you want to Delete this Quiz?");

    if (userConfirmed) {
        document.getElementById("deleteForm").submit();
    }
}
function confirmClear(event) {
    event.preventDefault();

    var userConfirmed = confirm("Are you sure you want to clear this Quiz History?");

    if (userConfirmed) {
        document.getElementById("clearForm").submit();
    }
}