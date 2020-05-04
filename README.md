# Spartan Recreation

Spartan Recreation Application is a convenient way to do daily on-campus activities at San Jose State University. The Instructor is able to create a session for activities (like Yoga, Zumba, and Fitness). Further, the Coach is able to create the Team sessions (Like Basketball, Volleyball, Football, and Badminton). The Front Desk Assistant has authority for account approval. At last, the students must able to do the enrollment for the available sessions. He has the ability to drop the enrolled sessions as well. Once these activities happen in the system then the respective user will get notified via email.
 
Spartan Application is using MySQL 8.0.18 on wherein Spartan’s database exists with the name ‘cmpe226_spartan’. The triggers, procedures, and views are created using PL/SQL. It is a web application in which frontend is developed using ReactJS technology, Backend is Spring MVC. MongoDB database added as a logger of the system. It captures all the activities from the system and displays the numerical data about the traffic on certain API and the popularity of activities.


## Members 

   | Name                           | SJSU ID    |             Email ID                  | 
   |--------------------------------|------------|---------------------------------------|
   | **Nikhil Limaye**              | 013007644  |     nikhil.limaye@sjsu.edu            |
   | **Priya  Chaitanya Yadav**     | 013836719  |      priyachaitanya.yadav@sjsu.edu    | 
   | **Laxmikant Bhaskar Pandhare** | 013859989  |  laxmikantbhaskar.pandhare@sjsu.edu   |
   

## High Level Design Diagram
![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Design/architecture.png)

## Build Instructions

Two folders are there: 

#### Frontend: 

Goto-> frontend->Quora and run run npm install ( it will install all dependency for this application) 

then npm start in cmd. 

#### Backend: 

import backend folder in eclipse or IntelliJ IDE. 

Import all the dependeccies required to run spring boot applciation and run the application.

## Bonus Features

As part of the bonus features, We have decided to generate application logs using a NoSQL database like MongoDB. Each user action could have a different structure such as API, payload, activity etc. Such unstructured data can be stored in the form of documents in a MongoDB database collection. Each API hit generates a document with a timestamp in the mongo collection. These logs are only visible to the admin user and no one else. 

#### Guest and Host can give rating.

![alt text](https://github.com/kumarrishabh0289/airbnb/blob/laxmikant_new/images/Bonus%20Features.png)

![alt text](https://github.com/kumarrishabh0289/airbnb/blob/laxmikant_new/images/host%20side%20rating.png)

#### Implemented Google Map feature.

Further, we have integrated google map functionality in our application. Once the user enters the location, then its location gets displayed on the google location. Google Map shows for the location entered below. Once you click on view larger map, you can see below.

![alt text](https://github.com/kumarrishabh0289/airbnb/blob/laxmikant_new/images/Map.png)

## Application
A glimpse of the application

#### Admin

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Admin/Admin%20Dashboard.png)



