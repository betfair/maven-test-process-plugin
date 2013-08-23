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

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestTestProcess {

    @Test
    public void testUnquoted() {
        TestProcess tp = new TestProcess("id", "mvn clean package", System.getProperty("user.home"), "1000", "10000", "FAIL", "Done", null);
        ProcessBuilder pb = tp.createProcessBuilder();
        List<String> args = pb.command();
        assertEquals(3, args.size());
        assertEquals("mvn", args.get(0));
        assertEquals("clean", args.get(1));
        assertEquals("package", args.get(2));
    }

    @Test
    public void testQuotedExecutable() {
        TestProcess tp = new TestProcess("id", "\"wibble mvn\" clean package", System.getProperty("user.home"), "1000", "10000", "FAIL", "Done", null);
        ProcessBuilder pb = tp.createProcessBuilder();
        List<String> args = pb.command();
        assertEquals(3, args.size());
        assertEquals("\"wibble mvn\"", args.get(0));
        assertEquals("clean", args.get(1));
        assertEquals("package", args.get(2));
    }

    @Test
    public void testQuotedArg() {
        TestProcess tp = new TestProcess("id", "mvn \"clean package\"", System.getProperty("user.home"), "1000", "10000", "FAIL", "Done", null);
        ProcessBuilder pb = tp.createProcessBuilder();
        List<String> args = pb.command();
        assertEquals(2, args.size());
        assertEquals("mvn", args.get(0));
        assertEquals("\"clean package\"", args.get(1));
    }
}
