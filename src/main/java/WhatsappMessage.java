// Install the Java helper library from twilio.com/docs/java/install

import com.twilio.Twilio;
        import com.twilio.rest.api.v2010.account.Message;
        import com.twilio.type.PhoneNumber;

 public class WhatsappMessage{
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACf38cb8f2e0b42edd973d52947563ec00";
    public static final String AUTH_TOKEN = "c5dbb80ac51fef436c22de1343575307";
    public static final String TWILIO_NUMBER = "+12052932581"; // Twilio phone number for dialing outbound phone calls

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                new com.twilio.type.PhoneNumber("whatsapp:+12052932581"),
                "Hi Joe! Thanks for placing an order with us. We’ll let you know once your order has been processed and delivered. Your order number is O12235234")
                .create();

        System.out.println(message.getSid());
    }
}