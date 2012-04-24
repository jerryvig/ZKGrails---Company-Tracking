<html><head><title>Venture Funding List</title><meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.css" />
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.2.min.js " type="text/javascript"></script>
<script src="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
    $.getJSON('./secondMarketFactData.jsp?queryName=startupQuery', function(data){
       var records = data.records;
       if ( records.length > 0 ) {
         var monthDate = new Date(records[0].lastFundingDate);
         $("#companyListView").append( $('<li data-role="list-divider">'+(monthDate.getMonth()+2)+'/'+monthDate.getFullYear()+'</li>') );
         for ( var i=0; i<records.length; i++ ) {
           var recordDate = new Date(records[i].lastFundingDate);
           if ( recordDate.getMonth() != monthDate.getMonth() ) {
              monthDate = new Date(records[i].lastFundingDate);
              $("#companyListView").append( $('<li data-role="list-divider">'+(monthDate.getMonth()+2)+'/'+monthDate.getFullYear()+'</li>') );
           }

           $("#companyListView").append( $('<li id="li'+i+'"><a href="#"><h3>'+records[i].companyName+'</h3><p style="font-weight:bold;">Raised: '+records[i].lastFundingAmount+'<br>Location: '+records[i].city+', '+records[i].state+'</p></a></li>') );                      
         }
         $("li").click(function(){
            var id = $(this).attr("id");
            if ( id.indexOf("li") > -1 ) {
              var index = id.substr(2,1);
              
            }
         });
         $("#companyListView").listview("refresh");
       }
    });
  });
</script>
</head><body> 
<div data-role="page" data-theme="b">
 <div data-role="header">
   <h1>Venture Funding Company List</h1>
 </div>
 <div data-role="content">
   <div class="content-primary">
   <ul data-role="listview" id="companyListView">
    <!-- <li data-role="list-divider">April 2012</li>
    <li><a href="#">Sapphire Energy<span class="ui-li-count">$144,000,000</span></a></li>
     <li><a href="#">Skout<span class="ui-li-count">$22,000,000</span></a></li>
     <li data-role="list-divider">March 2012</li>
     <li><a href="#">Instagram <span class="ui-li-count">$80,000,000</span></a></li>
     <li><a href="#">Marin Software<span class="ui-li-count">$30,000,000</span></a></li> -->
   </div>
 </div>
 <div data-role="footer">
   <h1>&copy; 2012 MktNeutral.com</h1>
 </div>
</div>
</body>
</html>