<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>Player Help | Web Checkers</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/game.css">
</head>
<body>
<div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
        <a href="/">my home</a> |
        <#if currentPlayer??>
            <a href="/signout">sign out [${currentPlayer.name}]</a>
        <#else>
            <a href="/signin">sign in</a>
        </#if>

    </div>

    <div class="body">
        <h3>American Rules for Checkers</h3>
        <ol>
            <li>
                The checkerboard is an 8x8 grid of light and dark squares in the famous "checkerboard" pattern. Each
                player has a dark square on the far left and a light square on his far right. The double-corner
                sometimes mentioned is the distinctive pair of dark squares in the near right corner.
            </li>
            <li>
                The checkers to be used shall be round and red and white in color. The pieces shall be placed on the
                dark squares. The starting position is with each player having twelve pieces, on the twelve dark
                squares closest to the player's edge of the board.
            </li>
            <li>
                The red player moves first.
            </li>
            <li>
                A player must move each turn. If the player cannot move, the player loses the game.
            </li>
            <li>
                In each turn, a player can make a simple move, a single jump, or a multiple jump move.
                <ul>
                    <li>
                        <i>Simple move</i>: Single pieces can move one adjacent square diagonally forward away from the
                        player. A piece can only move to a vacant dark square.
                    </li>
                    <li>
                        <i>Single jump move</i>: A player captures an opponent's piece by jumping over it, diagonally, to an
                        adjacent vacant dark square. The opponent's captured piece is removed from the board. The
                        player can never jump over, even without capturing, one of the player's own pieces. A player
                        cannot jump the same piece twice.
                    </li>
                    <li>
                        <i>Multiple jump move</i>: Within one turn, a player can make a multiple jump move with the same
                        piece by jumping from vacant dark square to vacant dark square. The player must capture one
                        of the opponent's pieces with each jump. The player can capture several pieces with a move
                        of several jumps.
                    </li>
                </ul>
            </li>
            <li>
                If a jump move is possible, the player must make that jump move. A multiple jump move must be
                completed. The player cannot stop part way through a multiple jump. If the player has a choice of
                jumps, the player can choose among them, regardless of whether some of them are multiple, or not.
            </li>
            <li>
                When a single piece reaches the row of the board furthest from the player, i.e the king-row, by reason
                of a simple move, or as the completion of a jump, it becomes a king. This ends the player's turn.
                The opponent crowns the piece by placing a second piece on top of it.
            </li>
            <li>
                A king follows the same move rules as a single piece except that a king can move and jump diagonally
                forward away from the player or diagonally backward toward the player. Within one multiple jump move,
                the jumps can be any combination of forward or backward jumps. At any point, if multiple jumps are
                available to a king, the player can choose among them.
            </li>
            <li>
                A player who loses all of their pieces to captures loses the game.
            </li>
        </ol>

        <a href="/game">return to game</a>
    </div>


</div>
</body>
</html>
