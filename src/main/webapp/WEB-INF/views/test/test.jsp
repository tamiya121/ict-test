<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
</head>
<body>
난 뷰폴더 및에 테스트 폴더 밑에 테스트 제이에스피.<br>

<c:forEach items="${list}" var="dept">
	번호 : ${dept.deptno} , 부서명 : ${dept.deptname},
	지역 : ${dept.deptloc }<br>
</c:forEach>
</body>
</html>