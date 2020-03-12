<#import "add/page.ftlh" as html>
<@html.page>
    <form method="post">
        <input type="text" name="text" placeholder="Введите сообщение..."/>
        <input type="submit" value="Отправить" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@html.page>