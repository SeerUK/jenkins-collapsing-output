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
import hudson.console.ConsoleNote;

public class SectionConsoleNote extends ConsoleNote<Object> {
    public enum Kind {
        BUILD_SECTION_START,
        BUILD_SECTION_END
    }

    private Kind kind;
    private int sectionNumber;

    public SectionConsoleNote(Kind kind, int sectionNumber) {
        this.kind = kind;
        this.sectionNumber = sectionNumber;
    }

    @Override
    public ConsoleAnnotator annotate(Object context, MarkupText text, int charPos) {
        if (kind == Kind.BUILD_SECTION_START) {
            String sectionName = "section." + sectionNumber;
            String markup = "<section id='collapsing-section-%s' class='collapsing-section'><span class='collapsing-section__name'>%s</span>";

            text.addMarkup(0, String.format(markup, sectionNumber, sectionName));
        } else if (kind == Kind.BUILD_SECTION_END) {
            text.addMarkup(0, 0, "", "</section>");
        }

        // We don't have anything to do with the next line being annotated
        return null;
    }
}
