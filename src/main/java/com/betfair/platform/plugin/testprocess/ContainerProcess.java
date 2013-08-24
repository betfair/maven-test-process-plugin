/*
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
 */
package com.betfair.platform.plugin.testprocess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ContainerProcess {
	/**
     * Id for this container process
     * @parameter default-value='default'
	 */
	private String id;

	/**
     * The command to execute this container process
	 * @parameter
     * @required
	 */
	private String command;

	/**
     * The working directory in which to run the command. This is relative to the directory the original maven command was run from.
	 * @parameter
     * @required
	 */
	private String workingDir;
   /**
    * Text to look for in the process output to denote that the process has failed.
    * @parameter
    */
   private String failureWatchString;
   /**
    * Text to look for in the process output to denote that the process has started.
    * @parameter
    */
   private String startWatchString;

    /**
     * Environment properties to set when starting the process.
     * @parameter
     */
    private Map<String,String> environmentProperties;

    // used by maven
    public ContainerProcess() {
    }

    public ContainerProcess(String id, String command, String workingDir, String failureWatchString, String startWatchString, Map<String, String> environmentProperties) {
        this.id = id;
        this.command = command;
        this.workingDir = workingDir;
        this.failureWatchString = failureWatchString;
        this.startWatchString = startWatchString;
        this.environmentProperties = environmentProperties;
    }

    public ProcessBuilder createProcessBuilder() {
        String[] testProcessArgs = command.split(" ");
        // deal with paths with a space in them, anything "quoted" will be merged into a single arg
        List<String> args = new ArrayList<String>();
        boolean inQuotes = false;
        String mergedItem = "";
        for (String s : testProcessArgs) {
            // we'll always start like this
            if (!inQuotes) {
                // if we've found the start of the quotes, then start the command
                if (s.startsWith("\"")) {
                    mergedItem = s;
                    inQuotes = true;
                }
                // normally we'll just add the arg to the list
                else {
                    args.add(s);
                }
            }
            // if we've found an item starting with a quote...
            else {
                // add the item to the end
                mergedItem += " " + s;
                // check for exit clause
                if (s.endsWith("\"")) {
                    args.add(mergedItem);
                    inQuotes = false;
                }
            }
        }
        // if we're still in quotes then someone's forgotten to finish them...
        if (inQuotes) {
            throw new IllegalStateException("Found unmatched quotes in string: "+mergedItem);
        }
        // now we can safely build the command
        ProcessBuilder testProcessBuilder = new ProcessBuilder(args);
        if (environmentProperties != null) {
            testProcessBuilder.environment().putAll(environmentProperties);
        }
        testProcessBuilder.redirectErrorStream(true);
        testProcessBuilder.directory(new File(workingDir));
        return testProcessBuilder;
    }

    public String getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public String getFailureWatchString() {
        return failureWatchString;
    }

    public String getStartWatchString() {
        return startWatchString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerProcess)) return false;

        ContainerProcess that = (ContainerProcess) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
