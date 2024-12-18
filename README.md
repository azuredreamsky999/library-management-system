Library Management System
--------------------------------------------

How to run : 
--------------------------------------------

mvn clean package -DskipTests

docker build -t library-management .

docker run -p 8080:8080 library-management

--------------------------------------------
Please refer to Book.postman_collection.json for Postman query
