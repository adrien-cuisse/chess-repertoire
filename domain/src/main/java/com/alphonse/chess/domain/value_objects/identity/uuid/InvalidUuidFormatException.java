
package com.alphonse.chess.domain.value_objects.identity.uuid;

public final class InvalidUuidFormatException extends Exception
{
    public InvalidUuidFormatException(final String message)
    {
        super(message);
    }
}
