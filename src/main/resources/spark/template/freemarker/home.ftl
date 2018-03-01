<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
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
        <ol>
          <#if currentPlayer??>
            <#if (playerManager.getPlayerCount() > 1)>
                <p>
                    Currently online players are:
                </p>
            </#if>
            <#list playerManager.getPlayers() as player>
                <#if player != currentPlayer>
                    <li>
                        <form action="/game" method="POST">
                            <input type="submit" name="opponent" value="${player.getName()}">
                        </form>
                    </li>
                </#if>
            </#list>
          <#else>
              <p>${playerManager.getPlayerCount()} players currently signed in.</p>
          </#if>
        </ol>
    </div>


</div>
</body>
</html>
