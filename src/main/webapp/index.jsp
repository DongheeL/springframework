<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시작화면</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script >
	function getJsonData() {
		alert("getJsonData() 시작");
		$.ajax("getJson.do", {
			type : "get",
			dataType : "json",	//응답받을 데이터타입
			//data: {key: value}, //서버쪽으로 전달할 데이터 지정
			success : function (result) {	//성공했을 때 실행할 함수
				alert("성공 result : "+result);
				console.log("result : "+JSON.stringify(result));
				console.log(result);
				var dispHtml = "";
				dispHtml += "<ul>";
				$.each(result, function(index, obj){
					dispHtml += "<li>";	
					dispHtml += this.seq +", ";
					dispHtml += this.title +", ";
					dispHtml += this["writer"] +", ";
					dispHtml += obj["regdate"] +", ";
					dispHtml += obj["cnt"] +", ";
					dispHtml += "</li>";	
				});
				dispHtml += "</ul>";
				$("#listDisp").html(dispHtml);
			},	
			error : function (){
				alert("실패");
			}
		})
	}
</script>
</head>
<body>
<div id="container">
	<h1>게시판 프로그램</h1>
	<hr>
	<p><a href="login.do">로그인 페이지로 이동(GET)</a></p>
	<p><a href="getJson.do">JSON 데이터 받기</a></p>
	<hr>
	<hr>
	<p><a href="javascript:getJsonData()">JSON 데이터 받아서 화면 표시</a></p>
</div>
<hr>
<div id="listDisp">
	<ul>
		<li>데이터 가져와서 출력하기</li>
	</ul>
</div>	


</body>
</html>





