# Space-Travel-WebApp
Space travel themed WebApp developed using Spring framework.

Main Technologies used: Spring framework, MySQL8.0, Hibernate, Bootstrap.
Languages: HTML, CSS, Java, HQL.

This webapp is secured with the Spring Security module, and different users can do different things on the app:<br />
ADMINS: Can create new space trips, delete space trips, add trip descriptions, and edit tours.<br />
EMPLOYEES: Same rights as Admin, but they can't delete tours.<br />
CLIENTS: Don't have access to any of the functionalities related to creating, editing or adding trip descriptions.<br />
Clients can leave comments on trips, and sign up to them.<br />
Note that all of the passwords are automatically encrypted in the database.<br />
