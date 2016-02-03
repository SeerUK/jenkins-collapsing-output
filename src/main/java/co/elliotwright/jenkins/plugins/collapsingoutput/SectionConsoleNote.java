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

public class SectionConsoleNote<T> extends ConsoleNote<T> {
    private int sectionNumber;

    public SectionConsoleNote(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    @Override
    public ConsoleAnnotator annotate(T context, MarkupText text, int charPos) {
        String sectionName = "step." + sectionNumber;
        String markup = "<span class='collapsing-section__start'>%s</span>";

        text.addMarkup(0, String.format(markup, sectionName));

        // We don't have anything to do with the next line being annotated
        return null;
    }
}
