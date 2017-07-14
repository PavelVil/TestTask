<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 01.07.2017
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Найденные пользователи</title>

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
<%--Searching result--%>
<c:forEach items="${searchingUsers}" var="user">
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
                        <input type="file" id="photo-from-search-${user.id}" name="photo" accept="image/png" class="btn-default">
                        <label for="photo-from-search-${user.id}" class="btn">Обновить фото</label>
                    </div>
                    <input type="submit" value="Загрузить" class="btn btn-default">
                </form>
            </div>
        </div>
    </div>
</c:forEach>
</body>
</html>
