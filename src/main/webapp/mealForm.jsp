<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <jsp:useBean id="meal" type="com.gmail.mxwild.mealcalories.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">

        <label for="dateTime">DateTime:</label><br>
        <input type="datetime-local" id="dateTime" value="${meal.dateTime}" name="dateTime" required><br>

        <label for="description">Description:</label><br>
        <input type="text" id="description" value="${meal.description}" name="description" required><br>

        <label for="calories">Calories:</label><br>
        <input type="number" id="calories" value="${meal.calories}" name="calories" required><br>

        <button type="submit">Save</button><br>
        <button type="button" onclick="window.history.back()">Cancel</button><br>
    </form>
</body>
</html>
