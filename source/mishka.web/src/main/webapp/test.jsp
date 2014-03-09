<%@ page import="ru.org.icad.mishka.app.process.calculation.ScheduleCalculator" %>
<%@ page import="org.apache.commons.lang3.exception.ExceptionUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    ScheduleCalculator calculator = new ScheduleCalculator();

    calculator.calculateSchedule();

    out.println("Calculation has been finished successfully. Please, check server.log for more details.");
%>
</body>
</html>
