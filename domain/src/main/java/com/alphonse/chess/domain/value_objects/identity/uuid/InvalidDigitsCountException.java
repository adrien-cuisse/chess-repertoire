
package com.alphonse.chess.domain.value_objects.identity.uuid;

public final class InvalidDigitsCountException extends Exception
{
    public InvalidDigitsCountException(final String uuid, final int bytesCount)
    {
        super(String.format("Expected 32 hex digits, got %d (%s)", bytesCount, uuid.length()));
    }
}
