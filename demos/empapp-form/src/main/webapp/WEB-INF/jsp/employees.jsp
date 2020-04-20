<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees</title>
</head>
<body>

    <form method="post">
        <input type="text" name="name" />
        <input type="submit" value="Create employee" />
    </form>


    <table>
        <tr><td>Name</td></tr>

        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.name}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
