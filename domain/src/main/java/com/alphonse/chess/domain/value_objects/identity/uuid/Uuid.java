
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.IValueObject;

import java.util.Arrays;
import java.util.List;

final class Uuid implements IUuid
{
    protected final byte[] bytes;

    /**
     * @throws InvalidUuidException - if bytes count isn't 16
     */
    protected Uuid(final byte[] bytes) throws InvalidUuidException
    {
        if (bytes.length != 16)
            throw new InvalidUuidException(bytes);

        this.bytes = bytes;
    }

    /**
     * @throws InvalidUuidException - if bytes count isn't 16
     */
    protected Uuid(final byte[] bytes, final int version) throws InvalidUuidException
    {
        this(interlopVersionInTimestampHigh(bytes, version));
    }

    /**
     * @throws InvalidUuidException - if bytes count isn't 16
     */
    protected Uuid(final byte[] bytes, final int version, final Variant variant) throws InvalidUuidException
    {
        this(interlopVariantInClockSequenceHigh(bytes, variant), version);
    }

    /**
     * @throws InvalidUuidException - if uuid isn't RFC compliant, or if version mismatches with string
     */
    protected Uuid(final String uuid, final int expectedVersion) throws InvalidUuidException
    {
        if (uuid.matches("[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}") == false)
            throw new InvalidUuidException(uuid);

        final String digits = uuid.replace("-", "");

        final String versionDigit = digits.substring(12, 13);
        final int actualVersion = Integer.parseInt(versionDigit, 16);
        if (actualVersion != expectedVersion)
            throw new InvalidUuidException(uuid, expectedVersion, actualVersion);

        this.bytes = new byte[digits.length() / 2];

        for (int processedDigits = 0; processedDigits < digits.length(); processedDigits += 2)
        {
            String hexOctet = digits.substring(processedDigits, processedDigits + 2);
            this.bytes[processedDigits / 2] = (byte) Integer.parseInt(hexOctet, 16);
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
        return Variant.matchFromByte(this.bytes[8]);
    }

    public final String toNative()
    {
        return this.toString();
    }

    public final String toString()
    {
        final StringBuilder builder = new StringBuilder("");
        final List<Integer> dashesPosition = Arrays.asList(4, 6, 8, 10);

        for (int bytesCount = 0; bytesCount < this.bytes.length; bytesCount++)
        {
            if (dashesPosition.contains(bytesCount))
                builder.append("-");

            final String hexOctet = String.format("%02x", this.bytes[bytesCount]);
            builder.append(hexOctet);
        }

        return builder.toString();
    }

    private static final byte[] interlopVersionInTimestampHigh(final byte[] bytes, final int version)
    {
        final int lowNibbleBitMask = 0x0f;

        final byte highNibble = (byte) ((version & lowNibbleBitMask) << 4);
        final byte lowNibble = (byte) (bytes[6] & lowNibbleBitMask);

        final byte[] bytesWithInterlopedVersion = bytes.clone();
        bytesWithInterlopedVersion[6] = (byte) (highNibble | lowNibble);

        return bytesWithInterlopedVersion;
    }

    private static final byte[] interlopVariantInClockSequenceHigh(final byte[] bytes, final Variant variant)
    {
        final byte[] bytesWithInterlopedVariant = bytes.clone();
        bytesWithInterlopedVariant[8] = (byte) (variant.bits() | (bytes[8] & variant.unusedBitsMask()));
        return bytesWithInterlopedVariant;
    }
}

