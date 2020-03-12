<#import "add/page.ftlh" as html>
<@html.page>
    <h1>Hello, Spring!</h1>
    <div>
        <#list messages as item>
            <ul>
                <li>${item.id}</li>
                <li>${item.text}</li>
                <li>${item.time}</li>
            </ul>
        </#list>
    </div>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</@html.page>