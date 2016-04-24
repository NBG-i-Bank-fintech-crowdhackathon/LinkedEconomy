# LinkedEconomy

#backend

##common
This package contains the classes which contain either SPARQL queries or other supportive methods.

##ethnikiGroup
This package contains the classes which are responsible for the creation of JSON files containing economical information regarding NBG group and its competitors (Piraeus Bank, Alpha Bank, Eurobank).

##fek
This package contains the classes which are responsible for the creation of JSON files containing information from Greek Government Gazette regarding NBG group.

##oracle
This package contains the classes which are responsible for the creation of JSON files containing economical information regarding Oracle group.

##oteGroup
This package contains the classes which are responsible for the creation of JSON files containing economical information regarding OTE group and its competitors (Vodafone, Forthnet, Cyta, Wind).

##social
This package contains the classes which are responsible for the creation of JSON files containing social information regarding OTE group and its competitors (Vodafone, Forthnet, Cyta, Wind) and NBG group and its competitors (Piraeus Bank, Alpha Bank, Eurobank).

![LinkedFin flow](https://www.dropbox.com/s/s2iz34gtv01pnyb/linkedFin.png?dl=1)

#frontend

##Controller
The controller includes the functions which we use in our project. For example, it receives the data from external JSON files using the http service and then passes the data to the views thanks to the scope. 

##Views
The folder of views consists of the files which contain the html code. In our case, it consists of the files OTE, NBG and ORACLE which contain the html code for the respective pages. The scope feeds the views with data from the controller.

##JSON
This package contains the classes which are responsible for the creation of JSON files containing information from Greek Government Gazette regarding NBG group.

![Dashboard example: OTE Group](https://www.dropbox.com/s/rjjagjmkkl37yyv/dashboard.png?dl=1)
![Dashboard example: OTE Group 2](https://www.dropbox.com/s/76b6ipq2ailpcai/dashboard%202.png?dl=1)
