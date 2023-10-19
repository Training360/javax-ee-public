<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees</title>
</head>
<body>

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
