package tech.skidonion.api.wrapper;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import tech.skidonion.api.wrapper.data.Order;
import tech.skidonion.api.wrapper.data.UserInformation;
import tech.skidonion.api.wrapper.data.UserStatus;
import tech.skidonion.api.wrapper.exception.BadRequestException;
import tech.skidonion.api.wrapper.util.HttpClient;
import tech.skidonion.api.wrapper.util.StringUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 对https://www.skidonion.tech/docs/api/introduction.html相关接口的包装
 * 详细参数和作用可以在此页面查看
 */
public class PhantomShieldAPI {

    private static final String URL = "https://skidonion.tech/api/admin/";
    private final String userId, token;
    private final HttpClient httpClient;

    /**
     * 此方法需要的信息可以在https://www.skidonion.tech/docs/api/introduction.html查看详细获取方法
     *
     * @param userId ID    可在用户面板上架产品界面查看
     * @param token  Token 可在用户面板上架产品界面查看
     */
    public PhantomShieldAPI(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.httpClient = new HttpClient();
        this.httpClient.addHeader("phantom-shield-x-uid", userId);
        this.httpClient.addHeader("phantom-shield-x-api-token", token);
    }

    /**
     * 绑定软件ID到示例，绑定后可以不传入softwareId参数
     *
     * @param id 软件ID 可以在用户面板软件列表界面查看
     */
    public BoundAPI bindSoftware(String id){
        return new BoundAPI(id,this);
    }

    /**
     * 在调用任何与用户状态相关的方法之前，请先使用 {@link UserStatus#isOnline()} 方法判断用户是否在线。
     * 否则，其他获取方法将无法获取到数据。
     *
     * @return {@link UserStatus}
     */
    public UserStatus getUserStatus(String token, String softwareId){
        String result = httpClient.post(URL + "user-online?software_id=" + softwareId + "&token=" + token);
        return new UserStatus(result);
    }

    /**
     * @param roleId 角色ID（用户组），可在用户组界面查看
     */
    public void setUserExpiredDate(String username, String roleId,long expiredDate,String softwareId){
        this.setUserExpiredDate(username,roleId, StringUtil.convertToString(expiredDate),softwareId);
    }

    /**
     * @param roleId 角色ID（用户组），可在用户组界面查看
     * @param expiredDate 示例格式：2025-05-05 20:00:00
     */
    public void setUserExpiredDate(String username, String roleId,String expiredDate,String softwareId){
        String result = httpClient.post(URL + "set-user-expired-date?software_id=" + softwareId + "&username=" + username + "&role_id=" + roleId + "&expired_date=" + URLEncoder.encode(expiredDate));
        JsonObject resp = Json.parse(result).asObject();
        if (resp.get("code").asInt() != 0) {
            throw new BadRequestException(resp.get("message").asString());
        }
    }

    /**
     * @param cardId 卡密ID，可在卡密类型界面查看
     */
    public Collection<String> generateKey(String cardId, int amount, String softwareId){
        String result = httpClient.post(URL + "generate-card?software_id=" + softwareId + "&card_id=" + cardId + "&amount=" + amount);
        JsonObject resp = Json.parse(result).asObject();
        if (resp.get("code").asInt() != 0) {
            throw new BadRequestException(resp.get("message").asString());
        }
        JsonArray entry = resp.get("entity").asObject().get("data").asArray();
        List<String> returnValue = new ArrayList<>();
        for (JsonValue jsonValue : entry) {
            returnValue.add(jsonValue.asString());
        }
        return returnValue;
    }

    public void removeSuspected(String username, String softwareId) {
        String result = httpClient.post(URL + "remove-suspected?software_id=" + softwareId + "&username=" + username);
        JsonObject resp = Json.parse(result).asObject();
        if (resp.get("code").asInt() != 0) {
            throw new BadRequestException(resp.get("message").asString());
        }
    }

    public void setSuspected(String username, String reason, String softwareId) {
        String result = httpClient.post(URL + "set-as-suspected?software_id=" + softwareId + "&username=" + username + "&reason=" + reason);
        JsonObject resp = Json.parse(result).asObject();
        if (resp.get("code").asInt() != 0) {
            throw new BadRequestException(resp.get("message").asString());
        }
    }

    public Order queryOrder(String orderId, String softwareId) {
        return new Order(httpClient.post(URL + "query-order?software_id=" + softwareId + "&order_id=" + orderId));
    }

    public UserInformation getUserInformation(String username, String softwareId) {
        return new UserInformation(httpClient.post(URL + "user-information?software_id=" + softwareId + "&username=" + username));
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
