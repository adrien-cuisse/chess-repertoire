
package com.alphonse.chess.engine;

public enum File
{
    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h');

    public final Character label;

    private File(final Character label)
    {
        this.label = label;
    }

    @Override
    public final String toString()
    {
        return this.label.toString();
    }
}
