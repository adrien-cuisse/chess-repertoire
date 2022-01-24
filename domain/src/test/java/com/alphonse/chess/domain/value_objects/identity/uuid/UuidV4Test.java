
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.identity.uuid.IUuid.InvalidUuidException;
import com.alphonse.chess.domain.value_objects.identity.uuid.IUuid.Variant;
import com.alphonse.chess.domain.value_objects.identity.uuid.UuidV4.InvalidUuidV4Exception;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

// @RunWith(DataProviderRunner.class)
public final class UuidV4Test
{
    public final static byte[] nullBytes()
    {
        final byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte) 0);
        return bytes;
    }

    @Test
    public final void is32HexDigitsLong()
    {
        // given an uuid
        final UuidV4 uuid = new UuidV4();

        // when checking how many digits are composing its string representation
        final int digitsCount = uuid.toString().replace("-", "").length();

        // then there should be 32
        assertEquals(
            "Uuid should be made of 32 hex-digits",
            32,
            digitsCount
        );
    }

    @Test
    public final void isVersion4()
    {
        // given a new uuid
        final UuidV4 uuid = new UuidV4();

        // when checking its version
        final int version = uuid.version();

        // then it should be 4
        assertEquals(
            "Version of UuidV4 should be 4",
            4,
            version
        );
    }

    @Test
    public final void isRfcVariant()
    {
        // given a new uuid
        final UuidV4 uuid = new UuidV4();

        // when checking its variant
        final Variant variant = uuid.variant();

        // then it should be RFC
        assertEquals(
            "UuidV4 should be RFC variant",
            Variant.RFC_VARIANT,
            variant
        );
    }

    public final static Object[][] equality() throws InvalidUuidException, InvalidUuidV4Exception
    {
        final UuidV4 uuid = new UuidV4();
        final IUuid otherInstance = new UuidV4();
        final IUuid otherImplementation = new Uuid(nullBytes(), 0);
        final IUuid sameStringRepresentation = new UuidV4(uuid.toString());

        return new Object[][] {
            { uuid, otherImplementation, false },
            { uuid, otherInstance, false },
            { uuid, uuid, true },
            { uuid, sameStringRepresentation, true },
        };
    }

    @ParameterizedTest
    @MethodSource("equality")
    public final void matchesOnlyExactString(final UuidV4 uuid, final IUuid other, final boolean expectedEquality)
    {
        // given 2 uuids

        // when comparing them
        final boolean actualEquality = uuid.equals(other);

        // then it should match
        assertEquals(
            "Uuids should equal only if they represent the same value",
            expectedEquality,
            actualEquality
        );
    }

    @Test
    public final void isUnique()
    {
        // given 2 uuids built the same way
        final UuidV4 first = new UuidV4();
        final UuidV4 second = new UuidV4();

        // when comparing them
        final boolean instancesAreTheSame = first.equals(second);

        // then they should be different
        assertFalse(
            "Each instance should not equal any other",
            instancesAreTheSame
        );
    }
}
