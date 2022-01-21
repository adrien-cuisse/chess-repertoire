
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.IValueObject;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidBytesCountException;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidDigitsCountException;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidVersionException;

import java.util.Random;

public final class UuidV4 implements IUuid
{
    private final static Random generator = new Random();

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

    final public boolean equals(final IUuid other)
    {
        return this.uuid.equals(other);
    }

    final public boolean equals(final IValueObject other)
    {
        return this.uuid.equals(other);
    }

    final public int version()
    {
        return 4;
    }

    final public Variant variant()
    {
        return this.uuid.variant();
    }

    final public String toNative()
    {
        return this.uuid.toNative();
    }

    final public String toString()
    {
        return this.uuid.toString();
    }

    private final static byte[] randomBytes()
    {
        final byte[] randomBytes = new byte[16];
        generator.nextBytes(randomBytes);
        return randomBytes;
    }
}
