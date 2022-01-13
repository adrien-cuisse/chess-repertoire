
package com.alphonse.chess.engine;

public final class Square
{
    public final Color color;

    public final File file;

    public final Rank rank;

    public Square(final Color color, final File file, final Rank rank)
    {
        this.color = color;

        this.file = file;

        this.rank = rank;
    }

    public final String toString()
    {
        return this.file.toString() + this.rank.toString();
    }
}
