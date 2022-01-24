
package com.alphonse.chess.domain.value_objects.nickname;

import com.alphonse.chess.domain.value_objects.nickname.Nickname.InvalidNicknameException;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public final class NicknameTest
{
    public final static Object[][] trimming()
    {
        return new Object[][] {
            { " foo", "foo" },
            { "bar ", "bar" },
            { "b   az", "baz"},
            { "foo . bar - a _ baz", "foo.bar-a_baz" }
        };
    }

    @ParameterizedTest
    @MethodSource("trimming")
    public final void hasNoWhitespaces(final String pollutedNickname, final String trimmedNickname) throws InvalidNicknameException
    {
        // given a nickname
        final Nickname instance = new Nickname(pollutedNickname);

        // when checking its actual format
        final String actualNickname = instance.toString();

        // then it should be the expected trimmed one
        assertEquals(
            "Nickname shouldn't contain useless whitespaces",
            trimmedNickname,
            actualNickname
        );
    }

    @Test
    public final void isAtLeast2CharactersLong()
    {
        // given nickname with invalid length (after trimming)
        final String invalidNickname = "i  ";

        // when trying to create an instance from it
        ThrowingRunnable instanciation = () -> {
            new Nickname(invalidNickname);
        };

        // then an exception should be thrown
        assertThrows(
            "Nickname shouldn't be instantiated when its length is lesser than 2",
            InvalidNicknameException.class,
            instanciation
        );
    }

    @Test
    public final void isAtMost16CharactersLong()
    {
        // given nickname with invalid length
        final String invalidNickname = "01234567890123456";

        // when trying to create an instance from it
        ThrowingRunnable instanciation = () -> {
            new Nickname(invalidNickname);
        };

        // then an exception should be thrown
        assertThrows(
            "Nickname shouldn't be instantiated when its length is greater than 16",
            InvalidNicknameException.class,
            instanciation
        );
    }

    public final static Object[][] invalidCharacters()
    {
        return new Object[][] {
            { '!' }, { '"' }, { '#' }, { '$' }, { '%' },
            { '&' }, { '\'' }, { '(' }, { ')' }, { '*' },
            { '+' }, { ',' }, { '/' }, { ':' }, { ';' },
            { '<' }, { '=' }, { '>' }, { '?' }, { '@' },
            { '[' }, { '\\' }, { ']' }, { '^' }, { '`' },
            { '{' }, { '|' }, { '}' },{ '~' }, { '°' },
        };
    }

    @ParameterizedTest
    @MethodSource("invalidCharacters")
    public final void expectsValidSymbols(final Character invalidSymbol)
    {
        // given a nickname containing invalid symbols
        final String invalidNickname = "012345678901234" + invalidSymbol.toString();

        // when trying to create an instance from it
        ThrowingRunnable instanciation = () -> {
            new Nickname(invalidNickname);
        };

        // then an exception should be thrown
        assertThrows(
            "Nickname shouldn't contain symbol",
            InvalidNicknameException.class,
            instanciation
        );
    }

    public final static Object[][] validCharacters()
    {
        return new Object[][] {
            { '-' }, { '.' }, { '_' },
        };
    }

    @ParameterizedTest
    @MethodSource("validCharacters")
    public final void mustStartWithAlphaNum(final Character validSymbol)
    {
        // given a nickname starting with a valid symbol
        final String invalidNickname = validSymbol.toString() + "012345678901234";

        // when trying to create an instance from it
        ThrowingRunnable instanciation = () -> {
            new Nickname(invalidNickname);
        };

        // then an exception should be thrown
        assertThrows(
            "Nickname should start with an alpha-numeric character",
            InvalidNicknameException.class,
            instanciation
        );
    }
}
