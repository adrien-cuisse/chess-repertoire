
package com.alphonse.chess.engine;

public enum Rank
{
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2'),
    ONE('1');

    public final Character label;

    private Rank(final Character label)
    {
        this.label = label;
    }

    @Override
    public final String toString()
    {
        return this.label.toString();
    }
}
