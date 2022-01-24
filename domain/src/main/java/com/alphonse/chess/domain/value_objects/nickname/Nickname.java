package com.alphonse.chess.domain.value_objects.nickname;

import com.alphonse.chess.domain.value_objects.IValueObject;

public final class Nickname implements IValueObject
{
    private static final int MINIMUM_LENGHT = 2;

    private static final int MAXIMUM_LENGTH = 16;

    private final String nickname;

    public Nickname(final String nickname) throws InvalidNicknameException
    {
        this.nickname = nickname.trim().replaceAll("\\s+", "");

        if (this.nickname.length() < MINIMUM_LENGHT)
            throw new InvalidNicknameException(nickname, "more", MINIMUM_LENGHT);
        else if (this.nickname.length() > MAXIMUM_LENGTH)
            throw new InvalidNicknameException(nickname, "less", MAXIMUM_LENGTH);
        else if (this.nickname.matches("([a-zA-Z0-9]+[a-zA-Z0-9\\-_.]*)") == false)
            throw new InvalidNicknameException(nickname);
    }

    public final boolean equals(final IValueObject other)
    {
        return false;
    }

    public final String toString()
    {
        return this.nickname;
    }

    public final class InvalidNicknameException extends Exception
    {
        public InvalidNicknameException(final String nickname)
        {
            super(String.format(
                "Invalid nickname '%s', it can't contain symbols other than '-', '_', '.' and must start with alpha-numeric character",
                nickname
            ));
        }

        public InvalidNicknameException(final String nickname, final String adjective, final int length)
        {
            super("Invalid nickname '" + nickname + "', it must be " + adjective + " than " + length + " characters long");
        }
    }
}
