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
public class TestProcess {

	/**
     * Id of this test process, must be unique.
	 * @parameter default-value='default'
	 */
	private String id;

	/**
     * The command to execute this test process
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
     * Time to wait prior to starting test process. If not specified there will be no delay.
	 * @parameter
	 */
	private String startupDelay;

	/**
     * The maximum time allowed for this test process to complete. If not specified there will be no limit.
	 * @parameter
	 */
	private String completionTimeout;

    /**
     * Text to look for in the process output to denote that the process has failed.
     * @parameter
     */
    private String failureWatchString;

    /**
     * Text to look for in the process output to denote that the process has succeeded.
     * @parameter
     */
    private String watchString;

    /**
     * Environment properties to set when starting the process.
     * @parameter
     */
    private Map<String,String> environmentProperties;

    // used by maven
    public TestProcess() {
    }

    public TestProcess(String id, String command, String workingDir, String startupDelay, String completionTimeout, String failureWatchString, String watchString, Map<String,String> environmentProperties) {
        this.id = id;
        this.command = command;
        this.workingDir = workingDir;
        this.startupDelay = startupDelay;
        this.completionTimeout = completionTimeout;
        this.failureWatchString = failureWatchString;
        this.watchString = watchString;
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

    public String getStartupDelay() {
        return startupDelay;
    }

    public String getCompletionTimeout() {
        return completionTimeout;
    }

    public String getFailureWatchString() {
        return failureWatchString;
    }

    public String getWatchString() {
        return watchString;
    }
}
