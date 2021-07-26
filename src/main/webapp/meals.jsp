<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="mailTo:mxwild@gmail.com" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/meals.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <%--@elvariable id="meals" type="com.gmail.mxwild.mealcalories.model.MealTo"--%>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="com.gmail.mxwild.mealcalories.model.MealTo"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${fn:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="#">Update</a></td>
            <td><a href="#">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
