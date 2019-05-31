<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>聊天</title>
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}/static/js/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}/static/css/base.css"/>
</head>
<body>
<div class="container-fluid" id="app">
    <div class="row">
        <div class="col-md-2 col-xs-3 col-md-offset-1">
            <div class="panel panel-default">
                <div class="panel-heading">群聊列表</div>
                <!-- List group -->
                <ul class="list-group" id="groupList">
                    <a class="list-group-item" href="javascript:void(0)" @click="joinGroup(group.groupId)" v-for="group in groups"
                       v-bind:class="{ active: group.groupId==currentGroup.groupId }">{{group.name}} ({{group.cnt}})</a>
                </ul>
            </div>
        </div>
        <div class="col-md-8 col-xs-9">
            <div class="panel panel-default">
                <div class="panel-heading" id="groupHead">{{currentGroup.name}} ({{currentGroup.cnt}})</div>
                <div class="panel-body" v-bind:style="{height : chatPanelHeight + 'px', overflow: 'auto'}">
                    <ul class="list-unstyled">
                        <li v-for="item in chatMsgs" >
                            <div v-if="item.userDto.userId=='${user.userId}' && item.command==40" class="msg-mine">
                                <p>
                                    <span class="text-primary">{{ item.userDto.name }}</span>
                                    <span class="msg-avator" v-bind:style="{background: item.userDto.bgColor}">{{item.userDto.avatorText}}</span>
                                </p>
                                <div class="msg-content">
                                    <span >{{ item.msg }}</span><b></b>
                                    <p><small><em class="text-muted">{{ item.createTime }}</em></small></p>
                                </div>
                            </div>
                            <div v-else-if="item.userDto.userId!='${user.userId}' && item.command==40" class="msg-other">
                                <p>
                                    <span class="msg-avator" v-bind:style="{background: item.userDto.bgColor}">{{item.userDto.avatorText}}</span>
                                    <span class="text-primary">{{ item.userDto.name }}</span>
                                </p>
                                <div class="msg-content">
                                    <span >{{ item.msg }}</span><b></b>
                                    <p><small><em class="text-muted">{{ item.createTime }}</em></small></p>
                                </div>
                            </div>
                            <div v-else-if="item.command==20" class="msg-system">
                                <p><span class="msg-content">用户 {{ item.userDto.name }} 加入群聊</span> </p>
                            </div>
                            <div v-else-if="item.command==30" class="msg-system">
                                <p><span class="msg-content">用户 {{ item.userDto.name }} 退出群聊</span> </p>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="panel-footer" id="groupFooter">
                    <div class="input-group">
                        <input type="text" class="form-control" v-model="message.msg" @keyup.enter="chat">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="button" @click="chat"><i class="glyphicon glyphicon-send"></i></button>
                    </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${request.contextPath}/static/js/jquery-2.1.1.min.js"></script>
<script src="${request.contextPath}/static/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/static/js/vue.js"></script>
<#--<script src="${request.contextPath}/static/js/namedavatar.js"></script>-->
<script src="${request.contextPath}/static/js/base.js"></script>

<script>
    var socket;
    var app = new Vue({
        el: '#app',
        data: {
            message: {command: 1, msg: "", userId: "${user.userId!}", groupId: ""},
            currentGroup: {name: "群聊", groupId: "", cnt: 0},
            groups: [],
            chatMsgs: []
        },
        computed: {
            // 计算属性的 getter
            chatPanelHeight: function () {
                // `this` 指向 vm 实例
                return $(document).height() - $("#groupHead").height() - $("#groupFooter").height()- 50;
            }
        },
        methods: {
            joinGroup: function (groupId) {
                if (groupId == this.currentGroup.groupId) {
                    return false;
                }
                var _this = this;
                this.message.command = 20;
                this.message.groupId = groupId;
                this.send(JSON.stringify(this.message));
                this.chatMsgs = [];
            },
            send: function (msg) {
                if (!window.WebSocket) {
                    return;
                }
                if (socket.readyState == socket.OPEN) {
                    socket.send(msg);
                } else {
                    alert("The socket is not open.");
                }
            },
            chat: function () {
                this.message.command = 40;
                this.send(JSON.stringify(this.message));
                this.message.msg = '';
            },
            tranBgColor: function (name) {
                var str ='';
                for(var i=0; i<name.length; i++) {
                    str += parseInt(name[i].charCodeAt(0), 10).toString(16);
                }
                return '#' + str.slice(1, 4);
            }
        },
        created: function () {
            openIndexedDB();
            var _this = this;
            if (!window.WebSocket) {
                window.WebSocket = window.MozWebSocket;
            }
            if (window.WebSocket) {
                socket = new WebSocket("${websocketUrl}");
                socket.onmessage = function (event) {
                    console.log(event);
                    var data = $.parseJSON(event.data);
                    if (data.type==10) {
                        // 收到群组列表消息
                        _this.groups = data.msg;
                        $.each(data.msg, function (i, val) {
                            if (val.containsMe == 1) {
                                _this.currentGroup = val;
                                _this.message.groupId = val.groupId;
                                return;
                            }
                        });
                    } else if (data.type==20) {
                        // 收到普通消息
                        // var svg = namedavatar.getSVGString(data.msg.userDto.name);
                        // data.msg.userDto.avatar = namedavatar.getDataURI(svg);
                        data.msg.userDto.bgColor = _this.tranBgColor(data.msg.userDto.name);
                        data.msg.userDto.avatorText = data.msg.userDto.name.substring(0, 1);
                        _this.chatMsgs.push(data.msg);
                        addChatMessage(data.msg);
                    } else if (data.type==30) {
                        // 收到加入群聊消息
                        _this.chatMsgs.push(data.msg);
                    } else if (data.type==40) {
                        // 收到退出群聊消息
                        _this.chatMsgs.push(data.msg);
                    }
                }
                socket.onopen = function (event) {
                    console.log(event);
                    _this.message.command = 10;
                    _this.send(JSON.stringify(_this.message));
                }
                socket.onclose = function (event) {
                    console.log(event);
                }
            } else {
                alert("Your browser does not support Web Socket.");
            }
        }
    })
</script>
</body>
</html>