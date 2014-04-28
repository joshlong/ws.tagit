$( function() {

	 
	 // make a call to our ajax service 
	 $(document).ready(function() {
	     $.ajax({
	         url: "http://localhost:8080/hi"
	     }).then(function(data, status, jqxhr) {
	     //   $('.greeting-id').append(data.id);
	       // $('.greeting-content').append(data.content);
	        console.log(jqxhr); 
	     });
	 });
   
	
});