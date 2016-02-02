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

package co.elliotwright.jenkins.plugins.collapsingoutput;

import hudson.MarkupText;
import hudson.console.ConsoleAnnotator;
import hudson.model.Run;

import java.text.MessageFormat;

public class LineNumbersAnnotator extends ConsoleAnnotator<Object> {
    private int calls = 0;
    private static final long serialVersionUid = 1L;
    private long offset;

    public LineNumbersAnnotator(long offset) {
        this.offset = offset;
    }

    @Override
    public ConsoleAnnotator annotate(Object context, MarkupText text) {
        if (!(context instanceof Run)) {
            return this;
        }

        Run r = (Run)context;
        long start;

        if (offset < 0) {
            start = r.getLogFile().length() + offset;
        } else {
            start = offset;
        }

        if (start <= 0) { // only annotate if we are handling the full log
            calls++;
            int end = text.length() - 1;

            // Trick to make sure we wrap everything including the Timestamp from Timestamper plugin
            text.addMarkup(0, 0, "",
                    MessageFormat.format("<p class=\"line\"><a class=\"linenumber\" id=\"L{0}\" href=\"#L{0}\"></a><span>", calls));
            text.addMarkup(end, end, "", "</span></p>");
        }

        return this;
    }
}
