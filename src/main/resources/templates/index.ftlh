<#import "add/page.ftlh" as html>
<@html.page>
    <h1>Hello, Spring!</h1>
    <form method="post" action="/login">
        <input type="text" name="name" placeholder="Имя"/>
        <input type="text" name="password" placeholder="Пароль"/>
        <button type="submit">Подтвердить</button>
    </form>
    <div>
        <#list userList as item>
            <ul>
                <li>${item.id}</li>
                <li>${item.name}</li>
                <li>${item.password}</li>
            </ul>
        </#list>
    </div>
</@html.page>