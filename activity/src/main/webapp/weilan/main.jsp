<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>众筹计划表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <style>
        body, table, tr, td {
            margin: 0;
            padding: 0;
            border: none;
            border-spacing: 0;
        }

        .w100 {
            width: 100%;
        }

        .po-re {
            position: relative;
        }

        .po-abs {
            position: absolute;
        }

        .form-div {
            padding: 15px 34px;
        }

        span, label, button {
            font-family: "Microsoft YaHei";
            font-size: 14px;
        }

        .title-icon {
            height: 14px;
            top: 3px;
            left: 8px;
        }

        .title span {
            padding: 2px 10px 3px 30px;
            background: #8B6C44;
            color: #ffffff;
            font-size: 14px;
        }

        .form-group {
            border: 1px solid #a2a2a2;
            background: #ffffff;
            margin-bottom: 12px;
        }

        .form-group .img {
            top: 7px;
            height: 16px;
            left: 9px;
        }

        .form-group .label {
            top: 6px;
            left: 30px;
        }

        .form-group input[type=text],
        .form-group input[type=number] {
            height: 30px;
            padding-left: 110px;
            border: none;
            font-family: "Microsoft YaHei";
        }

        .form-group textarea {
            height: 65px;
            padding: 7px 9px 7px 96px;
            border: none;
            font-family: "Microsoft YaHei";
        }

        .card {
            width: 15px;
            height: 13px !important;
            top: 9px !important;
        }

        .demo-label {
            height: 30px;
            display: inline-block;
            float: left;
            padding-top: 6px;
        }

        .demo-radio {
            display: none
        }

        .demo-radioInput {
            background-color: #fff;
            border: 1px solid #333;
            border-radius: 50%;
            display: inline-block;
            height: 16px;
            margin-right: 6px;
            margin-top: -1px;
            vertical-align: bottom;
            width: 16px;
            line-height: 1
        }

        .demo-radio:checked + .demo-radioInput:after {
            background-color: #8B6C44;
            border-radius: 50%;
            content: "";
            display: inline-block;
            height: 12px;
            margin: 2px;
            width: 12px
        }

        .demo-radioInput, .demo-radio:checked + .demo-radioInput:after {
            border-radius: 50%
        }

        .submit {
            background: #8B6C44;
            border: 1px solid #8B6C44;
            color: #fff;
            padding: 2px 8px 2px 26px;
            font-size: 15px;

        }

        .btn-ic {
            left: 7px;
            top: 4px;
            height: 18px;
        }

        .text-right {
            text-align: right;
        }

    </style>
</head>
<body>
<table class="w100">
    <tr class="w100">
        <td class="w100">
            <img src="images/img-1.png" class="w100" alt="">
        </td>
    </tr>
    <tr class="w100">
        <td class="w100">
            <img src="images/img-2.png" class="w100" alt="">
        </td>
    </tr>
    <tr class="w100">
        <td class="w100">
            <img src="images/img-3.png" class="w100" alt="">
        </td>
    </tr>
    <tr class="w100">
        <td class="w100">
            <img src="images/img-4.png" class="w100" alt="">
        </td>
    </tr>
    <tr class="w100">
        <td class="w100">
            <img src="images/img-5.png" class="w100" alt="">
        </td>
    </tr>
    <tr class="w100">
        <td class="w100 po-re">
            <img src="images/img-6.png" class="w100 po-abs" style="min-height: 449px" alt="">
            <div class="po-re form-div">
                <div class="title po-re">
                    <img src="images/ic-pen.png" class="title-icon po-abs" alt="">
                    <span>意向认筹信息登记</span>
                </div>
                <form action="./weilan/add" method="post" enctype="application/json" style="margin-top: 15px">
                    <div class="form-group po-re">
                        <img src="images/ic-people.png" class="po-abs img" alt="">
                        <label class="po-abs label">申请人姓名:</label>
                        <input type="text" class="w100" name="username">
                    </div>
                    <div class="form-group po-re">
                        <img src="images/ic-sshy.png" class="po-abs img" alt="">
                        <label class="po-abs label">从事行业:</label>
                        <input type="text" class="w100" style="padding-left: 96px" name="job">
                    </div>
                    <div class="form-group po-re">
                        <img src="images/ic-card.png" class="po-abs img card" alt="">
                        <label class="po-abs label">所任职务:</label>
                        <input type="text" class="w100" style="padding-left: 96px" name="position">
                    </div>
                    <div class="form-group po-re">
                        <img src="images/ic-phone.png" class="po-abs img" alt="">
                        <label class="po-abs label">联系方式:</label>
                        <input type="number" class="w100" style="padding-left: 96px" name="phone">
                    </div>
                    <div class="form-group po-re">
                        <img src="images/ic-wechat.png" class="po-abs img" alt="">
                        <label class="po-abs label">微信号:</label>
                        <input type="text" class="w100" style="padding-left: 82px" name="weChat">
                    </div>
                    <div class="form-group po-re" style="height: 30px">
                        <img src="images/ic-money.png" class="po-abs img" alt="">
                        <label class="po-abs label">意投金额:</label>
                        <label class="demo-label" style="padding-left: 100px">
                            <input id="moneyLess" class="demo-radio" type="radio" name="money" value='3' checked/>
                            <span class="demo-radioInput"></span>3万元
                        </label>
                        <label class="demo-label" style="padding-left: 15px">
                            <input id="moneyMore" class="demo-radio" type="radio" name="money" value='6'/>
                            <span class="demo-radioInput"></span>6万元
                        </label>
                    </div>
                    <div class="form-group po-re">
                        <img src="images/ic-info.png" class="po-abs img" alt="">
                        <label class="po-abs label">相关建议:</label>
                        <textarea class="w100" placeholder="选填" name="info"></textarea>
                    </div>
                    <div class="text-right">
                        <button type="button" class="po-re submit" id="submit">
                            <img src="images/ic-submit.png" alt="" class="po-abs btn-ic">确认提交
                        </button>
                    </div>
                </form>
            </div>
        </td>
    </tr>
</table>


<script src="http://cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script>


    $("#submit").click(function () {
        if ($("input[name='username']").val().length <= 0) {
            alert("请输入姓名！");
            return;
        }
        if ($("input[name='job']").val().length <= 0) {
            alert("请输入从事行业！");
            return;
        }
        if ($("input[name='phone']").val().length <= 0) {
            alert("请输入手机号！");
            return;
        }
        if ($("input[name='position']").val().length <= 0) {
            alert("请输入所任职务！");
            return;
        }
        if ($("input[name='weChat']").val().length <= 0) {
            alert("请输入微信号！");
            return;
        }

//        给radio赋值
        $("#moneyLess").val("3");
        $("#moneyMore").val("6");
//        var radio = document.getElementsByName("money");

        var postData = {};
        postData.username = $("input[name='username']").val();
        postData.job = $("input[name='job']").val();
        postData.phone = $("input[name='phone']").val();
        postData.position = $("input[name='position']").val();
        postData.weChat = $("input[name='weChat']").val();
        postData.info = $("textarea[name='info']").val();
        postData.money = $('input:radio[name="money"]:checked').val();

//        alert(JSON.stringify(postData));
//        $("form").submit();
//        $.post("./add", postData, function (data) {
//            console.info(data)
//        })

        var url = "./add";
        $.ajax({
            type: "POST",
//            dataType: "json",
            contentType: "application/json",
            url: url,
            data: JSON.stringify(postData),
            success: function (data) {
//                console.info(data);
                if (data.success) alert("提交完成！")

            }
        });
    });

    $("input").each(function (index) {
        $(this).val($.cookie('input' + index));
        $(this).change(function () {
            $.cookie('input' + index, $(this).val());
        })

    });
</script>
</body>
</html>