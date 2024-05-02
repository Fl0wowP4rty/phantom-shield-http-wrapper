import com.sun.org.apache.xpath.internal.operations.Or;
import tech.skidonion.api.wrapper.BoundAPI;
import tech.skidonion.api.wrapper.PhantomShieldAPI;
import tech.skidonion.api.wrapper.data.Order;
import tech.skidonion.api.wrapper.data.UserInformation;

public class Test {
    public static void main(String[] args) {
        PhantomShieldAPI api = new PhantomShieldAPI("EXAMPLE_ID","EXAMPLE_TOKEN");
        for (UserInformation.Subscription sub : api.getUserInformation("EXAMPLE_USERNAME", "EXAMPLE_SOFTWARE_ID").getSubscriptions()) {
            System.out.println(sub.getRank());
        }
        BoundAPI boundAPI = api.bindSoftware("EXAMPLE_SOFTWARE_ID");
        for (String s : boundAPI.generateKey("EXAMPLE_CARD_ID", 3)) {
            System.out.println(s);
        }
        System.out.println(boundAPI.getUserStatus("EXAMPLE_TOKEN").isOnline());
        boundAPI.setSuspected("EXAMPLE_USERNAME","EXAMPLE_REASON");
        boundAPI.removeSuspected("EXAMPLE_USERNAME");
        Order order = boundAPI.queryOrder("EXAMPLE_ORDER_ID");
        System.out.println(order.getCardName());
        boundAPI.setUserExpiredDate("EXAMPLE_USERNAME","EXAMPLE_ROLE_ID","EXAMPLE_DATE");
    }
}
