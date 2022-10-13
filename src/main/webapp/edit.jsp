<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    <br/>
    Description : <input
        type="text"
        name="description"
        value="${meal.description}"/>
    <br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/>
    <br/>
    Date :
    <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <br/>
    <input type="submit" value="Submit"/>
    <input type="button" onclick="history.back();" value="Cancel"/>
</form>
</body>
</html>