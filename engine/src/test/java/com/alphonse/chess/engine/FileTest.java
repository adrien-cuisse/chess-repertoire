
package com.alphonse.chess.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public final class FileTest
{
    @DataProvider
    public static Object[][] fileProvider()
    {
        Object[][] files = new Object[File.values().length][1];

        for (File file : File.values())
        {
            files[file.ordinal()][0] = file;
        }

        return files;
    }

    @Test
    @UseDataProvider(value = "fileProvider")
    public void showsAsSingleCharacter(File file)
    {
        // given a valid file

        // when checking its string format
        String format = file.toString();

        // then it should be a single character
        assertEquals(
            "Files should display as a single character",
            1,
            format.length()
        );
    }

    @Test
    @UseDataProvider(value = "fileProvider")
    public void showsAsLowerCase(File file)
    {
        // given a valid file

        // when checking its string format
        String format = file.toString();

        // then it should be lower-case
        assertEquals(
            "Files should display as lower-case",
            format.toLowerCase(),
            format
        );
    }
}
