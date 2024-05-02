package tech.skidonion.api.wrapper.data;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import tech.skidonion.api.wrapper.exception.BadRequestException;
import tech.skidonion.api.wrapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class UserInformation {

    private final List<Subscription> subscriptions = new ArrayList<>();

    public UserInformation(String string){
        JsonValue value = Json.parse(string);
        JsonObject object = value.asObject();
        if(object.get("code").asInt() != 0){
            throw new BadRequestException(object.get("message").asString());
        }
        JsonArray entry = object.get("entity").asObject().get("data").asArray();
        for (JsonValue sub : entry) {
            JsonObject data = sub.asObject();
            subscriptions.add(new Subscription(data.get("rank_name").asString(),data.get("expired_date").asString(),data.get("start_date").asString()));
        }
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public static class Subscription{
        private final String rank;
        private final String expired;
        private final String start;

        public Subscription(String rank, String expired, String start) {
            this.rank = rank;
            this.expired = expired;
            this.start = start;
        }

        public long getExpiredAsMs(){
            return StringUtil.convertToMillis(getExpired());
        }

        public long getStartAsMs(){
            return StringUtil.convertToMillis(getStart());
        }

        public String getRank() {
            return rank;
        }

        public String getExpired() {
            return expired;
        }

        public String getStart() {
            return start;
        }
    }
}
