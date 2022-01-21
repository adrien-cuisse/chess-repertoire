
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.IValueObject;

import java.util.Arrays;
import java.util.List;

final class Uuid implements IUuid
{
    protected final byte[] bytes;

    protected Uuid(final byte[] bytes) throws InvalidBytesCountException
    {
        if (bytes.length != 16)
        {
            throw new InvalidBytesCountException(bytes);
        }

        this.bytes = bytes;
    }

    protected Uuid(final byte[] bytes, final int version) throws InvalidBytesCountException
    {
        this(interlopVersionInTimestampHigh(bytes, version));
    }

    protected Uuid(final byte[] bytes, final int version, final Variant variant) throws InvalidBytesCountException
    {
        this(interlopVariantInClockSequenceHigh(bytes, variant), version);
    }

    protected Uuid(final String rfcCompliantUuid, final int expectedVersion) throws InvalidDigitsCountException, InvalidVersionException
    {
        final char[] digits = rfcCompliantUuid.replace("-", "").toCharArray();

        if (digits.length != 32)
        {
            throw new InvalidDigitsCountException(rfcCompliantUuid, digits.length);
        }
        if (digits[12] != Character.forDigit(expectedVersion, 10))
        {
            throw new InvalidVersionException(rfcCompliantUuid, expectedVersion);
        }

        this.bytes = new byte[digits.length / 2];

        for (int processedDigits = 0; processedDigits < digits.length; processedDigits += 2)
        {
            byte highNibble = (byte) ((Character.digit(digits[processedDigits], 16) << 4));
            byte lowNibble = (byte) (Character.digit(digits[processedDigits + 1], 16));
            this.bytes[processedDigits / 2] = (byte) (highNibble | lowNibble);
        }
    }

    public final boolean equals(final IUuid other)
    {
        return this.toNative().equals(other.toNative());
    }

    public final boolean equals(final IValueObject other)
    {
        return false;
    }

    public final int version()
    {
        return (this.bytes[6] & 0b1111_1111) >> 4;
    }

    public final Variant variant()
    {
        final byte variantBits = (byte) ((this.bytes[8] & 0b1110_0000) >> 5);

        switch (variantBits)
        {
            case 0b100:
            case 0b101:
                return Variant.RFC_VARIANT;
            case 0b110:
                return Variant.MICROSOFT_VARIANT;
            case 0b111:
                return Variant.FUTURE_VARIANT;
            default:
                return Variant.APOLLO_NCS_VARIANT;
        }
    }

    public final String toNative()
    {
        return this.toString();
    }

    public final String toString()
    {
        String format = "";

        int bytesCount = 0;
        final List<Integer> dashesPosition = Arrays.asList(new Integer[] { 4, 6, 8, 10 });

        for (byte currentByte : this.bytes)
        {
            format += String.format(
                "%c%c",
                Character.forDigit((currentByte & 0b1111_0000) >> 4, 16),
                Character.forDigit(currentByte & 0b0000_1111, 16)
            );
            bytesCount++;
            if (dashesPosition.contains(bytesCount))
            {
                format += "-";
            }
        }

        return format;
    }

    private final static byte[] interlopVersionInTimestampHigh(final byte[] bytes, final int version)
    {
        final byte[] bytesWithInterlopedVersion = bytes.clone();
        bytesWithInterlopedVersion[6] = (byte) ((version << 4) | (bytes[6] & 0b0000_1111));
        return bytesWithInterlopedVersion;
    }

    private final static byte[] interlopVariantInClockSequenceHigh(final byte[] bytes, final Variant variant)
    {
        final byte[] bytesWithInterlopedVariant = bytes.clone();

        switch (variant)
        {
            case RFC_VARIANT:
                bytesWithInterlopedVariant[8] = (byte) (0b1000_0000 | (bytesWithInterlopedVariant[8] & 0b0011_1111));
                break;
            case MICROSOFT_VARIANT:
                bytesWithInterlopedVariant[8] = (byte) (0b1100_0000 | (bytesWithInterlopedVariant[8] & 0b0001_1111));
                break;
            case FUTURE_VARIANT:
                bytesWithInterlopedVariant[8] = (byte) (0b1110_0000 | (bytesWithInterlopedVariant[8] & 0b0001_1111));
                break;
            case APOLLO_NCS_VARIANT:
                bytesWithInterlopedVariant[8] = (byte) (bytesWithInterlopedVariant[8] & 0b0111_1111);
                break;
        }

        return bytesWithInterlopedVariant;
    }
}

