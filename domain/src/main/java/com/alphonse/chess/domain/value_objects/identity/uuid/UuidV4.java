
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
        catch (InvalidUuidException exception)
        {
            // nope, 16 bytes were passed
        }
    }

    /**
     * @throws InvalidUuidV4Exception - if the given uuid is not a RFC compliant Uuid V4
     */
    public UuidV4(final String uuid) throws InvalidUuidV4Exception
    {
        try
        {
            this.uuid = new Uuid(uuid, 4);
        }
        catch (InvalidUuidException exception)
        {
            throw new InvalidUuidV4Exception(uuid, exception);
        }
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

    public final class InvalidUuidV4Exception extends Exception
    {
        public InvalidUuidV4Exception(final String uuid, final Throwable previousException)
        {
            super(String.format("%s is not a valid Uuid V4", uuid), previousException);
        }
    }
}
