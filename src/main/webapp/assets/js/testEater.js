$(document).ready(function () {
    $('#testCreateFeedback').click(function () {

        let json =`{
            "Feedback_Id": ${getTimeStamp()},
            "Content": "ERTGHJK"
        }`;

        var url="FeedbackServlet?cmd=CREATE";
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            async:false,
            crossDomain: true,
            type: "POST",
            url: url,
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            data: json,
            success: function(data) {
                console.log(data);
            },
            error: function () {
                console.log("not thing");
            }
        })
    })

    $('#testSwitchDeliver').click(function () {

        var url="SwitchStatusServlet?userStatus=DELIVER_ON";
        $.ajax({
            // xhrFields: {
            //     withCredentials: true
            // },
            // async:false,
            // crossDomain: true,
            type: "POST",
            url: url,
            dataType: "json",
            success: function() {
                console.log("SUCCESS");
            },
            error: function () {
                console.log("FAIL");
            }
        })
    })
})

function getTimeStamp() {
    const dateTime = new Date().getTime();
    const timestamp = Math.floor(dateTime / 1000);
    return timestamp;
}



