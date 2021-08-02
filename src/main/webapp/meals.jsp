<!doctype html>
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

<form method="get" action="meals">
    <input type="hidden" name="action" value="filter">
    <label for="startDate">From Date (inclusive)</label><br>
    <input type="date" id="startDate" name="startDate" value="${param.startDate}"><br>

    <label for="endDate">To Date (inclusive)</label><br>
    <input type="date" id="endDate" name="endDate" value="${param.endDate}"><br>

    <label for="startTime">From Time (inclusive)</label><br>
    <input type="time" id="startTime" name="startDate" value="${param.startTime}"><br>

    <label for="endTime">To Time (inclusive)</label><br>
    <input type="time" id="endTime" name="startDate" value="${param.endTime}"><br>
    <button type="submit">Filter</button>
</form>

<a href="meals?action=create">Add Meal</a>
<br>
<hr>

<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <%--@elvariable id="meals" type="com.gmail.mxwild.mealcalories.dto.MealDTO"--%>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="com.gmail.mxwild.mealcalories.dto.MealDTO"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${fn:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
