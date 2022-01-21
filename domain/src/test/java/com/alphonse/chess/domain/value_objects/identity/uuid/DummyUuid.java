
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.IValueObject;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidBytesCountException;
import com.alphonse.chess.domain.value_objects.identity.uuid.Uuid.InvalidDigitsCountException;

import java.util.Arrays;

final class DummyUuid implements IUuid
{
    private Uuid uuid = null;

    static final byte[] nullBytes()
    {
        byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte) 0);
        return bytes;
    }

    DummyUuid()
    {
        try
        {
            this.uuid = new Uuid(nullBytes());
        }
        catch (InvalidBytesCountException exception)
        {
            // nope, 16 bytes were passed
        }
    }

    DummyUuid(final byte[] bytes)
    {
        try
        {
            this.uuid = new Uuid(bytes);
        }
        catch (InvalidBytesCountException exception)
        {
            // nope, tests pass 16 bytes
        }
    }

    DummyUuid(final byte[] bytes, final int version)
    {
        try
        {
            this.uuid = new Uuid(bytes, version);
        }
        catch (InvalidBytesCountException exception)
        {
            // nope, tests pass 16 bytes
        }
    }

    DummyUuid(final byte[] bytes, final int version, final Variant variant)
    {
        try
        {
            this.uuid = new Uuid(bytes, version, variant);
        }
        catch (InvalidBytesCountException exception)
        {
            // nope, tests pass 16 bytes
        }
    }

    DummyUuid(final String rfcCompliantString)
    {
        try
        {
            this.uuid = new Uuid(rfcCompliantString);
        }
        catch (InvalidDigitsCountException exception)
        {
            // nope, tests pass 32 digits
        }
    }

    final public boolean equals(final IValueObject other)
    {
        return this.uuid.equals(other);
    }

    final public int version()
    {
        return this.uuid.version();
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
}
