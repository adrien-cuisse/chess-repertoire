
package com.alphonse.chess.engine;

import java.util.Optional;

public final class Board
{
    private final Square[][] squares;

    public Board()
    {
        this.squares = new Square[8][8];

        for (Rank rank : Rank.values())
        {
            int rowIndex = this.index(rank);

            for (File file : File.values())
            {
                int columnIndex = this.index(file);

                Color color = this.color(file, rank);
                Square square = new Square(color, file, rank);

                this.squares[rowIndex][columnIndex] = square;
            }
        }
    }

    public final Optional<Square> get(String squareLabel)
    {
        char fileLabel = squareLabel.charAt(0);
        File file = this.file(fileLabel);
        int fileIndex = this.index(file);

        char rankLabel = squareLabel.charAt(1);
        Rank rank = this.rank(rankLabel);
        int rankIndex = this.index(rank);

        boolean fileLabelIsInvalid = fileIndex > 8;
        boolean rankLabelIsInvalid = rankIndex > 8;

        if (fileLabelIsInvalid || rankLabelIsInvalid) {
            return Optional.empty();
        }

        Square square = this.squares[rankIndex][fileIndex];

        return Optional.of(square);
    }

    public final void print()
    {
        for (Square[] row : this.squares)
        {
            for (Square square : row)
            {
                System.out.print(square + " ");
            }
            System.out.println("");
        }
    }

    private final int index(File file)
    {
        return file.label - File.valueOf("A").label;
    }

    private final File file(char label)
    {
        return File.values()[label - 'a'];
    }

    private final int index(Rank rank)
    {
        return Rank.valueOf("EIGHT").label - rank.label;
    }

    private final Rank rank(char label)
    {
        return Rank.values()['8' - label];
    }

    private final Color color(File file, Rank rank)
    {
        int combinedIndex = this.index(file) + this.index(rank);
        int colorIndex = (combinedIndex + 1) % 2;

        return Color.values()[colorIndex];
    }
}
