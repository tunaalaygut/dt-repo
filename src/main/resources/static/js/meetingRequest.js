
$( document ).ready(function() {
    getAndPopulateMeetingRooms();
    meetingRoomCapacity = getRoomCapacity();
    addMember($("#currentUserId").val(), $("#currentUserFullName").val(), $("#currentUserEmail").val(), $("#currentUserParticipantType"));
    document.querySelector("#datePicker").valueAsDate = new Date();
    drawTheGrid();
    $('[data-toggle="tooltip"]').tooltip();
    loadRoomFeatures();
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
    loadMeetingRoomProperties();
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
        loadMeetingRoomProperties();
        reflectDataOnTheGrid(date, meetingRoomId);
    });
}

function checkRoomCapacity(){
    let addedMemberCount = $('#example2').DataTable().rows().count();

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

function loadRoomFeatures(){
    let filterRoomFeatureSelect = $("#filterRoomFeatureSelectMenu");
    filterRoomFeatureSelect.empty();

    $.ajax({
        url: "/loadAllFeatures"
    }).done(function(map) {
        $.each(map, function (roomFeatureId, roomFeatureName) {

            let markup = "<option value='"+ roomFeatureId +"'>"+ roomFeatureName +"</option>";
            markup = $($.parseHTML(markup));
            filterRoomFeatureSelect.append(markup);
        });
    });
}

function addParticipant(memberId, fullName, email, participantType){
    let addedMembers = $("#addedMembersMultipleSelect");
    let markup =
        '<option value="'+ memberId +'" selected></option>' +
        '<option value="'+ fullName +'" selected></option>' +
        '<option value="'+ email +'" selected></option>' +
        '<option value="'+ participantType +'" selected></option>';
    markup = $($.parseHTML(markup));1

    addedMembers.append(markup);
}

function  removeParticipant(memberId, fullName, email, participantType) {
    let participantOption = $('option[value="'+ participantType +'"]');
    let emailOption = participantOption.prev();
    let fullNameOption = emailOption.prev();
    let memberIdOption = fullNameOption.prev();

    participantOption.remove();
    emailOption.remove();
    fullNameOption.remove();
    memberIdOption.remove();
}

//Filtre özelliği
/*$(document).on('click', '#applyRoomFilter', function(){
    let capacityInput = $("#filterCapacityInput").val();
    let roomFeatureInput = $("#filterRoomFeatureSelectMenu option:selected");
    let selectedFeatureIds = [];
    let meetingRoomSelect = $("#meetingRoomId");

    $.each(roomFeatureInput, function(){
        selectedFeatureIds.push($(this).val());
    });

    $.ajax({
        url: "/filterMeetingRooms",
        data: {"capacity": capacityInput}
    }).done(function(map){
        meetingRoomSelect.empty();
        $.each(map, function(meetingRoomId, meetingRoomName){
            meetingRoomSelect.append('<option value=' + meetingRoomId + '>' + meetingRoomName + '</option>');
        });

    });
});*/

function transferParticipantInfo(memberId, fullName, email, participantType){
    let addedMembersDataTable = $('#example2').DataTable();
    let allMembersTable = $('#example').DataTable();
    let newMarkup;

    if(memberId){
        newMarkup = $($.parseHTML(
            '<tr>' +
            '<td>' + fullName + '</td>' +
            '<td>' + email + '</td>' +
            '<td>' + participantType + '</td>' +
            '<td class="text-center">' +
            '<button type="button" value0="'+ memberId +'" value1="'+ fullName +'" value2="'+ email +'" value3="'+ participantType +'" class="btn btn-sm btn-danger deleteParticipant">' +
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
            '<button type="button" value0="" value1="'+ fullName +'" value2="'+ email +'" value3="'+ "" +'" class="btn btn-sm btn-danger deleteParticipant">' +
            '<span class="fas fa-minus"></span>' +
            '</button>' +
            '</td>' +
            '</tr>'));

    addParticipant(memberId, fullName, email, participantType);
    addedMembersDataTable.row.add(newMarkup).draw();			//update added members table
}

let meetingRoomCapacity;

$(document).on('click','.addMember', function() {
    let clickedMemberId = $(this).attr("value0");
    let memberName = $(this).attr("value1");
    let email = $(this).attr("value2");
    let participantType = $(this).attr("value3");

    addMember(clickedMemberId, memberName, email, participantType);
});

function addMember(memberId, memberName, email, participantType){
    transferParticipantInfo(memberId, memberName, email, participantType);
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
    let participantType = $(this).attr('value3');

    console.log(memberId + " " + fullName + " " + email + " " + participantType);

    if(memberId) {
        let newMarkup = $($.parseHTML(
            '<tr>' +
            '<td>' + fullName + '</td>' +
            '<td>' + email + '</td>' +
            '<td>' + participantType + '</td>' +
            '<td class="text-center">' +
            '<button type="button" value0="' + memberId + '" value1="' + fullName + '" value2="' + email + '" value3="' + participantType +'"  class="btn btn-sm btn-info addMember">' +
            '<span class="fas fa-plus"></span>' +
            '</button>' +
            '</td>' +
            '</tr>'));

        addedMembersDataTable.rows(clickedRow).remove().draw();
        allMembersTable.row.add(newMarkup).draw();
    }

    removeParticipant(memberId, fullName, email, participantType);
    checkRoomCapacity();
});

function reflectDataOnTheGrid(date, meetingRoomId){
    $.ajax({
        url: "/getGridData",
        data : {
            "date" : date,
            "meetingRoomId" : meetingRoomId
        }
    }).done(function(meetingDetails) {
        cleanTheGrid();

        $.each(meetingDetails, function (index, meeting) {
            markAsTakenInBetween(hourToInt(meeting.beginningTime), hourToInt(meeting.endTime), meeting);
        });

        $("#scheduleInfo").text("(" + $("#buildingId option[value=" + $("#buildingId").val() + "]").text() + " - " + $("#meetingRoomId option[value=" + $("#meetingRoomId").val() + "]").text() + " toplantı odasının " + $("#datePicker").val() + " tarihindeki programı gösteriliyor.)");
    });
}

function loadMeetingRoomProperties(){
    let meetingRoomId = $("#meetingRoomId").val();
    let roomPropertiesList = $("#roomPropertiesList");
    $.ajax({
        url: "/loadMeetingRoomProperties",
        data : {
            "meetingRoomId" : meetingRoomId
        }
    }).done(function(properties) {
        roomPropertiesList.empty();
        let size = properties.length;
        console.log(size);
        $.each(properties, function(index, property){
            let markup = "";
            if(index === size-1)
                markup = "<li>" + property + " kişi kapasitesi</li>";
            else
                markup = "<li>" + property + "</li>";
            roomPropertiesList.append(markup);
        });

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
        let clusterBegin = $(this).attr("cluster-begin");
        let clusterEnd = $(this).attr("cluster-end");
        let owner = $(this).attr("ownerid");
        let requestForm = $("#requestMeeting");

        clicked1 = undefined;
        clicked2 = undefined;

        markAsNotSelected($("[state='selected']"));

        swal({
            title: clusterBegin + " - " + clusterEnd + " saatlerini talep etmek istediğinize emin misiniz?",
            text: "İsteğiniz direkt olarak şu anki toplantı sahibine gönderilecektir.",
            icon: "warning",
            buttons: true,
            buttons: ["Vazgeç", "Evet"],
            dangerMode: true,
        }).then((yes) => {
            if (yes) {
                setTimeInputs(clusterBegin, clusterEnd);
                //set recordId from meetingDetails
                $("#requestMadeTo").val(owner);
                requestForm.attr("action", "/requestMeetingFromUser");
                requestForm.submit();
            }
        });
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

function markAsTakenInBetween(value1, value2, meetingDetail) {
    if(value2 < value1){
        let temp = value1;
        value1 = value2;
        value2 = temp;
    }

    for (let i = value1; i <= value2; i+=5){
        let current = $('.timeHolder[time="'+ intToHour(i) +'"]');
        markAsTaken(current, meetingDetail);
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

    element.attr("data-toggle", "");
    element.attr("data-placement", "");
    element.attr("title", "");
    element.css("cursor", "pointer");
    element.attr("cluster-begin", "");
    element.attr("cluster-end", "");
    element.attr("ownerId", "");
}

function markAsTaken(element, meetingDetail){
    element.css("background", "#F08080");
    element.attr("state", "taken");

    element.attr("data-toggle", "tooltip");
    element.attr("data-placement", "bottom");
    element.attr("title", getMeetingDetailString(meetingDetail));
    element.attr("cluster-begin", meetingDetail.beginningTime);
    element.attr("cluster-end", meetingDetail.endTime);
    element.attr("ownerId", meetingDetail.memberId);
}

function getMeetingDetailString(meetingDetail) {
    let detailString  = "";

    detailString += (meetingDetail.member + " tarafından " + meetingDetail.meetingType + " amacıyla ayırtıldı. Bu toplantıyı talep etmek için tıklayın.");

    return detailString;
}

$(document).on('mouseenter', '[state="taken"]', function(){
    let hovered = $(this);
    highlightCluster(hourToInt(hovered.attr("cluster-begin")), hourToInt(hovered.attr("cluster-end")));
});

$(document).on('mouseleave', '[state="taken"]', function(){
    let hovered = $(this);
    normalizeCluster(hourToInt(hovered.attr("cluster-begin")), hourToInt(hovered.attr("cluster-end")));
});

function highlightCluster(beginning, end){
    fillInterval(beginning, end, "#c980f0");
}

function normalizeCluster(beginning, end){
    fillInterval(beginning, end, "#F08080");
}

function fillInterval(beginning, end, color){
    let element;
    for(let i = beginning; i <= end; i+=5){
        element = $('[time="'+ intToHour(i) +'"]');
        element.css("background", color);
    }
}

datePicker.min = new Date().toISOString().split("T")[0]; //datepicker'i bugun ve gelecek olarak kisitlama