package tech.skidonion.api.wrapper.data;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import tech.skidonion.api.wrapper.exception.BadRequestException;
import tech.skidonion.api.wrapper.util.StringUtil;

public class UserStatus {

    private final boolean isOnline;

    private String username,userId,softwareId,time;

    public UserStatus(String json){
        JsonValue value = Json.parse(json);
        JsonObject object = value.asObject();
        if(object.get("code").asInt() != 0){
            isOnline = false;
        }else {
            isOnline = true;
            JsonObject data = object.get("entity").asObject().get("data").asObject();
            this.userId = data.get("user_id").asString();
            this.username = data.get("username").asString();
            this.softwareId = data.get("software_id").asString();
            this.time = data.get("create_time").asString();
        }
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public String getTime() {
        return time;
    }

    public long getTimeAsMs(){
        return StringUtil.convertToMillis(getTime());
    }
}
