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

import hudson.console.ConsoleLogFilter;
import hudson.console.LineTransformationOutputStream;
import hudson.model.AbstractBuild;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NewlineConsoleLogFilter extends ConsoleLogFilter {
    @Override
    public OutputStream decorateLogger(AbstractBuild build, final OutputStream logger) throws IOException, InterruptedException {
        if (logger == null) {
            return null;
        }

        return new LineTransformationOutputStream() {
            private FilterOutputStream output = new FilterOutputStream(logger);

            private static final int CHAR_NULL = 0x00; // 0
            private static final int CHAR_ESC = 0x1b; // 27
            private static final int CHAR_A = 0x41; // 65
            private static final int CHAR_LF = 0x0a; // 10
            private static final int CHAR_CR = 0x0d; // 13
            private static final int CHAR_BRACE_L = 0x5b; // 91
//
//            @Override
//            public void write(byte[] b, int off, int len) throws IOException {
////                if (off != 0 && len > 1 && b[0] != LF) {
//                    super.write(b, off, len);
////                }
//            }

            @Override
            protected void eol(byte[] b, int len) throws IOException {
                if (b[0] == CHAR_ESC && b[1] == CHAR_BRACE_L && b[2] == CHAR_A) {
                    b[0] = b[1] = b[2] = CHAR_NULL;

                    if (b[3] == CHAR_LF || b[3] == CHAR_CR) {
                        b[3] = CHAR_NULL;
                    }
                }

                output.write(b, 0, len);
                output.flush();
                logger.flush();
            }

            @Override
            public void close() throws IOException {
                output.close();
                logger.close();
                super.close();
            }
        };
    }
}
