/**
 * This file is part of the "collapsingoutput" project.
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
import hudson.model.AbstractProject;
import hudson.tasks.BuildWrapperDescriptor;

@Extension
@SuppressWarnings("unused")
public class NewlineBuildWrapperDescriptor extends BuildWrapperDescriptor {

    public NewlineBuildWrapperDescriptor() {
        super(NewlineBuildWrapper.class);
        load();
    }

    @Override
    public boolean isApplicable(AbstractProject<?, ?> item) {
        return true;
    }

    @Override
    public String getDisplayName() {
        return getClass().getName();
    }
}
