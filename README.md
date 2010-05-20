Unplutofy
=========

Given a war file, unplutofy:

1. Removes all "servlet" elements that contain either "portlet-class" or a "param-name" element with value "portlet-name".
2. Removes all "servlet-mapping" elements that contain either "PlutoInvoker" or a "servlet-name" element with value of the "portlet-name" element as defined in portlet.xml.
3. Removes all "taglib" elements that contain "/portlet.tld".
4. Removes all portlet*.tld files from the war.

### Features

Support for calling service from Ant, Maven, command-line, or via API.

### Prerequisites for Build

Install [Maven 2][m2].

Install [Git][git].

### Clone the Project's Git Repository

Change directory to your favorite directory and clone the repo:

    git clone http://github.com/garysweaver/unplutofy.git

### Build

Run the build:

    mvn clean install

### Environment Variables for Command-line Usage

Set the UNPLUTOFY_HOME environment variable to the base directory of the project. For example, for OS X with bash, you might add the following to your .bash_profile and restart Terminal.app:

    export UNPLUTOFY_HOME=~/unplutofy

Test by doing the following in the project directory:

    cd bin
    unplutofy

### Command-line Usage

Back up the war file before attempting to use this. For example:

    cp (input_war) (input_war.bak)
    
Usage:

    unplutofy (input_war) (output_war)

### Maven 2 Plugin Usage

Add as a build plugin to your pom.xml:

            <plugin>
                <groupId>unplutofy</groupId>
                <artifactId>unplutofy</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <id>unplutofy a war</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>unplutofy</goal>
                        </goals>
                        <configuration>
                            <inputFile>some/path/to/input.war</inputFile>
                            <outputFile>some/path/to/output.war</outputFile>
                        </configuration>
                    </execution>
                </executions> 
            </plugin>

When it asks you to install it, install the jar using the command it provides using the jar under the target directory (assuming you built it), then build again.

(Maven mojo has not been tested at time of writing.)

### Ant Task Usage

Copy target/unplutofy(version).jar to Ant's lib dir somewhere so Ant can get to it. Then add this to your build.xml:

    <typedef name="unplutofy" classname="unplutofy.ant.task.UnplutofyTask"
             classpath="classes" loaderref="classes"/>
    
    <target name="unplutofy-war">
        <unplutofy inputFile="some/path/to/input.war" outputFile="some/path/to/output.war"/>
    </target>
    
(Ant task has not been tested at time of writing.)
    
### Troubleshooting

If you get:

    Exception in thread "main" java.lang.NoClassDefFoundError: unplutofy/commandline/UnplutofyCommand
    Caused by: java.lang.ClassNotFoundException: unplutofy.commandline.UnplutofyCommand

It is because you didn't build it yet or it can't find the jar because of the name or you aren't in the bin directory. See build instructions above.

### Warning

Read the code and understand what it does before executing it. Use these tools at your own risk.

### License

Copyright (c) 2010 Gary S. Weaver, released under the [MIT license][lic].


[lic]: http://github.com/garysweaver/unplutofy/blob/master/LICENSE
[git]: http://git-scm.com/
[m2]: http://maven.apache.org/
