<%@ page import="com.Consumption"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
		<script src="Components/main.js"></script>
		
		<title>Consumption Details</title>
	</head>


	<body>
		<div class="container">
		 	<div class="row">
		 		<div class="col-8">
		 			<h1 class="m-3">Consumption details</h1>
		 			
		 			<!-- Form -->
			 			<form id="formConsumption" name="formConsumption" method="post" action="index.jsp">  
		
				 			Range Of Unit:  
							<input type="text" id="RangeOfUnit" name="RangeOfUnit" class="form-control form-control-sm">
							<br>
							
							Price: 
							<input type="text" id="Price" name="Price" class="form-control form-control-sm">
							<br>
							
							Date:
							<input type="text" id="Date" name="Date" class="form-control form-control-sm" >
							<br>
							
							Comments:   
							<input type="text" id="Comments" name="Comments" class="form-control form-control-sm">
							<br>
						
						
				
							<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
							<input type="hidden" id="hidConsumptionIDSave" name="hidConsumptionIDSave" value="">
							
								
						</form>
						
						
						<div id="alertSuccess" class="alert alert-success"> </div>
				    	<div id="alertError" class="alert alert-danger"></div>
				    	
				    	<br>
						<div id="divConsumptionGrid">
							<%
							    Consumption consumptionObj = new Consumption();
								out.print(consumptionObj.readconsumptions());
							%>
						</div>
		 		</div>
		 	</div>
		 	<br>
		 	
		 	
			
	 	</div>
	</body>

</html>