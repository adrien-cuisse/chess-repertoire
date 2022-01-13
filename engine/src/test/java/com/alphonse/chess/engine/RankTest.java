
package com.alphonse.chess.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public final class RankTest
{
    @DataProvider
    public static Object[][] rankProvider()
    {
        Object[][] ranks = new Object[Rank.values().length][1];

        for (Rank rank : Rank.values())
        {
            ranks[rank.ordinal()][0] = rank;
        }

        return ranks;
    }

    @Test
    @UseDataProvider(value = "rankProvider")
    public void showsAsSingleCharacter(Rank rank)
    {
        // given a valid rank

        // when checking its string format
        String format = rank.toString();

        // then it should be a single character
        assertEquals(
            "Ranks should display as a single character",
            1,
            format.length()
        );
    }

    @Test
    @UseDataProvider(value = "rankProvider")
    public void showsAsDigits(Rank rank)
    {
        // given a valid rank

        // when checking its string format
        String format = rank.toString();

        // then it should be only digits
        assertTrue(
            "Ranks should display as digits",
            format.matches("[\\d]+")
        );
    }
}
