<#import "add/page.ftlh" as html>
<@html.page>
    <#list messages as item>
        <ul>
            <li>${item.text}</li>
            <li>${item.time}</li>
            <li>${item.authorName}</li>
        </ul>
    </#list>
    <form method="post">
        <input type="text" name="text" placeholder="Введите сообщение..."/>
        <input type="submit" value="Отправить" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@html.page>