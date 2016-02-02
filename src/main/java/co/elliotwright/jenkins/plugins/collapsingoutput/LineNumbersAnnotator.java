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

        // only annotate if we are handling the full log
        if (start <= 0) {
            int end = text.length() - 1;

            String startTemplate = "<div class=\"line\"><a class=\"linenumber\" id=\"L{0}\" href=\"#L{0}\"></a><span>";
            String endTemplate = "</span></div>";

            calls++;

            if (text.length() > 1) {
                text.addMarkup(0, 0, "", MessageFormat.format(startTemplate, calls));
                text.addMarkup(end, end, "", endTemplate);
            } else {
                text.addMarkup(0, text.length(), MessageFormat.format(startTemplate, calls), endTemplate);
            }
        }

        return this;
    }
}
