function deliverOffline(deliver_status) {
	alert(deliver_status);
	var deliver_temp="";
	
				if(deliver_status==0)
				{
					deliver_temp=deliver_temp+'<img src="images/unlogin.svg" style="z-index:999;position:absolute;width:100%;" />';
					deliver_status=1;
				}
				else
				{
					alert("online");
					deliver_temp="";
					deliver_status=0;
				}
			document.getElementById("offlineImg").innerHTML = deliver_temp;
			//document.getElementById("content").innerHTML = deliver_temp;




        
    return deliver_status;
}

