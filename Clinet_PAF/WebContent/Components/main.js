/**
 * 
 */
 
 //show status of message- conroller page load
/*$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); */
 
//Hide the alerts on page load
$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});
// SAVE
 
// SAVE button (event handler) ==================================================================
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear previous alerts and message boxes 
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateConsumptionForm(); 
	
	//if not valid 
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
	

	
	// If valid------------------------  
	var type = ($("#hidConsumptionIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "ConsumptionAPI",   
			type : type,  
			data : $("#formConsumption").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onConsumptionSaveComplete(response.responseText, status);  
			} 
	}); 

	
	
});




//==========================================================================================================
function onConsumptionSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divConsumptionGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidConsumptionIDSave").val("");  
	$("#formConsumption")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidConsumptionIDSave").val($(this).closest("tr").find('#hidConsumptionIDUpdate').val());     
	$("#RangeOfUnit").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#Price").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#Date").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Comments").val($(this).closest("tr").find('td:eq(3)').text());   
	
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "ConsumptionAPI",   	//ServiceConsumption
		type : "DELETE",   
		data : "Consum_ID=" + $(this).data("consumptionid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onConsumptionDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 



function onConsumptionDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divConsumptionGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 










//CLIENT MODEL - VALIDATING FORM

//validateConsumptionForm() - validate data entered into the form
//it it has error it will give the error message
function validateConsumptionForm() 
{ 
 	//Validations 
 
 	// Range of unit
	if ($("#RangeOfUnit").val().trim() == "") 
	{ 
		return "Insert Range of Unit"; 
	}
	
	// Price
	//if ($("#Price").val().trim() == "") 
	//{ 
	//return "Insert Price"; 
	//}
	
	var tmpPrice = $("#Price").val().trim();
	if (!$.isNumeric(tmpPrice)) 
	 {
	 	return "Insert Price.";
	 }
	
	// Date
	if ($("#Date").val().trim() == "") 
	{ 
		return "Insert Date"; 
	}
	
	// Comment
	if ($("#Comments").val().trim() == "") 
	{ 
	return "Insert Comments"; 
	}

 return true; 
}

