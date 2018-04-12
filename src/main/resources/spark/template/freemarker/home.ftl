<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="4">
    <title>Welcome! | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
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

    <#if message??>
        <div class="error">${message}</div>
    </#if>

    <div class="body">
        <p>Welcome to the world of online Checkers.</p>
      <#if currentPlayer??>
        <#if (playerLobby.getPlayerCount() > 1)>
            <p>
                Currently online players are:
            </p>
        </#if>
      <ol>
        <#list playerLobby.getPlayers() as player>
            <#if player != currentPlayer>
                <li>
                    <form action="/game" method="POST">
                        <#if gameManager.getGame(player)??>
                            <p>${player.getName()}</p>
                        <#else>
                            <input type="submit" name="opponent" value="${player.getName()}">
                        </#if>
                    </form>
                </li>
            </#if>
        </#list>
        </ol>
        <ol>
            <#list replayManager.getReplays(currentPlayer) as replay>
                <li>
                    <p><a href="/replay">${replay.getName()}</a></p>
                </li>
            </#list>
        </ol>
      <#else>
          <p>${playerLobby.getPlayerCount()} players currently signed in.</p>
      </#if>
    </div>


</div>
</body>
</html>
