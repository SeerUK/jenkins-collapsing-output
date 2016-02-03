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

import hudson.MarkupText;
import hudson.console.ConsoleAnnotator;
import hudson.model.Run;

import java.text.MessageFormat;
import java.util.StringJoiner;

public class LineAnnotator extends ConsoleAnnotator<Object> {
    private int calls = 0;
    private static final long serialVersionUID = 1L;
    private long offset;

    public LineAnnotator(long offset) {
        this.offset = offset;
    }

    @Override
    public ConsoleAnnotator annotate(Object context, MarkupText text) {
        if (!(context instanceof Run)) {
            return this;
        }

        Run run = (Run) context;

        long start = offset;
        int lineEnd = text.length() - 1;

        if (offset < 0) {
            start = run.getLogFile().length() + offset;
        }

        StringJoiner lineClasses = new StringJoiner(" ");
        StringJoiner lineNumberClasses = new StringJoiner(" ");

        if (start > 0) {
            // Only show actual line number if we are starting from line 1
            lineNumberClasses.add("linenumber--unknown");
        }

        String lineText = text.getText();

        if (lineText.startsWith("+ ") || lineText.startsWith(" > ")) {
            lineClasses.add("line--command");
        }

        String startTemplate = "<div class=\"line " + lineClasses.toString() + "\"><span>";
        String endTemplate = "</span></div>";

        startTemplate += "<a class=\"linenumber " + lineNumberClasses.toString() + "\" id=\"L{0}\" href=\"#L{0}\"></a>";

        calls++;

        if (text.length() > 1) {
            text.addMarkup(0, 0, "", MessageFormat.format(startTemplate, calls));
            text.addMarkup(lineEnd, lineEnd, "", endTemplate);
        } else {
            text.addMarkup(0, text.length(), MessageFormat.format(startTemplate, calls), endTemplate);
        }

        return this;
    }
}
