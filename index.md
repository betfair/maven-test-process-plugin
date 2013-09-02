---
---
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

* [Latest release](master/usage.html).

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
