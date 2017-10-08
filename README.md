# Humanity_Hacks
Team 7:

Description: Application that allows users to create local beacons over bluetooth that broadcast messages to all phones in a small radius (100m approx). This app is targeted towards disaster relief efforts, since it allows individuals to advertise and require assistance in areas where it may be difficult to coordinate help. This functionality is achieved through the Messages API of the Google Nearby API, which allows easy use of bluetooth and wifi to broadcast messages to nearby phones. This is wrapped in an android application, which is built using the Java Android SDK. 

In addition, a backend was setup in Spring Boot that connects to a MongoDB database. This is used to allow the Android application to store the messages on a database whenever a connection is able to be used. When a user is in a stable connection, the user can then view these queries and, say, give a thank you to the person of assistance.

Deployment:
The Humanity_hacks folder contains the Android application -- simply build gradle and run.
The Hacks_For_humanity contains the Spring Boot backend, which can be compiled on localhost through Maven.

**Pull from github to access the submodule 
