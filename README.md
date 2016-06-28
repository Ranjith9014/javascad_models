# JavaSCAD models

##### Examples for JavaSCAD

This repository contains examples for [JavaSCAD](https://github.com/printingin3d/javascad). Every example generate one or more files to the output directory, which is "C:\temp" by default.

##Building the project

The project is a standard Maven project, which means you can build it with the standard ```mvn clean package``` command.

##Run the examples

You can run the projects individually by issuing the correct ```java -jar models-1.0.0.0.jar <name-of-the-example-class>``` command. You can run them from your favourite IDE too. You can give the output directory as an argument the usual way if you want to change the default ```C:\temp```.

Or you can run all of them by running from Mavan: ```mvn clean install```. You can change the output directory this way too by setting the ```exec.args variable```. Eg: ```mvn clean install -Dexec.args="c:/temp/temp"```.
