<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 30.06.2017
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Главная</title>
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />
    <spring:url value="/resources/css/profile.css" var="profile"/>
    <link href="${profile}" rel="stylesheet" />

    <link rel="stylesheet" href="http://bootstraptema.ru/plugins/2015/bootstrap3/bootstrap.min.css" />
    <link rel="stylesheet" href="http://bootstraptema.ru/plugins/font-awesome/4-4-0/font-awesome.min.css" />
    <style>
        input[type=file]{
            display: none;
        }
    </style>


</head>
<body>
<%--Main menu--%>
<ul class="nav nav-pills">
    <li><a href="/users">На главную</a></li>
    <li><a href="<c:url value='/json/getAll'/>">Получить информацию в JSON</a></li>
    <li><a href="<c:url value='/xml/getAll'/>">Получить информацию в XML</a></li>
    <%--<li><a href="/pdf">Получить информацию в PDF</a></li>--%>
    <li><a href="/excel">Получить информацию в Excel</a></li>
</ul>
<%--Search form--%>
<form method="post" action="/search/" class="form-inline">
    <div class="form-group">
    <input type="text" name="userName" class="form-control">
    </div>
    <input type="submit" value="Найти" class="btn btn-default">
</form>
<%--User list--%>
<c:forEach items="${listUsers}" var="user">
    <div class="col-md-3">
        <div class="panel block-profile">
            <div style="background-image: url('http://bootstraptema.ru/images/bg/bg-4.png')" class="panel-body text-center bg-center">
                <div class="row profile-name">
                    <div class="col-xs-12 text-white">
                        <img src="/ShowPhoto?id=${user.id}" alt="Image" class="img-thumbnail img-circle profile-images">
                        <h3>${user.name}</h3>
                        <p>
                            ${user.email}</p>
                    </div>
                </div>
            </div>
            <div class="list-group">
                <span class="list-group-item">
                <span class="label "><em class="text-muted"></em>Дата создания</span>
                    <span class="label label-danger pull-right">${user.createDate}</span>
                </span>
                <span class="list-group-item">
                <span class="label "><em class="text-muted"></em>Возраст</span>
                <span class="label label-danger pull-right">${user.age}</span>
                </span>
                <span class="list-group-item">
                <span class="label "><em class="text-muted"></em>Город</span>
                    <span class="label label-danger pull-right">${user.city}</span>
                </span>
                <span class="list-group-item">
                <span class="label "><em class="text-muted"></em>Администратор</span>
                     <c:if test="${user.admin}"> <span class="label label-danger pull-right">Да</span></c:if>
                    <c:if test="${!user.admin}"><span class="label label-danger pull-right">Нет</span></c:if>
                </span>
                <a href="/edit/${user.id}" class="list-group-item">Обновить</a>
                <a href="/delete/${user.id}" class="list-group-item">Удалить</a>

                <form action="/uploadPhoto" method="post" enctype="multipart/form-data" class="form-inline list-group-item-form">
                    <input type="hidden" name="id" value="${user.id}">
                    <div class="form-group">
                        <input type="file" id="photo-from-list-${user.id}" name="photo" accept="image/png" class="btn-default">
                        <label for="photo-from-list-${user.id}" class="btn">Обновить фото</label>
                    </div>
                    <input type="submit" value="Загрузить" class="btn btn-default">
                </form>

            </div>
        </div>
    </div>
</c:forEach>
<%--Form for add/update--%>
    <div>
    <c:url var="addAction" value="/users/add"/>
<form:form action="${addAction}" commandName="user" enctype="multipart/form-data">
<c:if test="${!empty user.name}">
<div class="form-group">
            <form:label path="id">
                <spring:message text="ID"/>
            </form:label>
            <form:input path="id" readonly="true" size="8" disabled="true"/>
            <form:hidden path="id"/>
</div>
</c:if>
<div class="form-group">
            <form:label path="name">
                <spring:message text="Имя"/>
            </form:label>
            <form:input path="name"/>
            <form:errors path="name"/>
</div>
<div class="form-group">
            <form:label path="age">
                <spring:message text="Возраст"/>
            </form:label>
            <form:input path="age"/>
            <form:errors path="age"/>
</div>
    <div class="form-group">
        <form:label path="city">
            <spring:message text="Город"/>
        </form:label>
        <form:input path="city"/>
        <form:errors path="city"/>
    </div>
    <div class="form-group">
        <form:label path="email">
            <spring:message text="Email"/>
        </form:label>
        <form:input path="email"/>
    </div>
<div class="checkbox">
    <form:checkbox path="admin"/>
                <form:label path="admin">
                    <spring:message text="Администратор"/>
                </form:label>
</div>
        <div class="checkbox">
    <input type="file" id = "photo-from-form" name="photoFile" accept="image/png" class="btn">
            <label for="photo-from-form" class="btn">Выбрать фото</label>
        </div>
    <c:if test="${!empty user.name}">

        <input type="submit"
               value="<spring:message text="Редактировать пользователя"/>" class="btn"/>
    </c:if>
    <c:if test="${empty user.name}">
        <input type="submit"
               value="<spring:message text="Добавить пользователя"/>" class="btn"/>
    </c:if>
    <c:if test="${empty user.name}">
        <input type="reset" value="Отмена" class="btn">
    </c:if>
    <c:if test="${!empty user.name}">
        <a href="/users" class="btn">Отмена</a>
    </c:if>

</form:form>
</div>
<%--Pagination--%>
<div class="pagination">
    <ul>
    <c:url value="/users" var="prev">
        <c:param name="page" value="${page-1}"/>
    </c:url>
    <c:if test="${page > 1}">
        <li><a href="<c:out value="${prev}" />" class="pn prev">Назад</a></li>
    </c:if>
    <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
        <c:choose>
            <c:when test="${page == i.index}">
                <c:url value="/users" var="url"/>
                <li><a href='<c:out value="${url}"/>'>${i.index}</a></li>
            </c:when>
            <c:otherwise>
                <c:url value="/users" var="url">
                    <c:param name="page" value="${i.index}"/>
                </c:url>
               <li><a href='<c:out value="${url}" />'>${i.index}</a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:url value="/users" var="next">
        <c:param name="page" value="${page + 1}"/>
    </c:url>
    <c:if test="${page + 1 <= maxPages}">
        <li><a href='<c:out value="${next}" />' class="pn next">Вперед</a></li>
    </c:if>
        </ul>
</div>

</body>
</html>
