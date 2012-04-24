<html><head><title>Venture Funding List</title><meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.css" /><script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.2.min.js" type="text/javascript"></script><script src="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.js"></script>
<script type="text/javascript">
   var startRow = 0;
   var endRow = 29;

   function primaryContent( _startRow, _endRow ) {
    var queryString = './secondMarketFactData.jsp?queryName=scrollQuery&startRow='+_startRow+'&endRow='+_endRow;
    $.getJSON( queryString, function(data){
       var records = data.records;
       if ( records.length > 0 ) {
         var monthDateString = records[0].lastFundingDate;
         var pieces = monthDateString.split("-");
         var monthDate = new Date(pieces[0],pieces[1],pieces[2]);
         $("#primaryContent").children().each(function(){
           $(this).detach();
         });
         $("#primaryContent").append( $('<ul data-role="listview" id="companyListView" data-inset="true"></ul>') );
         $("#companyListView").append( $('<li data-role="list-divider">'+(monthDate.getMonth())+'/'+monthDate.getFullYear()+'</li>') );
         for ( var i=0; i<records.length; i++ ) {
           var lastFundingDateString = records[i].lastFundingDate;
           pieces = lastFundingDateString.split("-");
           var recordDate = new Date(pieces[0],pieces[1],pieces[2]);
         
           if ( recordDate.getMonth() != monthDate.getMonth() ) {
              monthDate = new Date(pieces[0],pieces[1],pieces[2]);
              $("#companyListView").append( $('<li data-role="list-divider">'+(monthDate.getMonth())+'/'+monthDate.getFullYear()+'</li>') );
           }

           $("#companyListView").append( $('<li id="li'+i+'"><a href="#dialog" data-transition="slide"><h3>'+records[i].companyName+'</h3><p style="font-weight:bold;">Raised: '+records[i].lastFundingAmount+'<br>Location: '+records[i].city+', '+records[i].state+'</p></a></li>') );                      
         }
         if ( _startRow > -1 ) {
           $("#primaryContent").append( $('<div data-role="controlgroup" data-type="horizontal" align="center"><a href="#" data-role="button" width="100%" id="last30">&nbsp;&nbsp;&nbsp;<< Last 30&nbsp;&nbsp;&nbsp;</a><a href="#" data-role="button" width="100%" id="next30">&nbsp;&nbsp;&nbsp;Next 30 >>&nbsp;&nbsp;&nbsp;</a></div>') ).trigger('create');
         }
         else {
           $("#primaryContent").append( $('<div data-role="controlgroup" data-type="horizontal" align="center"><a href="#" data-role="button" width="100%" id="next30">&nbsp;&nbsp;&nbsp;Next 30 >>&nbsp;&nbsp;&nbsp;</a></div>') ).trigger('create');
         }

         $("#next30").click(function(){
            startRow += 30;
            endRow += 30;
            primaryContent( startRow, endRow );
         });
         $("#last30").click(function(){
            startRow -= 30;
            endRow -= 30;
            primaryContent( startRow, endRow );
         });

         $("li").click(function(){
            var id = $(this).attr("id");
            if ( id.indexOf("li") > -1 ) {
              var index = id.substr(2,4);

              $("#dialogHeader").children().each(function(){
                 $(this).detach();
              });
              $("#dialogHeader").append( $('<div>'+records[index].companyName+'</div>') );

              $("#companyDataView").children().each(function(){
                 $(this).detach();
              });
              $("#companyDataView").append( $('<li><a href="#"><h2>Last Round: '+records[index].lastFundingAmount+'</h2></a></li>') );
              var lfdString = records[index].lastFundingDate;
              pieces = lfdString.split("-");
              var lastFundingDate = new Date(pieces[0],pieces[1],pieces[2]);
              $("#companyDataView").append( $('<li><a href="#"><h2>Last Funding Date: '+(lastFundingDate.getMonth())+"/"+lastFundingDate.getFullYear()+'</h2></a></li>') );    
              $("#companyDataView").append( $('<li><a href="#"><h2>Location: '+records[index].city+', '+records[index].state+'</h2></a></li>') );
              $("#companyDataView").append( $('<li><a href="'+records[index].secondMarketUrl+'"><h2>Second Market Profile</h2></a></li>') );
              $("#companyDataView").append( $('<li><a href="#"><h2>Unique Web Visitors: '+records[index].uniqueVisitors+'</h2></a></li>') );
              $("#companyDataView").append( $('<li><a href="#"><h2>Web Visitor Growth: '+records[index].visitorGrowth+'</h2></a></li>') );
              $("#companyDataView").append( $('<li><a href="'+records[index].companyUrl+'" data-transition="flip" target="_blank"><h2>Visit Company Website</h2></a></li>') );
              $("#companyDataView").listview("refresh");
            }
         });
         $("#companyListView").listview("refresh");
       }
    });
   }

  $(document).ready(function(){
    primaryContent( startRow, endRow );
  });
</script>
</head><body> 
<div data-role="page" data-theme="b">
 <div data-role="header">
   <h1>Recent VC Rounds</h1>
 </div>
 <div data-role="content">
   <div class="content-primary" id="primaryContent"></div>
 </div>
 <div data-role="footer">
   <h2>&copy; 2012 MktNeutral.com</h2>
 </div>
</div>
<div data-role="page" id="dialog" data-theme="b">
  <div data-role="header">
    <h1 id="dialogHeader"></h1>
  </div>
  <div data-role="content">
     <ul data-role="listview" id="companyDataView" data-inset="true"><ul>
  </div>
  <div data-role="footer">
    <h2>&copy; 2012 MktNeutral.com</h2>
  </div>
</div></body></html>