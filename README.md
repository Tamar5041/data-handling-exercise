# data-handling-exercise

The project deals with Big Data processing and was created for an exercise on behalf of the Secupi company.
The following processes take place in the project:
1. Data creation: Create a list with 10 million records, each record contains the fields:
   Timestamp: DataSubjectId: ViewerID
2. Save the data in kafka
3. Reading the data from kafka
4. Data Processing: Estimate how many unique DataSubjectIds each viewer have seen. The calculation is performed using the HyperLogLog algorithm.
5. Save the results in MongoDB
6. Display the results on Html page
In order to run the project, the following steps are required:
1. Download the project from git-hub
2. Run kafka & mongo servers in the local environment
   (Docker can be used to easily run the servers, 
   In the file https://storage.googleapis.com/secupi/secupi-exercise-env.tgz there are settings ready to run kafka & mongo by Docker)
3. Run the main method in the project. And results will be displayed in an html page in your local browser
