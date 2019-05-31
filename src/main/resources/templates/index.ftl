<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>首页</title>
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}/static/js/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <style>
        body {
            background: #e3e3e3;
        }

        .login-div {
            margin: 20% auto;
            display: flex;
            justify-content: center;
            align-content: center
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="login-div">
        <form class="form-inline" role="form" method="post" action="${request.contextPath}/index">
            <div class="input-group input-group-lg">
                <div class="input-group-btn">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        性别
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#">男</a></li>
                        <li><a href="#">女</a></li>
                    </ul>
                </div>
                <input type="text" class="form-control" name="name">
            </div>
            <button type="submit" class="btn btn-default">确定</button>
        </form>

    </div>
</div>
<script src="${request.contextPath}/static/js/jquery-2.1.1.min.js"></script>
<script src="${request.contextPath}/static/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script>
</script>
</body>
</html>