package tech.skidonion.api.wrapper.data;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import tech.skidonion.api.wrapper.exception.BadRequestException;
import tech.skidonion.api.wrapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    private final boolean pay;
    private final String rank,time,cardName,softwareName,username;
    public Order(String string){
        JsonValue value = Json.parse(string);
        JsonObject object = value.asObject();
        if(object.get("code").asInt() != 0){
            throw new BadRequestException(object.get("message").asString());
        }
        JsonObject data = object.get("entity").asObject().get("data").asObject();
        this.pay = data.get("pay_status").asBoolean();
        this.rank = data.get("rank_name").asString();
        this.time = data.get("create_time").asString();
        this.cardName =  data.get("card_name").asString();
        this.softwareName = data.get("software_name").asString();
        this.username = data.get("username").asString();
    }

    public long getCreateTimeAsMs() {
        return StringUtil.convertToMillis(time);
    }
    public String getCreateTime() {
        return time;
    }

    public boolean isPayed() {
        return pay;
    }

    public String getRank() {
        return rank;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public String getUsername() {
        return username;
    }


}
