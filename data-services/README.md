# Data Services
Project Data Services exposes Database Tables and Views as a Rest service. 

The web application is built using a front controller design pattern. 
ControlServlet acts as the Front Controller and serves all the requests that start with /rest/*
ServiceBusinessFacade is the business class that validates the URL with the services configured in the xml. ALl the validations and security 
authorization can be handled in this class. 
ServiceFactory initializes the Services based on the XML files and maps all the URL's with the service Objects. 
Facade calls the servicDAO after all the validations are complete. THe ServiceDAO calls the View name that is configured in the Services.xml
Service DAO prepares the Json String based on the columns and records and returns to the controller.,
The Architecture is similar to Apache MiniLang that allows users to write services in an xml and application will invoke them. 

Services Can be defined in an XML file ex: 
Customer-Service.xml 

<services>

	<service name="getAllCustomers" table="CUSTOMERS">
	
		<http url="/customers" method="GET" return="json"/>
		
	</service>
	
</services>

Security can be added with a tag (Future Enhancement.)

	<auth role="ADMIN,..."/>

 
Currently the application performs a selet operation the database to fetch the records. These must be replaced with the view names. 
Apache Derby is used as the database itself. When the application starts, THe DAO checks if the Tables exists otherwise, it creates the Tables and 
loads the data from the text file into them. 

Once the application is deployed, Navigate to http://localhost:8080/data-services/rest/customers
This should return a json

{

"matches": [

{

"POSTALCODE": "12209",

"COUNTRY": "Germany",

"ADDRESS": "Obere Str. 57",

"CITY": "Berlin",

"CONTACTNAME": "Maria Anders",

"CUSTOMERNAME": "Alfreds Futterkiste",

"CUSTOMERID": "1"

}]}




