package tech.skidonion.api.wrapper;

import tech.skidonion.api.wrapper.data.Order;
import tech.skidonion.api.wrapper.data.UserInformation;
import tech.skidonion.api.wrapper.data.UserStatus;
import tech.skidonion.api.wrapper.exception.ParameterException;
import tech.skidonion.api.wrapper.util.StringUtil;

import java.util.Collection;

public class BoundAPI {
    private final String softwareId;
    private final PhantomShieldAPI phantomShieldAPI;

    BoundAPI(String softwareId, PhantomShieldAPI phantomShieldAPI) {
        this.softwareId = softwareId;
        this.phantomShieldAPI = phantomShieldAPI;
    }

    public void setSuspected(String username, String reason) {
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        phantomShieldAPI.setSuspected(username,reason,softwareId);
    }
    public UserInformation getUserInformation(String username) {

        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }

        return phantomShieldAPI.getUserInformation(username,softwareId);
    }

    public Order queryOrder(String orderId) {
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        return phantomShieldAPI.queryOrder(orderId,softwareId);
    }

    public void setUserExpiredDate(String username, String roleId,long expiredDate){
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        phantomShieldAPI.setUserExpiredDate(username,roleId, StringUtil.convertToString(expiredDate),softwareId);
    }

    public void removeSuspected(String username) {
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        phantomShieldAPI.removeSuspected(username,softwareId);
    }

    public Collection<String> generateKey(String cardId, int amount){
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        return phantomShieldAPI.generateKey(cardId,amount,softwareId);
    }


    public void setUserExpiredDate(String username, String roleId,String expiredDate){
        if (softwareId == null) {
            throw new ParameterException("softwareId is empty.");
        }
        phantomShieldAPI.setUserExpiredDate(username,roleId,expiredDate,softwareId);
    }

    public UserStatus getUserStatus(String token){
        return phantomShieldAPI.getUserStatus(token,softwareId);
    }

}
