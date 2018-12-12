package kravchenko.solution.state;

import com.google.common.base.Strings;
import kravchenko.solution.FormatProgram;

public interface State extends FormatProgram {

    default String getIndent(int tabs) {
        return Strings.repeat("    ", tabs);
    }

}
