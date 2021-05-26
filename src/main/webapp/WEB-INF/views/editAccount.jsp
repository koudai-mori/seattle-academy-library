<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<!doctype html>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
 <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" type="text/css">
 <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/hedder.js" /></script>
</head>
<body class="wrapper">
<script type="text/javascript" src="hedder.js"></script> 
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logoByMori.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
             <div class="hamburger">
                <span></span> <span></span> <span></span> 
            </div>
            <nav class="globalMenuSp">
                <ul class=hambergermenu>
                    <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                    <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li> 
                    <li><a href="<%=request.getContextPath()%>/account" class="account">アカウント修正</a></li>
                </ul>
            </nav>
        </div>
    </header>
<body>
    <div class="wrapper">
        <main>
            <div class="account_authorization">
                <div class="account_authorization_form">
                    <form method="post" action="editAccount">
                        <div class="title">アカウントの修正</div>
                        <input type="hidden" name="userId" value="${userInfo.userId}"> 
                        <label class="label">メールアドレス</label>
                        <c:if test="${!empty att1}">
                            <div class="error">${att1}</div>
                        </c:if>
                        <input type="email" class="input"  name="email"  value="${userInfo.email}">
                        <label class="label">パスワード</label> 
                        <input type="password" class="input"  name="password" value="${userInfo.password}">
                        <label class="label">パスワード（確認用）</label>
                        <input type="password" class="input"  name="passwordForCheck" value="${userInfo.password}">
                         <button type="submit" value="${userInfo.userId} " class="account_update_buttom">更新する</button>
                     <!--    <input type="submit" class="button primary" value="更新する"> -->
                        <c:if test="${!empty att3}">
                            <div class="error">${att3}</div>
                        </c:if>
                    </form>
                </div>
            </div>
        </main>
        <footer>
            <div class="copyright">© 2019 Seattle Consulting Co., Ltd. All rights reserved.</div>
        </footer>
    </div>
</body>
</html>