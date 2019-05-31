package com.zzitbar.webim;

/**
 * @auther H2102371
 * @create 2019-05-06 下午 01:56
 * @Description
 */
public class Constants {

    /**
     * 消息命令
     * 10：连接成功
     * 20：加入群聊
     * 30：退出群聊
     * 40：发送消息
     * 99：断开连接
     */
    public enum MESSAGE_COMMAND {
        CONNECTED(10), JOIN_GROUP(20), QUIT_GROUP(30), CHAT(40), DISCONNECT(99);

        private Integer value;

        MESSAGE_COMMAND(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    /**
     * 响应消息类型
     * 10：群组列表
     * 20：普通消息
     * 30：加入群聊
     * 40：退出群聊
     */
    public enum RESPONSE_TYPE {
        GROUP_LIST(10), MESSAGE(20), JOIN_GROUP(30), QUIT_GROUP(40);

        private Integer value;

        RESPONSE_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
