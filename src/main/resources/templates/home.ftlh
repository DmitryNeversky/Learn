<#import "add/page.ftlh" as html>
<@html.page>
    <h1>Hello, Spring!</h1>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</@html.page>