
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.identity.uuid.IUuid.Variant;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidBytesCountException;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidDigitsCountException;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public final class UuidTest
{
    @Test(expected = InvalidBytesCountException.class)
    public final void expected16Bytes() throws InvalidBytesCountException
    {
        // given an invalid bytes count
        byte[] bytes = new byte[] { 0x42 };

        // when trying to create an uuid from it
        new Uuid(bytes);

        // then an exception should be thrown
    }

    @Test(expected = InvalidDigitsCountException.class)
    public final void expected32HexDigits() throws InvalidDigitsCountException
    {
        // given an invalid string representation
        String format = "42";

        // when trying to create an uuid from it
        new Uuid(format);

        // then an exception should be thrown
    }

    @Test
    public final void isRfcCompliant()
    {
        // given a valid Uuid
        IUuid uuid = new DummyUuid();

        // when checking its string format
        String format = uuid.toNative();

        // then it should match RFC representation
        assertTrue(
            "Uuid should comply with RFC format xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            // format.matches("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}")
            format.matches("[a-z0-9]{8}-([a-z0-9]{4}-){3}[a-z0-9]{12}")
        );
    }

    @DataProvider
    public final static Object[][] version()
    {
        return new Object[][] {
            { 4 },
            { 2 },
        };
    }

    @Test
    @UseDataProvider(value = "version")
    public final void hasSpecifiedVersion(final int expectedVersion)
    {
        // given an uuid from specified bytes, and a version
        IUuid uuid = new DummyUuid(DummyUuid.nullBytes(), expectedVersion);

        // when checking the actual version of the uuid
        int actualVersion = uuid.version();

        // then it should be the expected one
        assertEquals(
            "Version wasn't correctly set",
            expectedVersion,
            actualVersion
        );
    }

    @Test
    @UseDataProvider(value = "version")
    public final void versionIsThe13thDigit(final int expectedVersion)
    {
        // given an uuid from specified bytes, and a version
        IUuid uuid = new DummyUuid(DummyUuid.nullBytes(), expectedVersion);

        // when checking the string representation of the uuid
        String format = uuid.toString();

        // then it should contain the version number as 13th digit (skipping dashes)
        assertEquals(
            "Version should be set as the 13th digit",
            String.format("%d", expectedVersion).charAt(0),
            format.charAt(14)
        );
    }

    @DataProvider
    public final static Object[][] variant()
    {
        return new Object[][] {
            { Variant.APOLLO_NCS_VARIANT, List.of('0', '1', '2', '3', '4', '5', '6', '7') },
            { Variant.RFC_VARIANT, List.of('8', '9', 'a', 'b') },
            { Variant.MICROSOFT_VARIANT, List.of('c', 'd') },
            { Variant.FUTURE_VARIANT, List.of('e', 'f') },
        };
    }

    @Test
    @UseDataProvider(value = "variant")
    public final void hasSpecifiedVariant(final Variant expectedVariant, final List<Character> __)
    {
        // given an uuid given specified bytes, and a variant
        byte[] bytes = DummyUuid.nullBytes();
        DummyUuid uuid = new DummyUuid(bytes, 0, expectedVariant);

        // when checking the actual variant of the uuid
        Variant actualVariant = uuid.variant();

        // then it should be the expected one
        assertEquals(
            String.format("Wrong variant detected"),
            expectedVariant,
            actualVariant
        );
    }

    @Test
    @UseDataProvider(value = "variant")
    public final void variantIsThe17thDigit(final Variant expectedVariant, final List<Character> possibleVariantDigits)
    {
        // given an uuid given specified bytes, and a variant
        byte[] bytes = DummyUuid.nullBytes();
        DummyUuid uuid = new DummyUuid(bytes, 0, expectedVariant);

        // when checking the variant digit in the string representation of the uuid
        Character actualVariantDigit = uuid.toString().charAt(19);

        // then it should be a matching one
        assertTrue(
            String.format("Wrong variant detected"),
            possibleVariantDigits.contains(actualVariantDigit)
        );
    }

    @DataProvider
    public final static Object[][] string()
    {
        return new Object[][] {
            { "c940267f-9bab-62f9-1510-850b693705cd", 6, Variant.APOLLO_NCS_VARIANT },
            { "ee6fbac0-0ba7-7b42-9356-bb9aa340b7f2", 7, Variant.RFC_VARIANT },
            { "792c4ae2-0f55-834d-d4b8-68c013256fc3", 8, Variant.MICROSOFT_VARIANT },
            { "606e60f0-5fcf-9c25-f52a-456394790232", 9, Variant.FUTURE_VARIANT },
        };
    }

    @Test
    @UseDataProvider(value = "string")
    public final void buildsFromString(final String expectedFormat, final int __, final Variant ___)
    {
        // given an uuid made from a string
        IUuid uuid = new DummyUuid(expectedFormat);

        // when checking its string representation
        String actualFormat = uuid.toString();

        // then it should be the one used to build it
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedFormat,
            actualFormat
        );
    }

    @Test
    @UseDataProvider(value = "string")
    public final void parsesVersionFromString(final String expectedFormat, final int expectedVersion, final Variant __)
    {
        // given an uuid made from a string
        IUuid uuid = new DummyUuid(expectedFormat);

        // when checking its version
        int actualVersion = uuid.version();

        // then it should be the expected one
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedVersion,
            actualVersion
        );
    }

    @Test
    @UseDataProvider(value = "string")
    public final void parsesVariantFromString(final String expectedFormat, final int __, final Variant expectedVariant)
    {
        // given an uuid made from a string
        IUuid uuid = new DummyUuid(expectedFormat);

        // when checking its variant
        Variant actualVariant = uuid.variant();

        // then it should be the expected one
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedVariant,
            actualVariant
        );
    }
}
