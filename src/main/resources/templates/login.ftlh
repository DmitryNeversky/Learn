<#import "add/page.ftlh" as html>

<@html.page>
    <div class="container">
        <div class="section">
            <div class="row">
                <div class="col-md-6 form-login">
                    <form method="post" action="/login">
                        <label for="name">
                            <input type="text" placeholder="Имя" name="username"/>
                        </label>
                        <label for="password">
                            <input type="password" placeholder="Пароль" name="password"/>
                        </label>
                           <input type="submit" value="Войти"/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="col-md-6">
                    form for vk/facebook/twitter
                </div>
            </div>
        </div>
    </div>
</@html.page>