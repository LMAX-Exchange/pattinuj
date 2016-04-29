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

package com.lmax.pattinuj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;


import static java.util.Collections.singletonList;


public class JUnitTapCommandLineRunner
{
    public static void main(String... args)
    {
        try
        {
            final Class<?>[] testClasses = Arrays.stream(args).map(JUnitTapCommandLineRunner::loadClass).toArray(Class[]::new);

            File tapOutputFile = new File(System.getProperty("tap_output_file"));

            try (final FileOutputStream tapOutputStream = new FileOutputStream(tapOutputFile))
            {
                final TapListener listener = new TapListener(tapOutputStream, new TapFormatter());

                final Result result = runTestsWithListeners(testClasses, singletonList(listener));
                System.exit(result.wasSuccessful() ? 0 : 1);
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }
        catch (NoClassDefFoundError ex)
        {
            System.err.println("Could not find test class on class path");
            ex.printStackTrace(System.err);
        }
    }

    public static Result runTestsWithListeners(final Class<?>[] testClasses, final List<RunListener> listeners)
    {
        final JUnitCore jUnitCore = new JUnitCore();
        listeners.forEach(jUnitCore::addListener);
        return jUnitCore.run(testClasses);
    }

    private static Class<?> loadClass(final String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (ClassNotFoundException ex)
        {
            throw new IllegalArgumentException("Class not found : " + name, ex);
        }
    }
}
