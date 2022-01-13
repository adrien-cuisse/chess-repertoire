
package com.alphonse.chess.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public final class BoardTest
{
    private Board board;

    @Before
    public final void beforeEachTest()
    {
        this.board = new Board();
    }

    public final static Object[][] squaresLabel(List<Character> filesLabel, List<Character> ranksLabel)
    {
        int labelsCount = filesLabel.size() * ranksLabel.size();

        Object[][] labels = new Object[labelsCount][1];

        int addedLabels = 0;

        for (Character fileLabel : filesLabel)
        {
            for (Character rankLabel : ranksLabel)
            {
                labels[addedLabels++][0] = fileLabel.toString() + rankLabel.toString();
            }
        }

        return labels;
    }

    public final static List<Character> allFilesLabel()
    {
        return Arrays.stream(File.values())
            .map((File file) -> file.label)
            .collect(Collectors.toList())
        ;
    }

    public final static List<Character> allRanksLabel()
    {
        return Arrays.stream(Rank.values())
            .map((Rank rank) -> rank.label)
            .collect(Collectors.toList())
        ;
    }

    @DataProvider
    public final static Object[][] squaresLabelProvider()
    {
        return squaresLabel(
            allFilesLabel(),
            allRanksLabel()
        );
    }

    @Test
    @UseDataProvider(value = "squaresLabelProvider")
    public final void givesRequestedSquare(String squareLabel)
    {
        // given a valid square label

        // when requesting the actual square
        Optional<Square> square = this.board.get(squareLabel);

        // then it should be on the board
        assertTrue(
            String.format("Board has no square %s", squareLabel),
            square.isPresent()
        );

        // and the correct square should be given
        assertEquals(
            "Wrong square was returned",
            squareLabel,
            square.get().toString()
        );
    }

    @DataProvider
    public final static Object[][] squaresLabelExceptLastRankProvider()
    {
        List<Character> ranksLabelButLastOne = allRanksLabel().subList(0, 7);

        return squaresLabel(
            allFilesLabel(),
            ranksLabelButLastOne
        );
    }

    @Test
    @UseDataProvider(value = "squaresLabelExceptLastRankProvider")
    public final void nextSquareInFileHasDifferenctColor(String squareLabel)
    {
        // given a square on the board
        Square square = this.board.get(squareLabel).get();

        // when getting the square below it
        char fileLabel = squareLabel.charAt(0);
        char nextRankLabel = (char) (squareLabel.codePointAt(1) - 1);
        String nextSquareLabel = String.format("%c%c", fileLabel, nextRankLabel);
        Square nextSquare = this.board.get(nextSquareLabel).get();

        // then both squares should have different colors
        assertNotEquals(
            "Next square in file should have a different color",
            square.color,
            nextSquare.color
        );
    }

    @DataProvider
    public final static Object[][] squaresLabelExceptLastFileProvider()
    {
        List<Character> allFilesLabelButLastOne = allFilesLabel().subList(0, 1);

        return squaresLabel(
            allFilesLabelButLastOne,
            allRanksLabel()
        );
    }

    @Test
    @UseDataProvider(value = "squaresLabelExceptLastFileProvider")
    public final void nextSquareInRankHasDifferenctColor(String squareLabel)
    {
        // given a square on the board
        Square square = this.board.get(squareLabel).get();

        // when getting the square next to it
        char rankLabel = squareLabel.charAt(1);
        char nextFileLabel = (char) (squareLabel.codePointAt(0) + 1);
        String nextSquareLabel = String.format("%c%c", nextFileLabel, rankLabel);
        Square nextSquare = this.board.get(nextSquareLabel).get();

        // then both squares should have different colors
        assertNotEquals(
            "Next square in rank should have a different color",
            square.color,
            nextSquare.color
        );
    }
}
