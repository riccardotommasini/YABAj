YABAj
=====

Yet Another Buying Application (Java)

##Important funny note

The java implementation of our system is very easy to use and has a really nice interface.

##Working application
The application is available [here](http://yaba-dmw.appspot.com).

##Eclipse project configuration

1. Create a new [Slim3](https://sites.google.com/site/slim3appengine/) project, the root package is `it.polimi.yaba`.
2. Import `src` folder.
3. Import `war` folder.
4. Run the server.

In order to run locally the application, set to `true` the `session-enabled` field in `war/WEB-INF/appengine-web.xml` once you have created the Slim3 project.

## Initialise database

Start the server and visit `http://localhost:8888/init/`
