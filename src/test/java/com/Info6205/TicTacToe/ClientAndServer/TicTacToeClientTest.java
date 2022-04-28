package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.TicTacToe.Training;
import com.Info6205.TicTacToe.UserInterface.MainInterface;
import com.Info6205.Util.PrivateMethodTester;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.awt.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TicTacToeClientTest {


    @Test
    public void loadCellsTest() {
        TicTacToeClient clientTest = new TicTacToeClient("pve" ,"11",new MainInterface(), new Training(),1);
        PrivateMethodTester pmt = new PrivateMethodTester(clientTest);
        Point[] cellsTest = (Point[]) pmt.invokePrivate("loadCells");
        assertEquals(cellsTest[0].getX(),109);
        assertEquals(cellsTest[0].getY(),109);

        assertEquals(cellsTest[1].getX(),299);
        assertEquals(cellsTest[1].getY(),109);

        assertEquals(cellsTest[2].getX(),489);
        assertEquals(cellsTest[2].getY(),109);

        assertEquals(cellsTest[3].getX(),109);
        assertEquals(cellsTest[3].getY(),299);

        assertEquals(cellsTest[4].getX(),299);
        assertEquals(cellsTest[4].getY(),299);

        assertEquals(cellsTest[5].getX(),489);
        assertEquals(cellsTest[5].getY(),299);

        assertEquals(cellsTest[6].getX(),109);
        assertEquals(cellsTest[6].getY(),489);

        assertEquals(cellsTest[7].getX(),299);
        assertEquals(cellsTest[7].getY(),489);

        assertEquals(cellsTest[8].getX(),489);
        assertEquals(cellsTest[8].getY(),489);
    }



}