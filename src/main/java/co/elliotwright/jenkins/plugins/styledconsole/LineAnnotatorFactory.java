/**
 * This file is part of the "collapsing-output" project.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the LICENSE is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * <p>
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package co.elliotwright.jenkins.plugins.styledconsole;

import hudson.Extension;
import hudson.console.ConsoleAnnotator;
import hudson.console.ConsoleAnnotatorFactory;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

@Extension
@SuppressWarnings("unused")
public class LineAnnotatorFactory extends ConsoleAnnotatorFactory<Object> {
    @Override
    public ConsoleAnnotator<Object> newInstance(Object context) {
        long offset = getOffset(Stapler.getCurrentRequest());
        return new LineAnnotator(offset);
    }

    /**
     * Get the current offset for viewing the console log. A non-negative offset
     * is from the start of the file, and a negative offset is back from the end
     * of the file.
     * Note : Copied from hudson.plugins.timestamper.annotator.TimestampAnnotatorFactory
     *
     * @param request
     * @return the offset in bytes
     */
    private static long getOffset(StaplerRequest request) {
        String path = request.getPathInfo();

        if (path == null) {
            // JENKINS-16438
            path = request.getServletPath();
        }

        if (path.endsWith("/consoleFull")) {
            // Displaying the full log of a completed build.
            return 0;
        }

        if (path.endsWith("/console")) {
            // Displaying the tail of the log of a completed build.
            // This duplicates code found in /hudson/model/Run/console.jelly
            // TODO: Ask Jenkins for the console tail size instead.
            String threshold = System.getProperty("hudson.consoleTailKB", "150");
            return -(Long.parseLong(threshold) * 1024);
        }

        // Displaying the log of a build in progress.
        // The start parameter is documented on the build's remote API page.
        String startParameter = request.getParameter("start");
        
        return startParameter != null
            ? Long.parseLong(startParameter)
            : 0;
    }
}
