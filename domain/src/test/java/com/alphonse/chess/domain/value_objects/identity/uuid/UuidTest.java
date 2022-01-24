
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.identity.uuid.IUuid.InvalidUuidException;
import com.alphonse.chess.domain.value_objects.identity.uuid.IUuid.Variant;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public final class UuidTest
{
    public final static byte[] nullBytes()
    {
        final byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte) 0);
        return bytes;
    }

    public final static byte[] nullBytesAndVersion(final int version)
    {
        byte[] bytes = nullBytes();
        bytes[6] = (byte) (((version & 0b0000_1111) << 4) | (bytes[6] & 0b0000_1111));
        return bytes;
    }

    @Test(expected = InvalidUuidException.class)
    public final void expects16Bytes() throws InvalidUuidException
    {
        // given an invalid bytes count
        final byte[] bytes = new byte[] { 0x42 };

        // when trying to create an uuid from it
        new Uuid(bytes);

        // then an exception should be thrown
    }

    @Test(expected = InvalidUuidException.class)
    public final void expects32HexDigits() throws InvalidUuidException
    {
        // given an invalid string representation
        final String format = "42";

        // when trying to create an uuid from it
        new Uuid(format, 0);

        // then an exception should be thrown
    }

    @Test(expected = InvalidUuidException.class)
    public final void expectsStringMatchingVersion() throws InvalidUuidException
    {
        // given a string representation that mismatches the expected version
        final String format = "00000000-0000-f000-0000-000000000000";
        final int wrongVersion = 0xe;

        // when trying to build an uuid from it
        new Uuid(format, wrongVersion);

        // then an exception should be thrown
    }

    @Test
    public final void isRfcCompliant() throws InvalidUuidException
    {
        // given a valid Uuid
        final IUuid uuid = new Uuid(nullBytes());

        // when checking its string format
        final String format = uuid.toNative();

        // then it should match RFC representation
        assertTrue(
            "Uuid should comply with RFC format xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            // format.matches("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}")
            format.matches("[a-z0-9]{8}-([a-z0-9]{4}-){3}[a-z0-9]{12}")
        );
    }

    public final static Object[][] version()
    {
        return new Object[][] {
            { 4 },
            { 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("version")
    public final void hasSpecifiedVersion(final int expectedVersion) throws InvalidUuidException
    {
        // given an uuid from specified bytes, and a version
        final IUuid uuid = new Uuid(nullBytesAndVersion(expectedVersion), expectedVersion);

        // when checking the actual version of the uuid
        final int actualVersion = uuid.version();

        // then it should be the expected one
        assertEquals(
            "Version wasn't correctly set",
            expectedVersion,
            actualVersion
        );
    }

    @ParameterizedTest
    @MethodSource("version")
    public final void versionIsThe13thDigit(final int expectedVersion) throws InvalidUuidException
    {
        // given an uuid from specified bytes, and a version
        final IUuid uuid = new Uuid(nullBytesAndVersion(expectedVersion), expectedVersion);

        // when checking the string representation of the uuid
        final String format = uuid.toString();

        // then it should contain the version number as 13th digit (skipping dashes)
        assertEquals(
            "Version should be set as the 13th digit",
            Character.forDigit(expectedVersion, 16),
            format.charAt(14)
        );
    }

    public final static Object[][] variant()
    {
        return new Object[][] {
            { Variant.APOLLO_NCS_VARIANT, List.of('0', '1', '2', '3', '4', '5', '6', '7') },
            { Variant.RFC_VARIANT, List.of('8', '9', 'a', 'b') },
            { Variant.MICROSOFT_VARIANT, List.of('c', 'd') },
            { Variant.FUTURE_VARIANT, List.of('e', 'f') },
        };
    }

    @ParameterizedTest
    @MethodSource("variant")
    public final void hasSpecifiedVariant(final Variant expectedVariant, final List<Character> __) throws InvalidUuidException
    {
        // given an uuid given specified bytes, and a variant
        final Uuid uuid = new Uuid(nullBytes(), 0, expectedVariant);

        // when checking the actual variant of the uuid
        final Variant actualVariant = uuid.variant();

        // then it should be the expected one
        assertEquals(
            String.format("Wrong variant detected"),
            expectedVariant,
            actualVariant
        );
    }

    @ParameterizedTest
    @MethodSource("variant")
    public final void variantIsThe17thDigit(final Variant expectedVariant, final List<Character> possibleVariantDigits) throws InvalidUuidException
    {
        // given an uuid given specified bytes, and a variant
        final Uuid uuid = new Uuid(nullBytes(), 0, expectedVariant);

        // when checking the variant digit in the string representation of the uuid
        final Character actualVariantDigit = uuid.toString().charAt(19);

        // then it should be a matching one
        assertTrue(
            String.format("Wrong variant detected"),
            possibleVariantDigits.contains(actualVariantDigit)
        );
    }

    public final static Object[][] string()
    {
        return new Object[][] {
            { "c940267f-9bab-62f9-1510-850b693705cd", 6, Variant.APOLLO_NCS_VARIANT },
            { "ee6fbac0-0ba7-7b42-9356-bb9aa340b7f2", 7, Variant.RFC_VARIANT },
            { "792c4ae2-0f55-834d-d4b8-68c013256fc3", 8, Variant.MICROSOFT_VARIANT },
            { "606e60f0-5fcf-9c25-f52a-456394790232", 9, Variant.FUTURE_VARIANT },
        };
    }

    @ParameterizedTest
    @MethodSource("string")
    public final void buildsFromString(final String expectedFormat, final int expectedVersion, final Variant __) throws InvalidUuidException
    {
        // given an uuid made from a string
        final IUuid uuid = new Uuid(expectedFormat, expectedVersion);

        // when checking its string representation
        final String actualFormat = uuid.toString();

        // then it should be the one used to build it
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedFormat,
            actualFormat
        );
    }

    @ParameterizedTest
    @MethodSource("string")
    public final void parsesVersionFromString(final String expectedFormat, final int expectedVersion, final Variant __) throws InvalidUuidException
    {
        // given an uuid made from a string
        final IUuid uuid = new Uuid(expectedFormat, expectedVersion);

        // when checking its version
        final int actualVersion = uuid.version();

        // then it should be the expected one
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedVersion,
            actualVersion
        );
    }

    @ParameterizedTest
    @MethodSource("string")
    public final void parsesVariantFromString(final String expectedFormat, final int expectedVersion, final Variant expectedVariant) throws InvalidUuidException
    {
        // given an uuid made from a string
        final IUuid uuid = new Uuid(expectedFormat, expectedVersion);

        // when checking its variant
        final Variant actualVariant = uuid.variant();

        // then it should be the expected one
        assertEquals(
            "Failed to parse RFC compliant string",
            expectedVariant,
            actualVariant
        );
    }

    @Test // (expected = InvalidUuidFormatException.class)
    public final void expectsHexDigits()
    {
        // given an invalid format
        final String invalidFormat = "00000000-0z00-0000-0000-000000000000";

        // when trying to create a uuid from it

        ThrowingRunnable instanciation = () -> {
            new Uuid(invalidFormat, 0);
        };

        // then an exception should be thrown
        assertThrows(
            "Uuid should contain only hex digit",
            InvalidUuidException.class,
            instanciation
        );
    }
}
