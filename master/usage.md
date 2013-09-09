---
layout: default
title: Usage: maven-test-process-plugin
---
Usage Notes
-----------

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

Maven doco
----------

Latest maven documentation can be found [here](maven).

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

Wibble
