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

package co.elliotwright.jenkins.plugins.collapsingoutput;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.console.ConsoleLogFilter;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.tasks.SimpleBuildWrapper;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

public class NewlineBuildWrapper extends SimpleBuildWrapper {
    @DataBoundConstructor
    public NewlineBuildWrapper() {}

    @Override
    public void setUp(
            Context context,
            Run<?, ?> build,
            FilePath workspace,
            Launcher launcher,
            TaskListener listener,
            EnvVars initialEnvironment)
        throws IOException, InterruptedException {
    }

    @Override
    public ConsoleLogFilter createLoggerDecorator(@Nonnull Run<?, ?> build) {
        return new NewlineConsoleLogFilter();
    }
}
