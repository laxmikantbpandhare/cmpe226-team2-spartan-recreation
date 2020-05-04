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

## Entity Relationship (ER) Diagram

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Design/ER.png)

## ER mapped into Relations

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Design/Schema.png)

## Database

##### Table Objects of an application.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Database%20procedure%20triggers%20views/table%20objects.png)


##### Procedures.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Database%20procedure%20triggers%20views/Procedures.png)


##### Triggers.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Database%20procedure%20triggers%20views/Triggers.png)

##### Views.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Database%20procedure%20triggers%20views/Views.png)


## Normalization

This relation is in 3NF.
    1NF : yes – has PK; 
          all non-PK attributes FD on PK; 
          attributes have single atomic value 
     2NF: 
          yes –1NF; 
          attributes FD on the entire PK (no partial FD)
     3NF: 
          yes –2NF; 
          there is no transitive dependency, attributes are directly FD on PK 


### Student

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/student.png)

### Session

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/session.png)

### Activity

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/activity.png)

### Coach

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/coach.png)

### Enrollment

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/enrollment.png)

### instructor

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/instructor.png)

### student_registration

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/student_registration.png)

### team

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/team.png)

### team_tryouts

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/team_tryouts.png)

### user

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/Normalization/user.png)


## Build Instructions

Two folders are there: 

#### Frontend: 

Goto-> frontend->Quora and run run npm install ( it will install all dependency for this application) 

then npm start in cmd. 

#### Backend: 

import backend folder in eclipse or IntelliJ IDE. 

Import all the dependeccies required to run spring boot applciation and run the application.

#### Database: 

Create database, procedure, views and triggers. And add the admin as a first entry into the table.

## Bonus Features

As part of the bonus features, We have decided to generate application logs using a NoSQL database like MongoDB. Each user action could have a different structure such as API, payload, activity etc. Such unstructured data can be stored in the form of documents in a MongoDB database collection. Each API hit generates a document with a timestamp in the mongo collection. These logs are only visible to the admin user and no one else. 


## Application
A glimpse of the application

### Admin

##### Admin able to see dashboard with applicationd etails and able to load the sample data into the application.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Admin/Admin%20Dashboard.png)

##### Admin able to see application logs.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Admin/Application%20Logs.png)


### Coach

##### Coach able to create the team tryoput session.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Coach/create%20team.png)

##### Email sent to Coach.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Coach/email%20for%20creation%20of%20tryout%20team.png)

##### List of tryout sessions created.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Coach/list%20of%20team%20tryouts.png)

##### Coach can approve or deny the requests from students.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Coach/requests%20from%20Students.png)

##### Email sent to students about Coach's decision.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Coach/email%20for.%20approve%20or%20reject%20decision.png)


### Instructor


##### Instructor can create the session.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Instructor/create%20session.png)

##### Email will be se3nt to instructor

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Instructor/email.jpeg)

##### Instructor Dashboard

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Instructor/instructor%20dashboard.png)


### Student Registaration with Front Desk Assistant


##### Student signup to the application.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student%20Registration%20and%20FDA/student%20signup.png)

##### Student tries to login but fails with below message.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student%20Registration%20and%20FDA/student%20login%20gets.%20%20error.png)

##### Front Desk Assistant gets that request from student.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student%20Registration%20and%20FDA/pending%20requets.%20with.%20FDA.png)

##### Front Desk Assistant approves it.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student%20Registration%20and%20FDA/After.%20Approval.png)

##### Email will be sent to student about approval.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student%20Registration%20and%20FDA/email%20to%20student.png)


### Student


##### Student searches for the session.

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/Search%20for%20session.png)


##### search resultts

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/search%20results.png)

##### enrolled session

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/display%20s%20elected.%20session.png)


##### email sent to student after enrollment

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/email%20for%20enroolment.png)


##### list of enrolled sessions

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/enrolled%20sessions.png)

##### Student can drop that session

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/can%20delete%20session.png)

##### After drop of a session

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/after%20delete.png)

##### Email will be sent about cancellation

![alt text](https://github.com/laxmikantbpandhare/cmpe226-team2-spartan-recreation/blob/master/Spartan/images/application/Student/email%20for%20enrollment%20cancellation.png)
