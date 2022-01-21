
package com.alphonse.chess.domain.value_objects.identity.uuid;

import com.alphonse.chess.domain.value_objects.identity.IIdentity;

/**
 * A RFC-4122 compliant UUID
 *
 * @see https://datatracker.ietf.org/doc/html/rfc4122
 */
public interface IUuid extends IIdentity<String>
{
    public int version();

    public Variant variant();

    public enum Variant
    {
        APOLLO_NCS_VARIANT(0),
        RFC_VARIANT(2),
        MICROSOFT_VARIANT(6),
        FUTURE_VARIANT(7);

        private final int value;

        private Variant(final int value)
        {
            this.value = value;
        }

        public final byte mostSignificantBits()
        {
            return (byte) (this.value << 4);
        }
    }
}
