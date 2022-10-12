<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <a href="resume?uuid=&action=add">Add meal</a>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr >
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>
            <tr style=${meal.excess ? 'color:red' : 'color:green'}>
                <td>${DateTimeUtil.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meal?id=${meal.id}&action=edit">Update</a></td>
                <td><a href="?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
</section>
</body>
</html>