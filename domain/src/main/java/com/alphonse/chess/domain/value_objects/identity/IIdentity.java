
package com.alphonse.chess.domain.value_objects.identity;

import com.alphonse.chess.domain.value_objects.IValueObject;

/**
 * An unique identity, which makes entities what they are
 */
public interface IIdentity<T> extends IValueObject
{
    /**
     * @return T - the native representation of the identity
     */
    public T toNative();

    public String toString();
}
