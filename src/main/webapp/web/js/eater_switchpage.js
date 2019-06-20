function switchpage(input)
{
    if(input=="cart")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='on';
        document.getElementById("main").style.display='none';
        document.getElementById("extra").style.display='none';

    }
    else if(input=="bill")
    {
        document.getElementById("bill").style.display='on';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='none';
        document.getElementById("extra").style.display='none';


    }
    else if(input=="rest")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='on';
        document.getElementById("extra").style.display='on';


    }
    else if(input=="menu")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='on';
        document.getElementById("extra").style.display='on';


    }
}