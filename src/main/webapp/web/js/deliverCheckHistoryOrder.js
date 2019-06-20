function deliverCheckHistoryOrder() {
	var historyOrder;
	$.ajax({
            url: "/ShowHistoryOrderServlet",
            type: "GET",
            dataType: "json",
            success: function (JData) {
                //alert("get");
                historyOrder = JData;
                alert(historyOrder);
                
            },
        
            error: function () {
                alert("無法取得歷史紀錄，請重新整理或稍後再試");
            }
        });
   
    OpenWindow=window.open("", "newwin", "height=250, width=250,toolbar=no,scrollbars="+scroll+",menubar=no");
   
    OpenWindow.document.write("<TITLE>配送紀錄</TITLE>");
   
    OpenWindow.document.write("<BODY BGCOLOR=#ffffff>");
   
    OpenWindow.document.write("<h1>Hello!</h1>");
   
    OpenWindow.document.write("New window opened!");
	
    OpenWindow.document.write("</BODY>");
   
    OpenWindow.document.write("</HTML>");
   
    OpenWindow.document.close();
   
}