<html lang="en">
    <head><title>Spring</title></head>
    <body>
        <h1>Hello, Spring!</h1>
        <form method="post">
            <input type="text" name="email" placeholder="email"/>
            <input type="text" name="password" placeholder="password"/>
            <button type="submit">Подтвердить</button>
        </form>
        <div>
            <#list userList as item>
                <ul>
                    <li>${item.id}</li>
                    <li>${item.email}</li>
                    <li>${item.password}</li>
                </ul>
            </#list>
        </div>
    </body>
</html>