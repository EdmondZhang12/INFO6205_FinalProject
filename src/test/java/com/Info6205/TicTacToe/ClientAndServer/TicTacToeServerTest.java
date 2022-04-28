package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.Util.NetUtil;
import com.Info6205.Util.PrivateMethodTester;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicTacToeServerTest {

    @Test
    public void run() throws IOException {
        TicTacToeServer server = new TicTacToeServer();
        NetUtil netUtil = new NetUtil();
        boolean isRunning = netUtil.isLoclePortUsing(12345);
        assertFalse(isRunning);
    }

}
