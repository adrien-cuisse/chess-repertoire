
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.identity.IIdentity;

/**
 * A RFC-4122 compliant UUID
 *
 * @see https://datatracker.ietf.org/doc/html/rfc4122
 */
public interface IUuid extends IIdentity<String>
{
    public int version();

    public Variant variant();

    public enum Variant
    {
        APOLLO_NCS_VARIANT(0x00, 0x7f),
        RFC_VARIANT(0x80, 0x3f),
        MICROSOFT_VARIANT(0xc0, 0x1f),
        FUTURE_VARIANT(0xe0, 0x1f);

        private final byte bits;

        private final byte unusedBitsMask;

        private Variant(final int bits, final int unusedBitsMask)
        {
            this.bits = (byte) bits;
            this.unusedBitsMask = (byte) unusedBitsMask;
        }

        public final byte bits()
        {
            return this.bits;
        }

        public final byte unusedBitsMask()
        {
            return this.unusedBitsMask;
        }

        public static final Variant matchFromByte(final byte octet)
        {
            final int bits = (octet & 0b1110_0000);

            if (bits >= 0xe0)
                return FUTURE_VARIANT;
            if (bits >= 0xc0)
                return MICROSOFT_VARIANT;
            if (bits >= 0x80)
                return RFC_VARIANT;

            return APOLLO_NCS_VARIANT;
        }
    }

    public final class InvalidUuidException extends Exception
    {
        public InvalidUuidException(final byte[] bytes)
        {
            super(String.format("Uuid must be 16 bytes long, go %d", bytes.length));
        }

        public InvalidUuidException(final String uuid)
        {
            super(String.format("Uuid %s doesn't comply with RFC format xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", uuid));
        }

        public InvalidUuidException (final String uuid, final int expectedVersion, final int actualVersion)
        {
            super(String.format("Expected uuid %s to be version %d, got version %d", uuid, expectedVersion, actualVersion));
        }
    }
}
