
# filmsDB - VGAFilms
Web Application for administrate your films collection parsing FilmAffinity content and MKVToolNix Output to automate the process.

Application is developed under Grails Framework + JQuery + BootStrap

A test version is online at:
https://vgafilms.ayringenieros.synology.me

## Environment requirements
- Servlet container under Java Virtual Machine 1.7
- Relational Database (Mysql and MariaDB tested)
 
## Initial setup
- By default, the application checks if the folders to load flags and save images exists and are accessible. The path of these folders are configured by environment on the file *Config.groovy* with the following names:
  - film.defaultPostersFolder
  - film.defaultFlagsFolder

  You can modify the path of these folders without generate a new war saving the following values on DB on the table "setting":
  - name: "pathOfPosters", value: "${ThePathWhereYouWantToSavePosters}
  - name: "pathOfFlags", value: "${ThePathWhereYouWantPutFlags}

  If these values are saved on database, application ignores values configured on *Config.groovy*
- On first launch, the application will create the following data on DB:
  - Default Roles (Admin and User)
  - Default admin user with username: "admin" and password: "admin1234"
  - Default countrys
  - Default languages
