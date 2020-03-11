<#import "add/page.ftlh" as html>

<@html.page>
    <div class="container">
        <div class="section">
            <div class="col-md-6 form-login">
                <form>
                    <label for="name">
                        <input type="text" placeholder="Имя" name="name"/>
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
</@html.page>