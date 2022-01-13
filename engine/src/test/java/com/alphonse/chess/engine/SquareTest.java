
package com.alphonse.chess.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public final class SquareTest
{
    @DataProvider
    public static Object[][] squaresProvider()
    {
        int squaresCount = File.values().length * Rank.values().length;

        Object[][] squares = new Object[squaresCount][2];

        for (File file : File.values())
        {
            for (Rank rank : Rank.values())
            {
                int squareIndex = File.values().length * file.ordinal() + rank.ordinal();
                squares[squareIndex][0] = file;
                squares[squareIndex][1] = rank;
            }
        }

        return squares;
    }

    @Test
    @UseDataProvider(value = "squaresProvider")
    public void showsAsFileRank(File file, Rank rank)
    {
        // given a square at given coords
        Square square = new Square(null, file, rank);

        // check checking its string format
        String format = square.toString();

        // then it should be its coords <"file","rank">
        assertEquals(
            "Squares should be displayed as coords",
            file.toString() + rank.toString(),
            format
        );
    }
}
