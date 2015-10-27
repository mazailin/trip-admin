package com.ulplanet.trip.modules.tms.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.ulplanet.trip.common.security.Digests;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.Encodes;
import com.ulplanet.trip.common.utils.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Writer;
import java.util.*;

/**
 * Created by zhangxd on 15/8/14.
 */
public class ChatIdMaker {

    public static Map<String, String> parseXml(String text) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

        Document document = DocumentHelper.parseText(text);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());


        return map;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    static class XmlMessage {
        private String name;
        private String type;
        private String permission;
        private String declared;

        public XmlMessage(String name, String type, String permission,
                          String declared) {
            super();
            this.name = name;
            this.type = type;
            this.permission = permission;
            this.declared = declared;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getPermission() {
            return permission;
        }
        public void setPermission(String permission) {
            this.permission = permission;
        }
        public String getDeclared() {
            return declared;
        }
        public void setDeclared(String declared) {
            this.declared = declared;
        }
    }

    public static String makeChatId(String name, String description) {

        try {
            XmlMessage xmlMessage = new XmlMessage(name, "0", "0", description);

            xstream.alias("Request", xmlMessage.getClass());
            String xmlData = xstream.toXML(xmlMessage);

            String timestamp = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            String account = "70063b6a3b1a11e58b68ac853d9d52fd";
            String token = "04b32bdb8b9caa3ffdc05348034188c2";

            HttpClient client = HttpClientUtils.getConnection();
            String sig = account + token + timestamp;
            sig = Digests.md5(sig.getBytes()).toString().toUpperCase();
            String url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/" + account + "/Group/CreateGroup?sig="+sig;

            HttpUriRequest post = HttpClientUtils.getRequestMethod(xmlData, url, "post");
            post.addHeader("Accept", "application/xml");
            post.addHeader("Content-Type", "application/xml;charset=utf-8");
            String authorization = Encodes.encodeBase64(account + ":" + timestamp);
            post.addHeader("Authorization", authorization);

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");
                Map<String, String> xmlMap = parseXml(message);
                return xmlMap.get("groupId");
            } else {
                System.out.println("请求失败");
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    static class InviteXmlMessage {
        private String groupId;
        private List<String> members;
        private String confirm;
        private String declared;

        public InviteXmlMessage(String groupId, List<String> members, String confirm, String declared) {
            this.groupId = groupId;
            this.members = members;
            this.confirm = confirm;
            this.declared = declared;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public List<String> getMembers() {
            return members;
        }

        public void setMembers(List<String> members) {
            this.members = members;
        }

        public String getConfirm() {
            return confirm;
        }

        public void setConfirm(String confirm) {
            this.confirm = confirm;
        }

        public String getDeclared() {
            return declared;
        }

        public void setDeclared(String declared) {
            this.declared = declared;
        }
    }

    public static boolean inviteJoinGroup(String groupid, String member) {
        List<String> members = new ArrayList<>();
        members.add(member);

        return inviteJoinGroup(groupid, members);
    }

    public static boolean inviteJoinGroup(String groupid, List<String> members) {

        try {
            InviteXmlMessage xmlMessage = new InviteXmlMessage(groupid, members, "1", "");

            xstream.alias("Request", xmlMessage.getClass());
            xstream.alias("member", String.class);
            String xmlData = xstream.toXML(xmlMessage);

            String timestamp = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            String account = "70063b6a3b1a11e58b68ac853d9d52fd";
            String token = "04b32bdb8b9caa3ffdc05348034188c2";

            HttpClient client = HttpClientUtils.getConnection();
            String sig = account + token + timestamp;
            sig = Digests.md5(sig.getBytes()).toString().toUpperCase();
            String url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/" + account + "/Group/InviteJoinGroup?sig="+sig;

            HttpUriRequest post = HttpClientUtils.getRequestMethod(xmlData, url, "post");
            post.addHeader("Accept", "application/xml");
            post.addHeader("Content-Type", "application/xml;charset=utf-8");
            String authorization = Encodes.encodeBase64(account + ":" + timestamp);
            post.addHeader("Authorization", authorization);

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");
                Map<String, String> xmlMap = parseXml(message);
                System.out.println(xmlMap.get("statusCode"));
                return true;
            } else {
                System.out.println("请求失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    static class ModifyGroupMessage {
        private String groupId;
        private String name;
        private String permission;

        public ModifyGroupMessage(String groupId, String name, String permission) {
            this.groupId = groupId;
            this.name = name;
            this.permission = permission;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

    public static boolean modifyGroupName(String groupid, String name) {

        try {
            ModifyGroupMessage xmlMessage = new ModifyGroupMessage(groupid, name, "1");

            xstream.alias("Request", xmlMessage.getClass());
            xstream.alias("member", String.class);
            String xmlData = xstream.toXML(xmlMessage);

            String timestamp = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            String account = "70063b6a3b1a11e58b68ac853d9d52fd";
            String token = "04b32bdb8b9caa3ffdc05348034188c2";

            HttpClient client = HttpClientUtils.getConnection();
            String sig = account + token + timestamp;
            sig = Digests.md5(sig.getBytes()).toString().toUpperCase();
            String url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/" + account + "/Group/ModifyGroup?sig="+sig;

            HttpUriRequest post = HttpClientUtils.getRequestMethod(xmlData, url, "post");
            post.addHeader("Accept", "application/xml");
            post.addHeader("Content-Type", "application/xml;charset=utf-8");
            String authorization = Encodes.encodeBase64(account + ":" + timestamp);
            post.addHeader("Authorization", authorization);

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");
                Map<String, String> xmlMap = parseXml(message);
                System.out.println(xmlMap.get("statusCode"));
                return true;
            } else {
                System.out.println("请求失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        String groupid = "g88980319";

        modifyGroupName(groupid, "皇牌藍山8天豪華之旅");
    }

}
