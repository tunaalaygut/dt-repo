
$( document ).ready(function() {
    getAndPopulateMeetingRooms();
    meetingRoomCapacity = getRoomCapacity();
    addMember($("#currentUserId").val(), $("#currentUserFullName").val(), $("#currentUserEmail").val());
    document.querySelector("#datePicker").valueAsDate = new Date();
    drawTheGrid();
});

$("#buildingId").change(function(){
    getAndPopulateMeetingRooms();
});

$("#meetingRoomId").change(function (){
    meetingRoomCapacity = getRoomCapacity();
});

$(document).on('change', '.dateLocationInfo', function(){
    let date = $("#datePicker").val();
    let meetingRoomId = $("#meetingRoomId").val();
    reflectDataOnTheGrid(date, meetingRoomId);
});

function getAndPopulateMeetingRooms(){
    let buildingId = $("#buildingId").val();
    let meetingRoomSelect = $("#meetingRoomId");

    $.ajax({
        url: "/getBuildingMeetingRooms",
        data : {"buildingId" : buildingId}
    }).done(function(map) {
        meetingRoomSelect.empty();
        $.each(map, function(meetingRoomId, meetingRoomName)
        {
            meetingRoomSelect.append('<option value=' + meetingRoomId + '>' + meetingRoomName + '</option>');
        });
        let date = $("#datePicker").val();
        let meetingRoomId = $("#meetingRoomId").val();
        reflectDataOnTheGrid(date, meetingRoomId);
    });
}

function checkRoomCapacity(){
    let addedMemberCount = $('#example2').DataTable().rows().count();
    console.log(addedMemberCount + " people on the datatable.");

    if (addedMemberCount > meetingRoomCapacity){
        $("#capacityWarning").css("display", "block");
        $("#capacityWarning").text("Toplantı odasının kapasitesi " + meetingRoomCapacity + " kişi olarak belirlenmiştir. (" + addedMemberCount + " kişi eklendi)");
    }
    else
        $("#capacityWarning").css("display", "none");
}

function getRoomCapacity(){
    let meetingRoomId = $("#meetingRoomId").val();

    if(meetingRoomId !== ""){
        $.ajax({
            url: "/getMeetingRoomCapacity",
            data : {"meetingRoomId" : meetingRoomId}
        }).done(function(capacity) {
            meetingRoomCapacity = capacity;
            console.log(meetingRoomCapacity);
        });
    }

}

function addParticipant(memberId, fullName, email){
    let addedMembers = $("#addedMembersMultipleSelect");
    let markup =
        '<option value="'+ memberId +'" selected></option>' +
        '<option value="'+ fullName +'" selected></option>' +
        '<option value="'+ email +'" selected></option>';
    markup = $($.parseHTML(markup));

    addedMembers.append(markup);
}

function  removeParticipant(memberId, fullName, email) {
    let addedMembers = $("#addedMembersMultipleSelect");
    let emailOption = $('option[value="'+ email +'"]');
    let fullNameOption = emailOption.prev();
    let memberIdOption = fullNameOption.prev();

    emailOption.remove();
    fullNameOption.remove();
    memberIdOption.remove();
}

function transferParticipantInfo(memberId, fullName, email){
    let addedMembersDataTable = $('#example2').DataTable();
    let allMembersTable = $('#example').DataTable();
    let newMarkup;

    if(memberId){
        newMarkup = $($.parseHTML(
            '<tr>' +
            '<td>' + fullName + '</td>' +
            '<td>' + email + '</td>' +
            '<td class="text-center">' +
            '<button type="button" value0="'+ memberId +'" value1="'+ fullName +'" value2="'+ email +'" class="btn btn-sm btn-danger deleteParticipant">' +
            '<span class="fas fa-minus"></span>' +
            '</button>' +
            '</td>' +
            '</tr>'));
        let memberButton = $('button[value0='+ memberId +']');
        allMembersTable.rows(memberButton.parent().parent()).remove().draw();
    }
    else
        newMarkup = $($.parseHTML(
            '<tr>' +
            '<td>' + fullName + '<small class="text-muted"> (Misafir)</small></td>' +
            '<td>' + email + '</td>' +
            '<td class="text-center">' +
            '<button type="button" value0="" value1="'+ fullName +'" value2="'+ email +'" class="btn btn-sm btn-outline-danger deleteParticipant" style="border-radius: 50%;">' +
            '<span class="fas fa-minus"></span>' +
            '</button>' +
            '</td>' +
            '</tr>'));

    addParticipant(memberId, fullName, email);
    addedMembersDataTable.row.add(newMarkup).draw();			//update added members table
}

let meetingRoomCapacity;

$(document).on('click','.addMember', function() {
    let clickedMemberId = $(this).attr("value0");
    let memberName = $(this).attr("value1");
    let email = $(this).attr("value2");

    addMember(clickedMemberId, memberName, email);
});

function addMember(memberId, memberName, email){
    transferParticipantInfo(memberId, memberName, email);
    checkRoomCapacity();
}

$(document).on('click','#addGuest', function(){
    let guestFullNameInput = $("#guestFullNameInput");
    let guestEmailInput = $("#guestEmailInput");
    let guestFullName = guestFullNameInput.val();
    let guestEmail = guestEmailInput.val();


    if ((guestFullName !== "") && (guestEmail !== "")) {
        //Clear inputs in the add guest form.
        guestFullNameInput.val('');
        guestEmailInput.val('');

        transferParticipantInfo("", guestFullName, guestEmail);
    }
    checkRoomCapacity();
});

$(document).on('click', '#meetingRoomFilterDivButton', function(){
   let icon = $("#meetingRoomFilterDivButton svg");

   if(icon.hasClass("fa-sort-down"))
       icon.removeClass("fa-sort-down").addClass("fa-sort-up");
   else
       icon.removeClass("fa-sort-up").addClass("fa-sort-down");
});

$(document).on('click','.deleteParticipant', function(){
    let addedMembersDataTable = $('#example2').DataTable();
    let allMembersTable = $('#example').DataTable();

    let clickedRow = $(this).parent().parent();
    let memberId = $(this).attr('value0');
    let fullName = $(this).attr('value1');
    let email = $(this).attr('value2');

    console.log(memberId + " " + fullName + " " + email);

    if(memberId) {
        let newMarkup = $($.parseHTML(
            '<tr>' +
            '<td>' + fullName + '</td>' +
            '<td>' + email + '</td>' +
            '<td class="text-center">' +
            '<button type="button" value0="' + memberId + '" value1="' + fullName + '" value2="' + email + '" class="btn btn-sm btn-outline-info addMember" style="border-radius: 50%;">' +
            '<span class="fas fa-plus"></span>' +
            '</button>' +
            '</td>' +
            '</tr>'));

        allMembersTable.row.add(newMarkup).draw();
    }


    addedMembersDataTable.rows(clickedRow).remove().draw();
    removeParticipant(memberId, fullName, email);
    checkRoomCapacity();
});

function reflectDataOnTheGrid(date, meetingRoomId){
    $.ajax({
        url: "/getGridData",
        data : {
            "date" : date,
            "meetingRoomId" : meetingRoomId
        }
    }).done(function(map) {
        cleanTheGrid();
        $.each(map, function(startTime, endTime){
            markAsTakenInBetween(hourToInt(startTime), hourToInt(endTime));
        });
        $("#scheduleInfo").text("(" + $("#buildingId option[value=" + $("#buildingId").val() + "]").text() + " - " + $("#meetingRoomId option[value=" + $("#meetingRoomId").val() + "]").text() + " toplantı odasının " + $("#datePicker").val() + " tarihindeki programı gösteriliyor.)");
    });

}

function drawTheGrid(){
    let theGrid = $('#theGrid');

    for (let hour = 8; hour < 18; hour++){
        let row = $($.parseHTML('<div class="row"></div>'));
        for(minute  = 0; minute < 60; minute+=5){
            let column = $($.parseHTML('<div class="col-sm-1 p-0"></div>'));

            let time = "";

            time = (hour < 10 ? "0" + hour + ":" :  time + hour + ":");

            time += (minute < 10 ? "0" + minute : minute);

            let timeHolder = $($.parseHTML('<div class="timeHolder p-1 m-1 text-center rounded h-90 w-90" time="' + time + '" state="free"><small class="unselectable">' + time + '</small></div>'));

            column.append(timeHolder);
            row.append(column);
        }
        theGrid.append(row);
    }
}

let cleanTheGrid = function(){
    let takenTimeHolders = $('.timeHolder[state="taken"]');
    let selectedTimeHolders = $('.timeHolder[state="selected"]');
    markAsNotSelected(takenTimeHolders);
    markAsNotSelected(selectedTimeHolders);
    clicked1 = undefined;
    clicked2 = undefined;
    setTimeInputs(null, null);
}

let clicked1 = undefined, clicked2 = undefined;

$(document).on('click', '.timeHolder', function () {
    if($(this).attr("state") === "taken"){	//taken cell
        console.log("cant touch this bro.");
    }
    else{
        if(clicked1 === undefined){	//first click
            clicked1 = $(this);
            markAsSelected(clicked1);
        }
        else if(clicked2 === undefined){ //second click fill in between
            clicked2 = $(this);

            if(clicked1.is(clicked2)){	//same button is clicked
                markAsNotSelected(clicked1);
                clicked1 = undefined;
                clicked2 = undefined;
            }
            else{
                let time1 = clicked1.attr("time");
                let time2 = clicked2.attr("time");

                let time1value = hourToInt(time1);
                let time2value = hourToInt(time2);

                markAsSelectedInBetween(time1value, time2value);
            }
        }
        else{ //new selection, clear current selection
            let time1 = clicked1.attr("time");
            let time2 = clicked2.attr("time");

            let time1value = hourToInt(time1);
            let time2value = hourToInt(time2);

            markAsNotSelectedInBetween(time1value, time2value);
            clicked1 = $(this);
            let newClickedValue = hourToInt(clicked1.attr("time"));

            if ((newClickedValue >= time1value && newClickedValue <= time2value) || (newClickedValue <= time1value && newClickedValue >= time2value)){	//inside current selection
                clicked1 = undefined;
            }
            else{
                markAsSelected(clicked1);
            }

            clicked2 = undefined;
        }
    }

});

function hourToInt(time){	//takes an hour string "hh:mm", returns the corresponding integer
    let parts = time.split(":");
    let hour = parseInt(parts[0]);
    let minute = parseInt(parts[1]);

    return ((hour*60) + minute)-480;
}

function intToHour(x) {
    let timeString = "";
    x += 480;
    let hour = Math.floor(x/60);
    let minute = x%60;

    timeString = (hour < 10 ? "0" + hour + ":" :  timeString + hour + ":");

    timeString += (minute < 10 ? "0" + minute : minute);

    return timeString;
}

function markAsSelectedInBetween(value1, value2) {
    if(value2 < value1){
        let temp = value1;
        value1 = value2;
        value2 = temp;
        let tempClick = clicked1;
        clicked1 = clicked2;
        clicked2 = tempClick;
    }

    let prev, current;

    for (let i = value1; i <= value2; i+=5){
        prev = current;
        current = $('.timeHolder[time="'+ intToHour(i) +'"]');
        if(current.attr("state") !== "taken")
            markAsSelected(current);
        else{
            value2 = i-5;
            markAsNotSelected(clicked2);
            clicked2 = prev;
            break;
        }

    }

    setTimeInputs(intToHour(value1), intToHour(value2));
}

function markAsNotSelectedInBetween(value1, value2) {
    if(value2 < value1){
        let temp = value1;
        value1 = value2;
        value2 = temp;
    }

    for (let i = value1; i <= value2; i+=5){
        let current = $('.timeHolder[time="'+ intToHour(i) +'"]');
        markAsNotSelected(current);
    }

    setTimeInputs(null, null);
}

function markAsTakenInBetween(value1, value2) {
    if(value2 < value1){
        let temp = value1;
        value1 = value2;
        value2 = temp;
    }

    for (let i = value1; i <= value2; i+=5){
        let current = $('.timeHolder[time="'+ intToHour(i) +'"]');
        markAsTaken(current);
    }
}

function setTimeInputs(startTime, endTime){
    $("#startTimeInput").val(startTime);
    $("#endTimeInput").val(endTime);
}

function markAsSelected(element){
    element.css("background", "#17a2b8");
    element.attr("state", "selected");
}

function markAsNotSelected(element){
    element.css("background", "#E4E4E4");
    element.attr("state", "free");
}

function markAsTaken(element){
    element.css("background", "#F08080");
    element.attr("state", "taken");
}
