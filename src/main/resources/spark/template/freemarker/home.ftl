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
        <a href="/signin">sign in</a>

        <#if currentPlayer??>
            | ${currentPlayer.getName()}
        </#if>

    </div>

    <div class="body">
        <p>Welcome to the world of online Checkers.</p>
        <ol>
          <#if currentPlayer??>
            <#list playerLobby.getPlayerNames() as name>
                <#if name != currentPlayer.getName()>
                    <li>${name}</li>
                </#if>
            </#list>
          </#if>
        </ol>
    </div>


</div>
</body>
</html>
