
package com.alphonse.chess.domain.value_objects.identity.uuid;

public final class InvalidVersionException extends Exception
{
    public InvalidVersionException(final String uuid, final int expectedVersion)
    {
        super(String.format("The uuid %s doesn't match version %d", uuid, expectedVersion));
    }
}
