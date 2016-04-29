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

import java.io.PrintWriter;
import java.util.List;

public final class TapFormatter
{
    public TapFormatter()
    {
    }

    public void writeResults(final PrintWriter printWriter, final List<TapResult> results)
    {
        printWriter.println("1.." + results.size());
        for (int i = 0; i < results.size(); i++)
        {
            final TapResult tapResult = results.get(i);
            printWriter.println(String.format("%s %d %s %s", describeResult(tapResult), i + 1, tapResult.getDescription(), tapResult.getDirective()));
            if (tapResult.getCause() != null)
            {
                writeCause(printWriter, tapResult.getCause());
            }
        }
        printWriter.flush();
    }

    public static void writeCause(final PrintWriter printWriter, final Throwable cause)
    {
        writeCause(printWriter, cause, "");
    }

    private static void writeCause(final PrintWriter printWriter, final Throwable cause, final String preAmble)
    {
        if(cause == null)
        {
            return;
        }
        printWriter.print("# ");
        printWriter.print(preAmble);
        printWriter.print(cause.getClass().getCanonicalName());
        printWriter.print(":");
        printWriter.print(cause.getMessage());

        printWriter.println();

        for(StackTraceElement element: cause.getStackTrace())
        {
            printWriter.print("#   at ");
            printWriter.print(element);
            printWriter.println();
        }

        writeCause(printWriter, cause.getCause(), "caused by: ");
    }

    private static String describeResult(final TapResult tapResult)
    {
        return tapResult.passed() ? "ok" : "not ok";
    }
}
