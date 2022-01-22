
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.IValueObject;

import java.util.Random;

public final class UuidV4 implements IUuid
{
    private static final Random generator = new Random();

    private Uuid uuid;

    public UuidV4()
    {
        try
        {
            this.uuid = new Uuid(randomBytes(), 4, Variant.RFC_VARIANT);
        }
        catch (InvalidBytesCountException exception)
        {
            // nope, 16 bytes were passed
        }
    }

    public UuidV4(final String rfcCompliantString) throws InvalidDigitsCountException, InvalidVersionException
    {
        this.uuid = new Uuid(rfcCompliantString, 4);
    }

    public final boolean equals(final IUuid other)
    {
        return this.uuid.equals(other);
    }

    public final boolean equals(final IValueObject other)
    {
        return this.uuid.equals(other);
    }

    public final int version()
    {
        return 4;
    }

    public final Variant variant()
    {
        return this.uuid.variant();
    }

    public final String toNative()
    {
        return this.uuid.toNative();
    }

    public final String toString()
    {
        return this.uuid.toString();
    }

    private static final byte[] randomBytes()
    {
        final byte[] randomBytes = new byte[16];
        generator.nextBytes(randomBytes);
        return randomBytes;
    }
}
