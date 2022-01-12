package co.mandeep_singh.chatapp.Networking;

public class Connection {
    public static String api = "http://192.168.1.5:3001/";

    public static String token, userId;

    public static void setToken(String token) {
        Connection.token = token;
    }



    public static String getToken() {
        return token;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Connection.userId = userId;
    }

    public static  String getApi() {
        return api;
    }
}
