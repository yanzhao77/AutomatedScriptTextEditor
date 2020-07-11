package com.yz.scriptPrint;

import java.io.IOException;

public interface State {
    void setScriptPrinterState(int state, String scriptName) throws IOException;
}
