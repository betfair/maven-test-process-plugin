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

import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class ReaderRunnable implements Runnable {
    private BufferedReader br;
    private Log log;
    private String id;
    private Pattern notifyPattern;
    private Pattern failureNotifyPattern;
    private ProcessLauncherMojo processLauncherMojo;

    public ReaderRunnable(String id, InputStream r, Log log) {
        InputStreamReader isr = new InputStreamReader(r);
        this.br = new BufferedReader(isr);
        this.log = log;
        this.id = id;
    }

    public void setNotifyText(String text) {
        notifyPattern = Pattern.compile(text);
    }

    public void setFailureNotifyText(String text) {
        failureNotifyPattern = Pattern.compile(text);
    }

    public void run() {
        while (true) {
            String data;
            try {
                data = br.readLine();
                if (data == null) {
                    break;
                }
                log.info(id + ":" + data);
                if (notifyPattern != null && notifyPattern.matcher(data).find()) {
                    processLauncherMojo.notifyTextArrived(id);
                }
                if (failureNotifyPattern != null && failureNotifyPattern.matcher(data).find()) {
                    processLauncherMojo.failureNotifyTextArrived(id);
                }

            } catch (IOException e) {
                // ignore
            }
        }
    }

    public void setListener(ProcessLauncherMojo processLauncherMojo) {
        this.processLauncherMojo = processLauncherMojo;
    }
}
