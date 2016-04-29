/*
 * Copyright 2016 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lmax.pattinuj.tests;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

import com.lmax.pattinuj.TapListener;
import com.lmax.pattinuj.examples.FailingTest;
import com.lmax.pattinuj.examples.InvalidTest;
import com.lmax.pattinuj.examples.PassingTest;
import com.lmax.pattinuj.examples.ThrowingTest;
import com.lmax.pattinuj.TapFormatter;
import com.lmax.pattinuj.JUnitTapCommandLineRunner;

import com.lmax.pattinuj.examples.IgnoredTest;


import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class JUnitTapCommandLineRunnerTest
{
    @Test
    public void passingTestOutputsSuccess() throws Exception
    {
        String testResults = runTestAndGetTapOutput(PassingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.lmax.pattinuj.examples.PassingTest.aTestThatPasses"));
    }

    @Test
    public void failingTestOutputsFailureWithFailureMessage() throws Exception
    {
        String testResults = runTestAndGetTapOutput(FailingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("not ok 1 com.lmax.pattinuj.examples.FailingTest.aTestThatFails"));
        assertThat(testResults, Matchers.containsString("Failure message"));
    }

    @Test
    public void throwingTestOutputsFailureWithExceptionTypeMessageAndStacktrace() throws Exception
    {
        String testResults = runTestAndGetTapOutput(ThrowingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("not ok 1 com.lmax.pattinuj.examples.ThrowingTest.aTestThatThrows"));
        assertThat(testResults, Matchers.containsString("RuntimeException"));
        assertThat(testResults, Matchers.containsString("Exception message"));
        assertThat(testResults, Matchers.containsString("com.lmax.pattinuj.examples.ThrowingTest.aTestThatThrows(ThrowingTest.java:30)"));
    }

    @Test
    public void ignoredTestIsSkipped() throws Exception
    {
        String testResults = runTestAndGetTapOutput(IgnoredTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.lmax.pattinuj.examples.IgnoredTest.aTestWhichIsIgnored # skip Test ignored"));

    }

    @Test
    public void invalidTestIsSkipped() throws Exception
    {
        String testResults = runTestAndGetTapOutput(InvalidTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.lmax.pattinuj.examples.InvalidTest.aTestWhichIsInvalid # skip The world is flat"));
    }



    private String runTestAndGetTapOutput(final Class<?> testClass) throws Exception
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Class[] testClasses = new Class[] {testClass};

        JUnitTapCommandLineRunner.runTestsWithListeners(testClasses, Collections.singletonList(new TapListener(os, new TapFormatter())));

        return os.toString("UTF-8");
    }
}
