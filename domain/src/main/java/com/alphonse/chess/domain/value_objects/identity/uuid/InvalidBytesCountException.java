
package com.alphonse.chess.domain.value_objects.identity.uuid;

public final class InvalidBytesCountException extends Exception
{
    public InvalidBytesCountException(final byte[] bytes)
    {
        super(String.format("Expected 16 bytes, got %d", bytes.length));
    }
}
