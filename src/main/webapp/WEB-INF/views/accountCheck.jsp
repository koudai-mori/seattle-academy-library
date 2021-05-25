<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>

<head>
    <meta name="description" content="社内の書籍検索や貸出を行うことができます。" />
    <meta name="robots" content="noindex,nofollow" />
    <meta http-equiv="content-type" content="text/html" charset="utf-8" />
    <title>ログイン｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
    <link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
    <link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="resources/js/hedder.js" /></script>
</head>
<body>
 <header>
 <script type="text/javascript" src="hedder.js"></script> 
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
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
    <div class="wrapper">

           
            <div class="account_authorization">
                <div class="account_authorization_form">
                    <form method="post" action="accountCheck">
                        <div class="title">アカウント認証</div>
                        <label class="label">メールアドレス</label>
                        <input type="text" class="input" name="email" id="email" autocomplete="off" required/>
                        <label class="label">パスワード</label>
                        <input type="password" class="input" id="password" name="password" required/>

                        <input type="submit" class="button primary" value="確認" />
                         <c:if test="${!empty errorMessage}">
							 <div class="error">${errorMessage}</div>
						</c:if>
                    </form>
                </div>
            </div>

        <footer>
            <div class="copyright">
                    © 2019 Seattle Consulting Co., Ltd. All rights reserved.
            </div>
        </footer>
    </div>

</body>
</html>
