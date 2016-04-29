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

package com.lmax.pattinuj.examples;

import org.junit.Assume;
import org.junit.Test;

/**
 * This test is not intended to be run as part of the build, but rather to be programatically run by the runner
 * in the runner's unit test, to then assert on the output.
 */
public class InvalidTest
{
    @Test
    public void aTestWhichIsInvalid()
    {
        Assume.assumeTrue("The world is flat", false);
    }
}
