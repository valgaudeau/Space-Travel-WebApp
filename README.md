# Space-Travel-WebApp
Space travel themed WebApp developed using Spring framework.

<b>Main Technologies used</b>: Spring framework, MySQL8.0, Hibernate, Bootstrap.<br />
<b>Languages</b>: HTML, CSS, Java, HQL.

This webapp is secured with the <b>Spring Security module</b>, and different users can do different things on the app:<br />
<b>ADMINS</b>: Can create new space trips, delete space trips, add trip descriptions, and edit tours.<br />
<b>EMPLOYEES</b>: Same rights as Admin, but they can't delete tours.<br />
<b>CLIENTS</b>: Don't have access to any of the functionalities related to creating, editing or adding trip descriptions.<br />
Clients can leave comments on trips, and sign up to them.<br />
Note that all of the passwords are automatically encrypted in the database.<br />
