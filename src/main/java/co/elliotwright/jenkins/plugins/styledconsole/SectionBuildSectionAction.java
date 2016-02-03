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

import hudson.model.InvisibleAction;

public class SectionBuildSectionAction extends InvisibleAction {
    private int sectionNumber = 0;

    public int increment() {
        return ++sectionNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }
}
