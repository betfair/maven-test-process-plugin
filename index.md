---
---
a

maven-test-process-plugin
=========================

Welcome to the maven-test-process-plugin for [Apache Maven 2](http://maven.apache.org).

This plugin makes it easy to run integration tests as part of your Maven build which require starting up your application as a separate command. It was originally written to support containers who's start scripts are blocking in nature, therefore preventing use of more standard pre- and post- integration test phase tasks.

Features
--------

* One or more target processes (known as containers).
* One or more test processes.
* Supports targets which run in a blocking manner.
* Configurable target start timeout as well as overall timeout.
* Watches output of target processes to detect start completion and fatal errors.
* Watches output of test processes to detect test success/failure.
* Can stop on first test failure or let all run to completion.

Usage
-----

Add the plugin to your pom's build section:

    <build>
        <plugins>
            ...
            <plugin>
                <groupId>com.betfair.plugins</groupId>
                <artifactId>maven-test-process-plugin</artifactId>
                <version>1.2-SNAPSHOT</version>
                <configuration>
                    <!-- Max time to allow for all containers to start -->
                    <containersStartupTimeout>120000</containersStartupTimeout>
                    <!-- Max time to allow all tests to take -->
                    <testsCompletionTimeout>120000</testsCompletionTimeout>
                    <!-- If set to 'false' then all test processes will be run, if 'true' (the default) then any failure will cause an immediate build failure -->
                    <failFast>false</failFast>
                    <containerProcesses>
                        <!-- You must have at least one containerProcess specified, you can have more if you want -->
                        <containerProcess>
                            <!-- Each container must have a unique id, if not specified then it has value 'default' -->
                            <id>web-server</id>
                            <!-- Command must be valid for the OS (required) -->
                            <command>mvn -Prun exec:java</command>
                            <!-- Working dirs may be relative (to maven's working dir) or absolute (required) -->
                            <workingDir>container</workingDir>
                            <!-- Regex for container failure, if not specified will rely on the process exit code -->
                            <failureWatchString>Exception</failureWatchString>
                            <!-- Regex for successful start, if not specified will rely on the process exit code -->
                            <startWatchString>Started in [0-9]*ms</startWatchString>
                            <!-- In case you want to pass environment properties into your command -->
                            <environmentProperties>
                                <key>value</key>
                            </environmentProperties>
                        </containerProcess>
                    </containerProcesses>
                    <testProcesses>
                        <!-- You must have at least one testProcess specified, you can have more if you want -->
                        <testProcess>
                            <!-- Each test process must have a unique id, if not specified then it has value 'default' -->
                            <id>httpsTests</id>
                            <command>mvn test</command>
                            <workingDir>http-tests</workingDir>
                            <!-- Allows you to specify a delay before starting -->
                            <startupDelay>30000</startupDelay>
                            <!-- Max time to allow for this test process to complete -->
                            <completionTimeout>120000</completionTimeout>
                            <!-- Regex for test failure, if not specified will rely on the process exit code -->
                            <failureWatchString>BUILD FAILED</failureWatchString>
                            <!-- Regex for test success, if not specified will rely on the process exit code -->
                            <watchString>BUILD SUCCESSFUL</watchString>
                            <environmentProperties>
                                <protocol>https</protocol>
                            </environmentProperties>
                        </testProcess>
                        <testProcess>
                            <id>httpTests</id>
                            <command>mvn test</command>
                            <workingDir>http-tests</workingDir>
                            <failureWatchString>BUILD FAILED</failureWatchString>
                            <watchString>BUILD SUCCESSFUL</watchString>
                            <environmentProperties>
                                <protocol>http</protocol>
                            </environmentProperties>
                        </testProcess>
                    </testProcesses>
                </configuration>
            </plugin>
            ...
		</plugins>
	</build>

Releases
--------

None as yet, although we do have a 1.2-SNAPSHOT floating around in the OSS Sonatype repository. Latest maven documentation can be found [here](master/maven).

Maven Repositories
------------------

Snapshots can be found in the [OSS Sonatype Snapshot repository](https://oss.sonatype.org/content/repositories/snapshots).

Releases will be made to the [OSS Sonatype Release repository](https://oss.sonatype.org/content/repositories/releases) which is synced to [Maven Central](http://repo1.maven.org/maven2).

Handily both of these are proxied by the following repository group:

    <pluginRepositories>
        <pluginRepository>
            <id>sonatype</id>
            <url>http://repository.sonatype.org/content/groups/public</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </pluginRepository>
    </pluginRepositories>
    
Mailing list
------------

Please direct any questions to [maven-test-process-plugin@googlegroups.com](mailto:maven-test-process-plugin@googlegroups.com).

You can checkout the archives [here](https://groups.google.com/forum/#!forum/maven-test-process-plugin).

Source access
-------------

Via Github: [https://github.com/betfair/maven-test-process-plugin](https://github.com/betfair/maven-test-process-plugin).

Licensing
---------

The maven-test-process-plugin is covered by "[The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)":

    Copyright 2013, The Sporting Exchange Limited
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
